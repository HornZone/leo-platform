package com.leo.platform.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseBizEntity<PK extends Serializable> extends BaseEntity<PK> {
    @Column(name = "code")
    private String code;
    @Column(name = "createTime")
    private Date createTime;
    @Column(name = "creator")
    private String creator;
    @Column(name = "creatorName")
    private String creatorName;
    @Column(name = "lastUpdateTime")
    private Date lastUpdateTime;
    @Column(name = "lastUpdator")
    private String lastUpdator;
    @Column(name = "lastUpdatorName")
    private String lastUpdatorName;
    @Column(name = "version")
    private String version;
    @Column(name = "optLock")
    private Long optLock;
    @Column(name = "remark")
    private String remark;
    @Column(name = "note")
    private String note;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdator() {
        return lastUpdator;
    }

    public void setLastUpdator(String lastUpdator) {
        this.lastUpdator = lastUpdator;
    }

    public String getLastUpdatorName() {
        return lastUpdatorName;
    }

    public void setLastUpdatorName(String lastUpdatorName) {
        this.lastUpdatorName = lastUpdatorName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getOptLock() {
        return optLock;
    }

    public void setOptLock(Long optLock) {
        this.optLock = optLock;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 重写这个方法，为了方便业务对象的比较
     * 
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        BaseBizEntity biz = (BaseBizEntity)obj;
        return biz != null
            && (this == biz || (Objects.equals(this.getId(), this.getId()) && Objects.equals(this.getCode(),
                biz.getCode())));
    }

    public void clearOperateInfo() {
        this.setCreateTime(null);
        this.setCreator(null);
        this.setCreatorName(null);
        this.setLastUpdateTime(null);
        this.setLastUpdator(null);
        this.setLastUpdatorName(null);
    }

    public void clear4Create() {
        this.setId(null);
        this.setCode(null);
        this.clearOperateInfo();
    }
}
