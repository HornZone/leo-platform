package com.leo.platform.upms.entity.userstatushistory;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.upms.entity.user.User;

@Entity
@Table(name = "sys_user_status_history")
public class UserStatusHistory extends BaseBizEntity<Long> {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "status")
    private String status;
    @Column(name = "reason")
    private String reason;
    @Column(name = "op_user_id")
    private Long opUserId;
    @Column(name = "op_date")
    private Date opDate;
    @Transient
    private User user;
    @Transient
    private User opUser;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOpUser() {
        return opUser;
    }

    public void setOpUser(User opUser) {
        this.opUser = opUser;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Long opUserId) {
        this.opUserId = opUserId;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}
