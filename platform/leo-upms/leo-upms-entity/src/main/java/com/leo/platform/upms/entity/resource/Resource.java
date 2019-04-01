package com.leo.platform.upms.entity.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;

@Entity
@Table(name = "sys_resource")
public class Resource extends BaseBizEntity<Long> {
    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "name")
    private String name;
    @Column(name = "identity")
    private String identity;
    @Column(name = "url")
    private String url;
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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
