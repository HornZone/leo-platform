package com.leo.platform.upms.service.extra.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.leo.platform.server.cache.BaseCacheAspect;
import com.leo.platform.upms.entity.auth.Auth;
import com.leo.platform.upms.entity.group.Group;
import com.leo.platform.upms.entity.grouprelation.GroupRelation;
import com.leo.platform.upms.entity.job.Job;
import com.leo.platform.upms.entity.organization.Organization;
import com.leo.platform.upms.entity.permission.Permission;
import com.leo.platform.upms.entity.resource.Resource;
import com.leo.platform.upms.entity.role.Role;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.service.auth.AuthService;
import com.leo.platform.upms.service.group.GroupRelationService;
import com.leo.platform.upms.service.group.GroupService;
import com.leo.platform.upms.service.organization.JobService;
import com.leo.platform.upms.service.organization.OrganizationService;
import com.leo.platform.upms.service.permission.PermissionService;
import com.leo.platform.upms.service.permission.RoleService;
import com.leo.platform.upms.service.resource.ResourceService;
import com.leo.platform.upms.service.user.UserService;

/**
 * evict是回收的意思，回收缓存
 * <p/>
 * 用户权限的切面
 * <p/>
 * 1、当调用如下方法时，加缓存
 * <p/>
 * {@link com.leo.platform.upms.service.auth.UserAuthService#findRoles}
 * {@link com.leo.platform.upms.service.auth.UserAuthService#findStringRoles}
 * {@link com.leo.platform.upms.service.auth.UserAuthService#findStringPermissions}
 * <p/>
 * 2、授权（Auth）
 * <p/>
 * 当增删改授权时， 如果是用户相关的，只删用户的即可，
 * <p/>
 * 其他的全部清理
 * <p/>
 * 
 * 3.1、资源（Resource）
 * <p/>
 * 当修改资源时判断是否发生变化（如resourceIdentity的改变，是否显示的改变），如果变了清缓存
 * <p/>
 * 当删除资源时，清缓存
 * <p/>
 * 3.2、权限（Permission）
 * <p/>
 * 当修改权限时判断是否发生变化（如permission，是否显示），如果变了清缓存
 * <p/>
 * 当删除权限时，清缓存
 * <p/>
 * 
 * 4、角色（Role）
 * <p/>
 * 当删除角色时，请缓存
 * <p/>
 * 当修改角色show/role/resourcePermissions关系时，清缓存
 * <p/>
 * 
 * 5.1、组织机构 当删除/修改show/parentId字段时，清缓存
 * <p/>
 * 5.2、工作职务 当删除/修改show/parentId字段时，清缓存
 * <p/>
 * 
 * 6.1、组
 * <p/>
 * 当修改组的默认组/show时，清缓存
 * <p/>
 * 当删除组时，清缓存
 * <p/>
 * 6.2、当删除组关系时
 * <p/>
 * 当新增/修改/删除的是某个用户的，只清理这个用户的缓存
 * <p/>
 * 其他清所有缓存
 * <p/>
 * 
 * 7、用户
 * <p/>
 * 修改时，如果组织机构/工作职务变了，仅需清自己的缓存
 * <p/>
 * 清理自己时也同时清理菜单的缓存
 * <p/>
 * TODO 1、异步失效缓存
 * <p/>
 * 2、预填充缓存（即把此刻失效的再通过异步任务查一次） 目前只查前100个吧
 * <p/>
 * 3、加二级缓存 提高失效再查的效率
 * <p/>
 * 此方法的一个缺点就是 只要改了一个，所有缓存失效。。。。 TODO 思考更好的做法？
 * <p/>
 * <p>
 * User: Caven_Zhou
 */
@Component
@Aspect
public class UserAuthCacheAspect extends BaseCacheAspect {

    public UserAuthCacheAspect() {
        setCacheName("sys-authCache");
    }

    private String rolesKeyPrefix = "roles-";
    private String stringRolesKeyPrefix = "string-roles-";
    private String stringPermissionsKeyPrefix = "string-permissions-";

    @Autowired
    private ResourceMenuCacheAspect resourceMenuCacheAspect;

