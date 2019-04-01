package com.leo.platform.upms.entity.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;

@Entity
@Table(name = "sys_group")
public class Group extends BaseBizEntity<Long> {
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "is_show")
    private Boolean isShow;
    @Column(name = "default_group")
    private Boolean defaultGroup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Boolean getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(Boolean defaultGroup) {
        this.defaultGroup = defaultGroup;
    }
}
