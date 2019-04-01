package com.leo.platform.pay.user.entity;

import java.io.Serializable;

import com.leo.platform.common.enums.PublicStatusEnum;
import com.leo.platform.pay.common.entity.BaseEntity;

/**
 * 用户信息
 */
public class LpUserInfo extends BaseEntity implements Serializable {

    private String userNo;

    private String userName;

    private String accountNo;

    private static final long serialVersionUID = 1L;

    private String mobile;

    private String password;
    /** 支付密码 */
    private String payPwd;

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getStatusDesc() {
        return PublicStatusEnum.getEnum(this.getStatus()).getDesc();
    }

}