    @Autowired
    @Qualifier("authServiceImpl")
    private AuthService authService;
    @Autowired
    @Qualifier("resourceServiceImpl")
    private ResourceService resourceService;
    @Autowired
    @Qualifier("permissionServiceImpl")
    private PermissionService permissionService;
    @Autowired
    @Qualifier("roleServiceImpl")
    private RoleService roleService;
    @Autowired
    @Qualifier("organizationServiceImpl")
    private OrganizationService organizationService;
    @Autowired
    @Qualifier("jobServiceImpl")
    private JobService jobService;
    @Autowired
    @Qualifier("groupServiceImpl")
    private GroupService groupService;
    @Autowired
    @Qualifier("groupRelationServiceImpl")
    private GroupRelationService groupRelationService;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    // //////////////////////////////////////////////////////////////////////////////
    // // 切入点
    // //////////////////////////////////////////////////////////////////////////////

    /**
     * 2、授权（Auth） 当增删改授权时， 如果是用户相关的，只删用户的即可， 其他的全部清理
     */
    @Pointcut(value = "target(com.leo.platform.upms.service.auth.impl.AuthServiceImpl)")
    private void authServicePointcut() {}

    @Pointcut(value = "execution(* addGroupAuth(..)) " + "|| execution(* addOrganizationJobAuth(..)) "
        + "|| execution(* addOrganizationJobAuth(..))")
    private void authCacheEvictAllPointcut() {}

    @Pointcut(value = "(execution(* addUserAuth(*,..)) && args(arg, ..)) "
        + "|| (execution(* update(*)) && args(arg)) " + "|| (execution(* save(*)) && args(arg)) "
        + "|| (execution(* delete(*)) && args(arg))", argNames = "arg")
    private void authCacheEvictAllOrSpecialPointcut(Object arg) {}

    /**
     * 3.1、资源（Resource） 当修改资源时判断是否发生变化（如resourceIdentity，是否显示isshow），如果变了清缓存 当删除资源时，清缓存
     */
    @Pointcut(value = "target(com.leo.platform.upms.service.resource.impl.ResourceServiceImpl)")
    private void resourceServicePointcut() {}

    @Pointcut(value = "execution(* delete(..))")
    private void resourceCacheEvictAllPointcut() {}

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void resourceMaybeCacheEvictAllPointcut(Resource arg) {}

    /**
     * 3.2、权限（Permission） 当修改权限时判断是否发生变化（如permission，是否显示），如果变了清缓存 当删除权限时，清缓存
     */
    @Pointcut(value = "target(com.leo.platform.upms.service.permission.impl.PermissionServiceImpl)")
    private void permissionServicePointcut() {}

    @Pointcut(value = "execution(* delete(..))")
    private void permissionCacheEvictAllPointcut() {}

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void permissionMaybeCacheEvictAllPointcut(Permission arg) {}

    /**
     * 4、角色（Role） 当删除角色时，请缓存 当修改角色show/role/resourcePermissions关系时，清缓存
     */
    @Pointcut(value = "target(com.leo.platform.upms.service.permission.impl.RoleServiceImpl)")
    private void roleServicePointcut() {}

    @Pointcut(value = "execution(* delete(..))")
    private void roleCacheEvictAllPointcut() {}

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void roleMaybeCacheEvictAllPointcut(Role arg) {}

    /**
     * 5.1、组织机构 当删除/修改show字段时，清缓存
     */
    @Pointcut(value = "target(com.leo.platform.upms.service.organization.impl.OrganizationServiceImpl)")
    private void organizationServicePointcut() {}

    @Pointcut(value = "execution(* delete(..))")
    private void organizationCacheEvictAllPointcut() {}

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void organizationMaybeCacheEvictAllPointcut(Organization arg) {}

    /**
     * 5.2、工作职务 当删除/修改show字段时，清缓存
     */
    @Pointcut(value = "target(com.leo.platform.upms.service.organization.impl.JobServiceImpl)")
    private void jobServicePointcut() {}

    @Pointcut(value = "execution(* delete(..))")
    private void jobCacheEvictAllPointcut() {}

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void jobMaybeCacheEvictAllPointcut(Job arg) {}

