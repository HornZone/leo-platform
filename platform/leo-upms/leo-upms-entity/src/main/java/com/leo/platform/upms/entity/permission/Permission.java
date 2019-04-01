package com.leo.platform.upms.entity.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;

@Entity
@Table(name = "sys_permission")
public class Permission extends BaseBizEntity<Long> {
    @Column(name = "name")
    private String name;
    @Column(name = "permission")
    private String permission;
    @Column(name = "description")
    private String description;
    @Column(name = "is_show")
    private Boolean isShow;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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
