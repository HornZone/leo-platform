package com.leo.platform.upms.service.permission.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.RoleDao;
import com.leo.platform.upms.entity.role.Role;
import com.leo.platform.upms.entity.roleresourcepermission.RoleResourcePermission;
import com.leo.platform.upms.service.permission.RoleService;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2016年11月1日 上午9:39:26
 * 
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    @Qualifier("roleDaoImpl")
    private RoleDao roleDao;

    @Override
    public Role update(Role role) {
        List<RoleResourcePermission> localResourcePermissions = role.getResourcePermissions();
        for (int i = 0, l = localResourcePermissions.size(); i < l; i++) {
            RoleResourcePermission localResourcePermission = localResourcePermissions.get(i);
            localResourcePermission.setRole(role);
            RoleResourcePermission dbResourcePermission = findRoleResourcePermission(localResourcePermission);
            if (dbResourcePermission != null) {// 出现在先删除再添加的情况
                dbResourcePermission.setRole(localResourcePermission.getRole());
                dbResourcePermission.setResourceId(localResourcePermission.getResourceId());
                dbResourcePermission.setPermissionIds(localResourcePermission.getPermissionIds());
                localResourcePermissions.set(i, dbResourcePermission);
            }
        }
        return super.update(role);
    }

    private RoleResourcePermission findRoleResourcePermission(RoleResourcePermission roleResourcePermission) {
        return roleDao.findRoleResourcePermission(roleResourcePermission.getRole(),
            roleResourcePermission.getResourceId());
    }

    /**
     * 获取可用的角色列表
     * 
     * @param roleIds，已经在sql层面用distinct来获取unique的roleId，所以此处就用List接收了，因为mybatis不能返回set结构的查询结果
     * @return
     */
    public Set<Role> findShowRoles(List<Long> roleIds) {

        Set<Role> roles = Sets.newHashSet();

        /* 获取最新的role角色对象，role是findAll从数据库最新获取到的 */
        for (Role role : findAll()) {
            if (Boolean.TRUE.equals(role.getShow()) && roleIds.contains(role.getId())) {
                roles.add(role);
            }
        }
        return roles;
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(roleDao);
    }

}
