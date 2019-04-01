package com.leo.platform.upms.entity.grouprelation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;

@Entity
@Table(name = "sys_group_relation")
public class GroupRelation extends BaseBizEntity<Long> {
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "organization_id")
    private Long organizationId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "start_user_id")
    private Long startUserId;
    @Column(name = "end_user_id")
    private Long endUserId;

    public GroupRelation(Long group_id, Long organization_id, Long user_id, Long start_user_id, Long end_user_id) {
        this.groupId = group_id;
        this.organizationId = organization_id;
        this.userId = user_id;
        this.startUserId = start_user_id;
        this.endUserId = end_user_id;
    }

    public GroupRelation() {}

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