    /**
     * 6。1、组 当修改组的默认组/show时，清缓存 当删除组时，清缓存
     */
    @Pointcut(value = "target(com.leo.platform.upms.service.group.impl.GroupServiceImpl)")
    private void groupServicePointcut() {}

    @Pointcut(value = "execution(* delete(..))")
    private void groupCacheEvictAllPointcut() {}

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void groupMaybeCacheEvictAllPointcut(Group arg) {}

    /**
     * 6.2、当删除组关系时 当添加/修改/删除的是某个用户的，只清理这个用户的缓存 其他情况，清所有
     */
    @Pointcut(value = "target(com.leo.platform.upms.service.group.impl.GroupRelationServiceImpl)")
    private void groupRelationServicePointcut() {}

    @Pointcut(value = "execution(* appendRelation(*,*))")
    private void groupRelationCacheEvictAllPointcut() {}

    @Pointcut(value = "(execution(* delete(*)) && args(arg)) " + "|| (execution(* update(*)) && args(arg)) "
        + "|| execution(* appendRelation(*,*,*,*)) && args(*,arg,*,*) ", argNames = "arg")
    private void groupRelationMaybeCacheEvictAllOrSpecialPointcut(Object arg) {}

    /**
     * 7、用户 修改时，如果组织机构/工作职务变了，仅需清自己的缓存
     */
    @Pointcut(value = "target(com.leo.platform.upms.service.user.impl.UserServiceImpl)")
    private void userServicePointcut() {}

    @Pointcut(value = "execution(* delete(*)) && args(arg) || execution(* update(*)) && args(arg)", argNames = "arg")
    private void userCacheEvictSpecialPointcut(Object arg) {}

    @Pointcut(value = "target(com.leo.platform.upms.service.auth.impl.UserAuthServiceImpl)")
    private void userAuthServicePointcut() {}

    @Pointcut(value = "execution(* findRoles(*)) && args(arg)", argNames = "arg")
    private void cacheFindRolesPointcut(User arg) {}

    @Pointcut(value = "execution(* findStringRoles(*)) && args(arg)", argNames = "arg")
    private void cacheFindStringRolesPointcut(User arg) {}

    @Pointcut(value = "execution(* findStringPermissions(*)) && args(arg)", argNames = "arg")
    private void cacheFindStringPermissionsPointcut(User arg) {}

    // ////////////////////////////////////////////////////////////////////////////////
    // // 增强
    // ////////////////////////////////////////////////////////////////////////////////

    // ////////////////////////////////////////////////////////////////////////////////
    // // 查询时 查缓存/加缓存
    // ////////////////////////////////////////////////////////////////////////////////
    @Around(value = "userAuthServicePointcut() && cacheFindRolesPointcut(arg)", argNames = "pjp,arg")
    public Object findRolesCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {
        User user = arg;

        String key = null;
        if (user != null) {
            key = rolesKey(user.getId());
        }

        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findRolesCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }

        log.debug("cacheName:{}, method:findRolesCacheableAdvice, miss key:{}", cacheName, key);

        retVal = pjp.proceed();

        this.put(key, retVal);

