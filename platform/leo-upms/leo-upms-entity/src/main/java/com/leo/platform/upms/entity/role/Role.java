package com.leo.platform.upms.entity.role;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.upms.entity.roleresourcepermission.RoleResourcePermission;

@Entity
@Table(name = "sys_role")
public class Role extends BaseBizEntity<Long> {
    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "name")
    private String name;
    @Column(name = "role")
    private String role;
    @Column(name = "description")
    private String description;
    @Column(name = "is_show")
    private Boolean isShow;

    /**
     * 用户 组织机构 工作职务关联表
     */
    @Transient
    private List<RoleResourcePermission> resourcePermissions;

    public Role(String name, String role, String description, Boolean isShow) {
        this.name = name;
        this.role = role;
        this.description = description;
        this.isShow = isShow;
    }

    public List<RoleResourcePermission> getResourcePermissions() {
        return resourcePermissions;
    }

    public void setResourcePermissions(List<RoleResourcePermission> resourcePermissions) {
        this.resourcePermissions = resourcePermissions;
    }

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

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean isShow) {
        this.isShow = isShow;
    }
}
