package com.leo.platform.upms.model.userstatushistory;

import java.util.Date;

import com.leo.platform.common.model.page.BasePageModel;
import com.leo.platform.upms.model.user.UserModel;

public class UserStatusHistoryModel extends BasePageModel {
    private Long userId;
    private String status;
    private String reason;
    private Long opUserId;
    private Date opDate;

    private UserModel user;
    private UserModel opUser;

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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getOpUser() {
        return opUser;
    }

    public void setOpUser(UserModel opUser) {
        this.opUser = opUser;
    }

}
