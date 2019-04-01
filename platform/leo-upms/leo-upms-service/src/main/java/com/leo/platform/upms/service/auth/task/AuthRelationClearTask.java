package com.leo.platform.upms.service.auth.task;

import java.util.Collection;
import java.util.Set;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.common.util.LogUtils;
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.upms.dao.AuthDao;
import com.leo.platform.upms.entity.auth.Auth;
import com.leo.platform.upms.entity.role.Role;
import com.leo.platform.upms.model.auth.AuthModel;
import com.leo.platform.upms.service.auth.AuthService;
import com.leo.platform.upms.service.group.GroupService;
import com.leo.platform.upms.service.organization.JobService;
import com.leo.platform.upms.service.organization.OrganizationService;
import com.leo.platform.upms.service.permission.RoleService;

/**
 * 清理Auth无关联的关系 1、Auth-Role 2、Auth-Organization/Job 3、Auth-Group
 * <p/>
 * <p>
 * User: Caven_Zhou
 * <p>
 * Date: 13-5-18 下午1:44
 * <p>
 * Version: 1.0
 */
@Service
public class AuthRelationClearTask {

    @Autowired
    @Qualifier("authServiceImpl")
    private AuthService authService;

    @Autowired
    @Qualifier("roleServiceImpl")
    private RoleService roleService;

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
    @Qualifier("authDaoImpl")
    private AuthDao authDao;

    /**
     * 清除删除的角色对应的关系 该任务会放在定时器中，清理没有维护的权限
     */
    public void clearDeletedAuthRelation() {

        Set<Long> allRoleIds = findAllRoleIds();

        int pn = 0;

        PageList<Auth> authPage = null;

        do {
            AuthModel authModel = new AuthModel();
            authModel.setPageNumber(pn++);
            authPage = authService.getPageList(authModel);
            // 开启新事物清除
            try {
                AuthRelationClearTask authRelationClearService = (AuthRelationClearTask)AopContext.currentProxy();
                authRelationClearService.doClear(authPage.getList(), allRoleIds);
            } catch (Exception e) {
                // 出异常也无所谓
                LogUtils.logError("clear auth relation error", e);
            }
            // 清空会话
            authDao.clear();
        } while (authPage.hasNextPage());
    }

    /**
     * 1.
     * 
     * @param authColl 要检查的权限列表
     * @param allRoleIds 所有的权限列表
     */
    public void doClear(Collection<Auth> authColl, Set<Long> allRoleIds) {
        for (Auth auth : authColl) {
            switch (auth.getType()) {
                case "user":
                    break;// 因为用户是逻辑删除不用管
                case "user_group":
                case "organization_group":
                    if (!groupService.exists(auth.getGroupId())) {
                        authService.delete(auth);
                        continue;
                    }
                    break;
                case "organization_job":
                    if (!organizationService.exists(auth.getOrganizationId())) {
                        auth.setOrganizationId(0L);
                    }
                    if (!jobService.exists(auth.getJobId())) {
                        auth.setJobId(0L);
                    }
                    // 如果组织机构/工作职务都为0L 那么可以删除
                    if (auth.getOrganizationId() == 0L && auth.getJobId() == 0L) {
                        authService.delete(auth);
                        continue;
                    }
                    break;
            }

            /* 与allRoleIds做交集
             * retainAll方法用于从列表中移除未包含在指定collection中的所有元素。
             * 语法  boolean retainAll(Collection<?> c)
             * 返回值：如果List集合对象由于调用retainAll方法而发生更改，则返回 true */
            boolean hasRemovedAny = StringUtil.stringToList(auth.getRoleIds(), ",").retainAll(allRoleIds);
            if (hasRemovedAny) {
                authService.update(auth);
            }
        }

    }

    /**
     * transform将findAll查找出的Role对象，根据他们的id重新组合成Set<Long>
     * 
     * @return
     */
    private Set<Long> findAllRoleIds() {
        return Sets.newHashSet(Lists.transform(roleService.findAll(), new Function<Role, Long>() {
            @Override
            public Long apply(Role input) {
                return input.getId();
            }
        }));
    }

}
