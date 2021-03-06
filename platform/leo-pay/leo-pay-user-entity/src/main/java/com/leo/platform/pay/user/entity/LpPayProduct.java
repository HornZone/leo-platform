package com.leo.platform.pay.user.entity;

import java.io.Serializable;

import com.leo.platform.common.enums.PublicEnum;
import com.leo.platform.pay.common.entity.BaseEntity;

/**
 * 支付产品实体类
 */
public class LpPayProduct extends BaseEntity implements Serializable {

    private String productCode;

    private String productName;

    private String auditStatus;

    private static final long serialVersionUID = 1L;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getAuditStatusDesc() {
        return PublicEnum.getEnum(this.getAuditStatus()).getDesc();
    }
}