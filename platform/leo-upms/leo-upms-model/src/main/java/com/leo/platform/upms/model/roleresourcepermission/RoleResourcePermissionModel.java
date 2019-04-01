package com.leo.platform.upms.model.roleresourcepermission;

import com.leo.platform.common.model.page.BasePageModel;

public class RoleResourcePermissionModel extends BasePageModel {
    private Long roleId;
    private Long resourceId;
    private String permissionIds;

    public RoleResourcePermissionModel(Long roleId, Long resourceId) {
        this.roleId = roleId;
        this.resourceId = resourceId;
    }

    public RoleResourcePermissionModel() {}

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

}
