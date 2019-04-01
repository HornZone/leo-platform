package com.leo.platform.upms.entity.userorganizationjob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.upms.entity.user.User;

@Entity
@Table(name = "sys_user_organization_job")
public class UserOrganizationJob extends BaseBizEntity<Long> {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "organization_id")
    private Long organizationId;
    @Column(name = "job_id")
    private Long jobId;

    @Transient
    private User user;

    public UserOrganizationJob(Long userId, Long organizationId, Long jobId) {
        this.userId = userId;
        this.organizationId = organizationId;
        this.jobId = jobId;
    }

    public UserOrganizationJob() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
