package com.leo.platform.upms.entity.roleresourcepermission;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.upms.entity.role.Role;

@Entity
@Table(name = "sys_role_resource_permission")
public class RoleResourcePermission extends BaseBizEntity<Long> {
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "resource_id")
    private Long resourceId;
    @Column(name = "permission_ids")
    private String permissionIds;
    /**
     * 角色id
     */
    @Transient
    private Role role;

    public RoleResourcePermission(Long role_id, Long resource_id, String permission_ids) {
        this.roleId = role_id;
        this.resourceId = resource_id;
        this.permissionIds = permission_ids;
    }

    public RoleResourcePermission() {}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(String permissionIds) {
        this.permissionIds = permissionIds;
    }

    public List<Long> getPermissionIdsList() {
        List<Long> permissionIdsList = StringUtil.stringToList(permissionIds, ",");
        return permissionIdsList;
    }
}
