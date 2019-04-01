package com.leo.platform.upms.service.auth.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.upms.entity.permission.Permission;
import com.leo.platform.upms.entity.resource.Resource;
import com.leo.platform.upms.entity.role.Role;
import com.leo.platform.upms.entity.roleresourcepermission.RoleResourcePermission;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.entity.useronline.UserOnline;
import com.leo.platform.upms.entity.userorganizationjob.UserOrganizationJob;
import com.leo.platform.upms.service.auth.AuthService;
import com.leo.platform.upms.service.auth.UserAuthService;
import com.leo.platform.upms.service.group.GroupService;
import com.leo.platform.upms.service.organization.JobService;
import com.leo.platform.upms.service.organization.OrganizationService;
import com.leo.platform.upms.service.permission.PermissionService;
import com.leo.platform.upms.service.permission.RoleService;
import com.leo.platform.upms.service.resource.ResourceService;
import com.leo.platform.upms.service.user.UserOnlineService;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    @Qualifier("groupServiceImpl")
    private GroupService groupService;

    @Autowired
    @Qualifier("organizationServiceImpl")
    private OrganizationService organizationService;

    @Autowired
    @Qualifier("jobServiceImpl")
    private JobService jobService;

    @Autowired
    @Qualifier("authServiceImpl")
    private AuthService authService;

    @Autowired
    @Qualifier("roleServiceImpl")
    private RoleService roleService;

    @Autowired
    @Qualifier("resourceServiceImpl")
    private ResourceService resourceService;

    @Autowired
    @Qualifier("permissionServiceImpl")
    private PermissionService permissionService;

    @Autowired
    @Qualifier("userOnlineServiceImpl")
    private UserOnlineService userOnlineService;

    /**
     * 查找指定用户的角色（角色代表权限）
     */
    public Set<Role> findRoles(User user) {

        if (user == null) {
            return Sets.newHashSet();
        }

        Long userId = user.getId();

        Set<Long[]> organizationJobIds = Sets.newHashSet();
        Set<Long> organizationIds = Sets.newHashSet();
        Set<Long> jobIds = Sets.newHashSet();

        /* 用户和组织机构职务表有一个sys_user_organization_job表，user中组织机构职务字段organizationJobs保存着关系
         * </p>
         * 1.如果organizationJobs字段即有organization字段，也有jobId字段，则表示，该用户拥有该组织机构下，该职务的权限（角色）
         * 2.并且也同时拥有该organization和job的权限 */
        for (UserOrganizationJob o : user.getOrganizationJobs()) {
            Long organizationId = o.getOrganizationId();
            Long jobId = o.getJobId();

            if (organizationId != null && jobId != null && organizationId != 0L && jobId != 0L) {
                organizationJobIds.add(new Long[] {organizationId, jobId});
            }
            organizationIds.add(organizationId);
            jobIds.add(jobId);
        }

        // TODO 目前默认子会继承父 后续实现添加flag控制是否继承

        // 找组织机构祖先
        /* 当前组织机构如果有祖先，还需要上溯祖先的权限，所以必须把当前组织机构的祖先也加入进去 */
        organizationIds.addAll(organizationService.findAncestorIds(organizationIds));

        // 找工作职务的祖先
        /* 当前职务如果有祖先，还需要上溯祖先的权限，所以必须把当前职务的祖先也加入进去 */
        jobIds.addAll(jobService.findAncestorIds(jobIds));

        // 过滤组织机构 仅获取目前可用的组织机构数据，isShow字段为true
        organizationService.filterForCanShow(organizationIds, organizationJobIds);

        // 过滤工作职务 仅获取目前可用的工作职务数据
        jobService.filterForCanShow(jobIds, organizationJobIds);

        // 默认分组 + 根据用户编号 和 组织编号 找 分组
        /* 组织机构和组有一张关系表sys_group_relation,可以根据组织机构和用户id查找对应的群组信息 */
        Set<Long> groupIds = groupService.findShowGroupIds(userId, organizationIds);

        // 获取真正的实时的权限
        // 1.1、获取用户角色
        // 1.2、获取组织机构角色
        // 1.3、获取工作职务角色
        // 1.4、获取组织机构和工作职务组合的角色
        // 1.5、获取组角色
        List<Long> roleIds = authService.findRoleIds(userId, groupIds, organizationIds, jobIds, organizationJobIds);

        Set<Role> roles = roleService.findShowRoles(roleIds);

        return roles;

    }

    @Override
    public Set<String> findStringRoles(User user) {
        Set<Role> roles = ((UserAuthService)AopContext.currentProxy()).findRoles(user);
        return Sets.newHashSet(Collections2.transform(roles, new Function<Role, String>() {
            @Override
            public String apply(Role input) {
                return input.getRole();
            }
        }));
    }

    /**
     * 根据角色获取 权限字符串 如sys:admin:create
     * 
     * @param user
     * @return
     */
    @Override
    public Set<String> findStringPermissions(User user) {
        Set<String> permissions = Sets.newHashSet();

        Set<Role> roles = ((UserAuthService)AopContext.currentProxy()).findRoles(user);
        for (Role role : roles) {
            for (RoleResourcePermission rrp : role.getResourcePermissions()) {
                Resource resource = resourceService.findOne(rrp.getResourceId());

                String actualResourceIdentity = resourceService.findActualResourceIdentity(resource);

                // 不可用 即没查到 或者标识字符串不存在
                if (resource == null || StringUtils.isEmpty(actualResourceIdentity)
                    || Boolean.FALSE.equals(resource.getIsShow())) {
                    continue;
                }

                for (Long permissionId : rrp.getPermissionIdsList()) {
                    Permission permission = permissionService.findOne(permissionId);

                    // 不可用
                    if (permission == null || Boolean.FALSE.equals(permission.getIsShow())) {
                        continue;
                    }
                    permissions.add(actualResourceIdentity + ":" + permission.getPermission());

                }
            }

        }

        return permissions;
    }

    @Override
    public PageList<UserOnline> findExpiredUserOnlineList(Date expiredDate) {
        return userOnlineService.findExpiredUserOnlineList(expiredDate);
    }

    @Override
    public void batchOffline(List<String> needOfflineIdList) {
        userOnlineService.batchOffline(needOfflineIdList);
    }

}
