package com.leo.platform.upms.model.message;

import com.leo.platform.common.model.page.BasePageModel;

public class MessageContentModel extends BasePageModel {

    private Long messageId;

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
