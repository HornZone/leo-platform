package com.leo.platform.upms.model.role;

import com.leo.platform.common.model.page.BasePageModel;

public class RoleModel extends BasePageModel {
    private String name;
    private String role;
    private String description;
    private Boolean isShow;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

}