        return retVal;
    }

    @Around(value = "userAuthServicePointcut() && cacheFindStringRolesPointcut(arg)", argNames = "pjp,arg")
    public Object findStringRolesCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {
        User user = arg;

        String key = null;
        if (user != null) {
            key = stringRolesKey(user.getId());
        }

        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findStringRolesCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findStringRolesCacheableAdvice, miss key:{}", cacheName, key);

        retVal = pjp.proceed();

        this.put(key, retVal);

        return retVal;
    }

    @Around(value = "userAuthServicePointcut() && cacheFindStringPermissionsPointcut(arg)", argNames = "pjp,arg")
    public Object findStringPermissionsCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {
        User user = arg;

        String key = stringPermissionsKey(user.getId());

        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findStringPermissionsCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findStringPermissionsCacheableAdvice, miss key:{}", cacheName, key);

        retVal = pjp.proceed();

        this.put(key, retVal);

        return retVal;
    }

    // ////////////////////////////////////////////////////////////////////////////////
    // // 清空整个缓存,原则上，添加操作都要清空缓存，因为如果有缓存，就会从缓存中获取脏数据
    // ////////////////////////////////////////////////////////////////////////////////
    @Before("(authServicePointcut() && authCacheEvictAllPointcut()) "
        + "|| (resourceServicePointcut() && resourceCacheEvictAllPointcut()) "
        + "|| (permissionServicePointcut() && permissionCacheEvictAllPointcut()) "
        + "|| (roleServicePointcut() && roleCacheEvictAllPointcut()) "
        + "|| (organizationServicePointcut() && organizationCacheEvictAllPointcut()) "
        + "|| (jobServicePointcut() && jobCacheEvictAllPointcut()) "
        + "|| (groupServicePointcut() && groupCacheEvictAllPointcut()) "
        + "|| (groupRelationServicePointcut() && groupRelationCacheEvictAllPointcut())")
    public void cacheClearAllAdvice() {
        log.debug("cacheName:{}, method:cacheClearAllAdvice, cache clear", cacheName);
        clear();
    }

    // ////////////////////////////////////////////////////////////////////////////////
    // // 可能清空特定/全部缓存
    // ////////////////////////////////////////////////////////////////////////////////

    /**
     * @param auth
     * @return 如果清空所有返回true 否则false
     */
    private boolean evictWithAuth(Auth auth) {
        boolean needEvictSpecail =
            auth != null && auth.getUserId() != null && auth.getGroupId() == null && auth.getOrganizationId() == null;
        if (needEvictSpecail) {
            Long userId = auth.getUserId();
            log.debug("cacheName:{}, method:evictWithAuth, evict userId:{}", cacheName, userId);
            evict(userId);
            return false;
        } else {
            log.debug("cacheName:{}, method:evictWithAuth, cache clear", cacheName);
            clear();
            return true;
        }
    }

    /**
     * <p>
     * 当添加，删除，修改的对象有userid，则说明都是针对这个userid做的操作，则回收这个userid的缓存即可
     * </p>
     * 即，针对一个userid的增删改，只需要对这个userid指定的缓存做缓存回收就可以了，没必要全部清空
     * 
     * @Title: authCacheClearSpecialOrAllAdvice
     * @author: Administrator
     * @data: 2018年9月19日
     * @param jp
     * @param arg
     * @return: void
     */
    @Before(value = "authServicePointcut() && authCacheEvictAllOrSpecialPointcut(arg)", argNames = "jp,arg")
    public void authCacheClearSpecialOrAllAdvice(JoinPoint jp, Object arg) {
        log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice begin", cacheName);
        String methodName = jp.getSignature().getName();
        if (arg instanceof Auth) {// 只清除某个用户的即可
            Auth auth = (Auth)arg;

            log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice delegate to evictWithAuth", cacheName);
            evictWithAuth(auth);
        } else if ("delete".equals(methodName)) { // 删除方法
            if (arg instanceof Long) { // 删除单个
                Long authId = (Long)arg;
                Auth auth = authService.findOne(authId);

                log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice delegate to evictWithAuth", cacheName);
                evictWithAuth(auth);
            } else if (arg instanceof Long[]) { // 批量删除
                for (Long authId : ((Long[])arg)) {
                    Auth auth = authService.findOne(authId);

                    log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice delegate to evictWithAuth",
                        cacheName);
                    if (evictWithAuth(auth)) {// 如果清空的是所有 直接返回
                        return;
                    }
                }
            } else if ("addUserAuth".equals(methodName)) {
                Long[] userIds = (Long[])arg;

                log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice, evict user ids:{}", cacheName,
                    Arrays.toString(userIds));
                evict(userIds);
            }
        }
    }

    @Before(value = "resourceServicePointcut() && resourceMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void resourceMaybeCacheClearAllAdvice(Resource arg) {
        Resource resource = arg;
        if (resource == null) {
            return;
        }
        Resource dbResource = resourceService.findOne(resource.getId());
        if (dbResource == null) {
            return;
        }

        // 只有当show/identity发生改变时才清理缓存
        if (!dbResource.getIsShow().equals(resource.getIsShow())
            || !dbResource.getIdentity().equals(resource.getIdentity())) {

            log.debug("cacheName:{}, method:resourceMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    @Before(value = "permissionServicePointcut() && permissionMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void permissionMaybeCacheClearAllAdvice(Permission arg) {

        Permission permission = arg;
        if (permission == null) {
            return;
        }
        Permission dbPermission = permissionService.findOne(permission.getId());
        if (dbPermission == null) {
            return;
        }

        // 只有当show/permission发生改变时才清理缓存
        if (!dbPermission.getIsShow().equals(permission.getIsShow())
            || !dbPermission.getPermission().equals(permission.getPermission())) {

            log.debug("cacheName:{}, method:permissionMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    @Before(value = "roleServicePointcut() && roleMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void roleMaybeCacheClearAllAdvice(Role arg) {
        Role role = arg;
        if (role == null) {
            return;
        }
        Role dbRole = roleService.findOne(role.getId());
        if (dbRole == null) {
            return;
        }

        // 只有当show/role发生改变时才清理缓存
        if (!dbRole.getShow().equals(role.getShow())
            || !dbRole.getRole().equals(role.getRole())
            || !(dbRole.getResourcePermissions().size() == role.getResourcePermissions().size() && dbRole
                .getResourcePermissions().containsAll(role.getResourcePermissions()))) {

            log.debug("cacheName:{}, method:roleMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    @Before(value = "organizationServicePointcut() && organizationMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void organizationMaybeCacheClearAllAdvice(Organization arg) {

        Organization organization = arg;
        if (organization == null) {
            return;
        }
        Organization dbOrganization = organizationService.findOne(organization.getId());
        if (dbOrganization == null) {
            return;
        }

        // 只有当show/parentId发生改变时才清理缓存
        if (!dbOrganization.getIsShow().equals(organization.getIsShow())
            || !dbOrganization.getParentId().equals(organization.getParentId())) {

            log.debug("cacheName:{}, method:organizationMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    @Before(value = "jobServicePointcut() && jobMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void jobMaybeCacheClearAllAdvice(Job arg) {
        Job job = arg;
        if (job == null) {
            return;
        }
        Job dbJob = jobService.findOne(job.getId());
        if (dbJob == null) {
            return;
        }

        // 只有当show/parentId发生改变时才清理缓存
        if (!dbJob.getIsShow().equals(job.getIsShow()) || !dbJob.getParentId().equals(job.getParentId())) {

            log.debug("cacheName:{}, method:jobMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    @Before(value = "groupServicePointcut() && groupMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void groupMaybeCacheClearAllAdvice(Group arg) {
        Group group = arg;
        if (group == null) {
            return;
        }
        Group dbGroup = groupService.findOne(group.getId());
        if (dbGroup == null) {
            return;
        }

        // 只有当修改组的默认组/show时才清理缓存
        if (!dbGroup.getShow().equals(group.getShow()) || !dbGroup.getDefaultGroup().equals(group.getDefaultGroup())) {

            log.debug("cacheName:{}, method:groupMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    /**
     * @param r
     * @return 如果清除所有 返回true，否则false
     */
    private boolean evictForGroupRelation(GroupRelation r) {
        // 如果是非某个用户，清理所有缓存
        if (r.getStartUserId() != null || r.getEndUserId() != null || r.getOrganizationId() != null) {

            log.debug("cacheName:{}, method:evictForGroupRelation, cache clear", cacheName);
            clear();
            return true;
        }
        if (r.getUserId() != null) {// 当添加/修改/删除的是某个用户的，只清理这个用户的缓存
            evict(r.getUserId());
            GroupRelation dbR = groupRelationService.findOne(r.getId());
            if (dbR != null && !dbR.getUserId().equals(r.getUserId())) { // 如果a用户替换为b用户时清理两个用户的缓存

                log.debug("cacheName:{}, method:evictForGroupRelation, evict userId:{}", cacheName, dbR.getUserId());
                evict(dbR.getUserId());
            }
        }
        return false;
    }

    @Before(value = "groupRelationServicePointcut() && groupRelationMaybeCacheEvictAllOrSpecialPointcut(arg)",
        argNames = "jp,arg")
    public void groupRelationMaybeCacheClearAllOrSpecialAdvice(JoinPoint jp, Object arg) {
        String methodName = jp.getSignature().getName();
        if (arg instanceof GroupRelation) {
            GroupRelation r = (GroupRelation)arg;

            log.debug(
                "cacheName:{}, method:groupRelationMaybeCacheClearAllOrSpecialAdvice delagate to evictForGroupRelation",
                cacheName);
            if (evictForGroupRelation(r)) {
                return;
            }

        } else if ("delete".equals(methodName)) {// 删除情况
            if (arg instanceof Long) {
                Long rId = (Long)arg;
                GroupRelation r = groupRelationService.findOne(rId);

                log.debug(
                    "cacheName:{}, method:groupRelationMaybeCacheClearAllOrSpecialAdvice delagate to evictForGroupRelation",
                    cacheName);
                if (evictForGroupRelation(r)) {
                    return;
                }
            } else if (arg instanceof Long[]) {
                for (Long rId : (Long[])arg) {
                    GroupRelation r = groupRelationService.findOne(rId);

                    log.debug(
                        "cacheName:{}, method:groupRelationMaybeCacheClearAllOrSpecialAdvice delagate to evictForGroupRelation",
                        cacheName);
                    if (evictForGroupRelation(r)) {
                        return;
                    }
                }

            }
        } else if ("appendRelation".equals(methodName)) {// 添加的情况
            Long[] userIds = (Long[])arg;

            log.debug("cacheName:{}, method:groupRelationMaybeCacheClearAllOrSpecialAdvice, evict user ids:{}",
                cacheName, Arrays.toString(userIds));
            evict(userIds);
        }
    }

    @Before(value = "userServicePointcut() && userCacheEvictSpecialPointcut(arg)", argNames = "jp,arg")
    public void userMaybeCacheClearSpecialAdvice(JoinPoint jp, Object arg) {
        String methodName = jp.getSignature().getName();
        if ("update".equals(methodName)) {
            User user = (User)arg;
            User dbUser = userService.findOne(user.getId());

            if (!(user.getOrganizationJobs().size() == dbUser.getOrganizationJobs().size() && dbUser
                .getOrganizationJobs().containsAll(user.getOrganizationJobs()))) {

                log.debug("cacheName:{}, method:userMaybeCacheClearSpecialAdvice, evict user id:{}", cacheName,
                    user.getId());
                evict(user.getId());
            }

        } else if ("delete".equals(methodName)) {// 删除情况
            if (arg instanceof Long) {
                Long userId = (Long)arg;

                log.debug("cacheName:{}, method:userMaybeCacheClearSpecialAdvice, evict user id:{}", cacheName, userId);
                evict(userId);
            } else if (arg instanceof Long[]) {

                Long[] userIds = (Long[])arg;

                log.debug("cacheName:{}, method:userMaybeCacheClearSpecialAdvice, evict user ids:{}", cacheName,
                    Arrays.toString(userIds));

                evict(userIds);
            }
        }
    }

    // ////////////////////////////////////////////////////////////////////////////////
    // // 缓存相关
    // ////////////////////////////////////////////////////////////////////////////////

    @Override
    public void clear() {
        super.clear();
        resourceMenuCacheAspect.clear();// 当权限过期 同时清理菜单的
    }

    public void evict(Long[] userIds) {
        for (Long userId : userIds) {
            evict(userId);
        }
    }

    public void evict(Long userId) {
        evict(rolesKey(userId));
        evict(stringRolesKey(userId));
        evict(stringPermissionsKey(userId));

        resourceMenuCacheAspect.evict(userId);// 当权限过期 同时清理菜单的
    }

    private String rolesKey(Long userId) {
        return this.rolesKeyPrefix + userId;
    }

    private String stringRolesKey(Long userId) {
        return this.stringRolesKeyPrefix + userId;
    }

    private String stringPermissionsKey(Long userId) {
        return this.stringPermissionsKeyPrefix + userId;
    }

}
