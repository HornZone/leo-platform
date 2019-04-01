package com.leo.platform.upms.entity.organization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;

@Entity
@Table(name = "sys_organization")
public class Organization extends BaseBizEntity<Long> {
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "parent_ids")
    private String parentIds;
    @Column(name = "icon")
    private String icon;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "is_show")
    private Boolean isShow;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }
}
