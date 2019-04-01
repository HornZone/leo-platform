package com.leo.platform.upms.model.grouprelation;

import java.util.List;

import com.leo.platform.common.model.page.BasePageModel;

public class GroupRelationModel extends BasePageModel {
    private Long groupId;
    private Long organizationId;
    private Long userId;
    private Long startUserId;
    private Long endUserId;
    private Long delStartUserId;
    private Long delEndUserId;

    private List<Long> organizationIds;

    public List<Long> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<Long> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public Long getDelStartUserId() {
        return delStartUserId;
    }

    public void setDelStartUserId(Long delStartUserId) {
        this.delStartUserId = delStartUserId;
    }

    public Long getDelEndUserId() {
        return delEndUserId;
    }

    public void setDelEndUserId(Long delEndUserId) {
        this.delEndUserId = delEndUserId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(Long startUserId) {
        this.startUserId = startUserId;
    }

    public Long getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(Long endUserId) {
        this.endUserId = endUserId;
    }

}
