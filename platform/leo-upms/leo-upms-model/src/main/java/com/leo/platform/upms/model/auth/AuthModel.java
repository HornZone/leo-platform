package com.leo.platform.upms.model.auth;

import java.util.Collection;

import com.leo.platform.common.model.page.BasePageModel;

public class AuthModel extends BasePageModel {
    private Long organizationId;
    private Long jobId;
    private Long userId;
    private Long groupId;
    private String roleIds;
    private String type;

    private Collection<Long> groupIds;
    private Collection<Long> organizationIds;
    private Collection<Long> jobIds;

    private Collection<Long[]> organizationJobIds;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Collection<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public Collection<Long> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(Collection<Long> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public Collection<Long> getJobIds() {
        return jobIds;
    }

    public void setJobIds(Collection<Long> jobIds) {
        this.jobIds = jobIds;
    }

    public Collection<Long[]> getOrganizationJobIds() {
        return organizationJobIds;
    }

    public void setOrganizationJobIds(Collection<Long[]> organizationJobIds) {
        this.organizationJobIds = organizationJobIds;
    }

}
