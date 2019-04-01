package com.leo.platform.upms.entity.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;

@Entity
@Table(name = "personal_message_content")
public class MessageContent extends BaseBizEntity<Long> {
    /**
     * @Fields serialVersionUID : 反序列化时候，判断是否是同一个对象
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "message_id")
    private Long messageId;
    @Column(name = "content")
    private String content;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
