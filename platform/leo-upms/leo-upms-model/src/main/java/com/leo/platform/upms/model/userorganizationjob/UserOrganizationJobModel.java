package com.leo.platform.upms.model.userorganizationjob;

import com.leo.platform.common.model.page.BasePageModel;
import com.leo.platform.upms.model.user.UserModel;

public class UserOrganizationJobModel extends BasePageModel {
    private UserModel user;
    private Long userId;
    private Long organizationId;
    private Long jobId;

    public UserOrganizationJobModel(Long userId, Long organizationId, Long jobId) {
        this.userId = userId;
        this.organizationId = organizationId;
        this.jobId = jobId;
    }

    public UserOrganizationJobModel() {}

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
}
