package com.leo.platform.upms.entity.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;

@Entity
@Table(name = "personal_message")
public class Message extends BaseBizEntity<Long> {
    /**
     * @Fields serialVersionUID : 序列号呗
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "sender_id")
    private Long senderId;
    @Column(name = "receiver_id")
    private Long receiverId;
    @Column(name = "send_date")
    private Date sendDate;
    @Column(name = "title")
    private String title;
    @Column(name = "sender_state")
    private String senderState;
    @Column(name = "sender_state_change_date")
    private Date senderStateChangeDate;
    @Column(name = "receiver_state")
    private String receiverState;
    @Column(name = "receiver_state_change_date")
    private Date receiverStateChangeDate;
    @Column(name = "type")
    private String type;
    @Column(name = "is_read")
    private Boolean isRead;
    @Column(name = "is_replied")
    private Boolean isReplied;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "parent_ids")
    private String parentIds;

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSenderState() {
        return senderState;
    }

    public void setSenderState(String senderState) {
        this.senderState = senderState;
    }

    public Date getSenderStateChangeDate() {
        return senderStateChangeDate;
    }

    public void setSenderStateChangeDate(Date senderStateChangeDate) {
        this.senderStateChangeDate = senderStateChangeDate;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public Date getReceiverStateChangeDate() {
        return receiverStateChangeDate;
    }

    public void setReceiverStateChangeDate(Date receiverStateChangeDate) {
        this.receiverStateChangeDate = receiverStateChangeDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsReplied() {
        return isReplied;
    }

    public void setIsReplied(Boolean isReplied) {
        this.isReplied = isReplied;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
