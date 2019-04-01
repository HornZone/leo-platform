package com.leo.platform.upms.service.permission.task;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.common.model.page.PageList;
import com.leo.platform.common.util.LogUtils;
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.upms.dao.RoleDao;
import com.leo.platform.upms.entity.role.Role;
import com.leo.platform.upms.entity.roleresourcepermission.RoleResourcePermission;
import com.leo.platform.upms.model.role.RoleModel;
import com.leo.platform.upms.service.permission.PermissionService;
import com.leo.platform.upms.service.permission.RoleService;
import com.leo.platform.upms.service.resource.ResourceService;

/**
 * 清理无关联的Role-Resource/Permission关系 1、Role-Resource 2、Role-Permission
 * <p/>
 * <p>
 * User: Caven_Zhou
 * <p>
 * Date: 13-5-13 下午5:09
 * <p>
 * Version: 1.0
 */
@Service()
public class RoleClearRelationTask {

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
    @Qualifier("roleDaoImpl")
    private RoleDao roleDao;

    /**
     * 清除删除的角色对应的关系
     */
    public void clearDeletedRoleRelation() {

        int pn = 0;

        PageList<Role> rolePage = null;
        do {
            RoleModel roleModel = new RoleModel();
            roleModel.setPageNumber(pn++);
            rolePage = roleService.getPageList(roleModel);
            // 开启新事物清除
            try {
                RoleClearRelationTask roleClearRelationTask = (RoleClearRelationTask)AopContext.currentProxy();
                roleClearRelationTask.doClear(rolePage.getList());
            } catch (Exception e) {
                // 出异常也无所谓
                LogUtils.logError("clear role relation error", e);

            }
            // 清空会话
            roleDao.clear();
        } while (rolePage.hasNextPage());
    }

    public void doClear(Collection<Role> roleColl) {

        for (Role role : roleColl) {

            boolean needUpdate = false;
            Iterator<RoleResourcePermission> iter = role.getResourcePermissions().iterator();

            while (iter.hasNext()) {
                RoleResourcePermission roleResourcePermission = iter.next();

                // 如果资源不存在了 就删除
                Long resourceId = roleResourcePermission.getResourceId();
                if (!resourceService.exists(resourceId)) {
                    iter.remove();
                    needUpdate = true;
                }

                Iterator<Object> permissionIdIter =
                    StringUtil.stringToList(roleResourcePermission.getPermissionIds(), ",").iterator();
                while (permissionIdIter.hasNext()) {
                    Long permissionId = (Long)permissionIdIter.next();

                    if (!permissionService.exists(permissionId)) {
                        permissionIdIter.remove();
                        needUpdate = true;
                    }
                }

            }

            if (needUpdate) {
                roleService.update(role);
            }

        }

    }

}
