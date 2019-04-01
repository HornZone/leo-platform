package com.leo.platform.upms.model.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.leo.platform.common.model.page.BasePageModel;

public class MessageModel extends BasePageModel {

    private List<Long> ids;
    /**
     * 此field用作更新
     */
    private Long senderId;
    /**
     * 此字段用于查询
     */
    private Long oldSenderId;

    /**
     * 此field用作更新
     */
    private Long receiverId;

    /**
     * 此字段用于查询
     */
    private Long oldReceiverId;

    private Date sendDate;

    private String title;

    /**
     * 此field用作更新
     */
    private String senderState;
    /**
     * 此字段用于查询
     */
    private String oldSenderState;
    /**
     * 此field用作更新
     */
    private Date senderStateChangeDate;
    /**
     * 此字段用于查询
     */
    private Date oldSenderStateChangeDate;

    /**
     * 此field用作更新
     */
    private String receiverState;
    /**
     * 此字段用于查询
     */
    private String oldReceiverState;
    /**
     * 此field用作更新
     */
    private Date receiverStateChangeDate;
    /**
     * 此字段用于查询
     */
    private Date oldReceiverStateChangeDate;

    private String type;

    private Boolean isRead;

    private Boolean isReplied;

    private Long parentId;

    private String parentIds;

    private ArrayList<String> senderStates;
    private ArrayList<String> receiverStates;
    private Date senderExpireDate;
    private Date receiverExpireDate;

    public Date getOldReceiverStateChangeDate() {
        return oldReceiverStateChangeDate;
    }

    public void setOldReceiverStateChangeDate(Date oldReceiverStateChangeDate) {
        this.oldReceiverStateChangeDate = oldReceiverStateChangeDate;
    }

    public Long getOldReceiverId() {
        return oldReceiverId;
    }

    public void setOldReceiverId(Long oldReceiverId) {
        this.oldReceiverId = oldReceiverId;
    }

    public Long getOldSenderId() {
        return oldSenderId;
    }

    public void setOldSenderId(Long oldSenderId) {
        this.oldSenderId = oldSenderId;
    }

    public Date getOldSenderStateChangeDate() {
        return oldSenderStateChangeDate;
    }

    public void setOldSenderStateChangeDate(Date oldSenderStateChangeDate) {
        this.oldSenderStateChangeDate = oldSenderStateChangeDate;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Date getSenderExpireDate() {
        return senderExpireDate;
    }

    public void setSenderExpireDate(Date senderExpireDate) {
        this.senderExpireDate = senderExpireDate;
    }

    public Date getReceiverExpireDate() {
        return receiverExpireDate;
    }

    public void setReceiverExpireDate(Date receiverExpireDate) {
        this.receiverExpireDate = receiverExpireDate;
    }

    public ArrayList<String> getSenderStates() {
        return senderStates;
    }

    public void setSenderStates(ArrayList<String> senderStates) {
        this.senderStates = senderStates;
    }

    public ArrayList<String> getReceiverStates() {
        return receiverStates;
    }

    public void setReceiverStates(ArrayList<String> receiverStates) {
        this.receiverStates = receiverStates;
    }

    public String getOldReceiverState() {
        return oldReceiverState;
    }

    public void setOldReceiverState(String oldReceiverState) {
        this.oldReceiverState = oldReceiverState;
    }

    public String getOldSenderState() {
        return oldSenderState;
    }

    public void setOldSenderState(String oldSenderState) {
        this.oldSenderState = oldSenderState;
    }

    public Long getSenderId() {
        return senderId;
    }

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
