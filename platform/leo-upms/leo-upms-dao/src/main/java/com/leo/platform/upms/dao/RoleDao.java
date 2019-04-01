package com.leo.platform.upms.dao;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.upms.entity.role.Role;
import com.leo.platform.upms.entity.roleresourcepermission.RoleResourcePermission;

public interface RoleDao extends BaseDao<Role, Long> {
    void clear();

    /* @Query("from RoleResourcePermission where role=?1 and resourceId=?2") */
    RoleResourcePermission findRoleResourcePermission(Role role, Long resourceId);
}
