package com.leo.platform.upms.service.message;

import java.util.ArrayList;

import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.message.Message;
import com.leo.platform.upms.enums.MessageState;

/**
 * Caven_ZHOU
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2017年1月2日 上午8:28:18
 * 
 */
public interface MessageService extends BaseService<Message, Long> {

    /**
     * 改变发件人 消息的原状态为目标状态
     * 
     * @param senderId
     * @param oldState
     * @param newState
     */
    public Integer changeSenderState(Long senderId, MessageState oldState, MessageState newState);

    /**
     * 改变收件人人 消息的原状态为目标状态
     * 
     * @param receiverId
     * @param oldState
     * @param newState
     */
    public Integer changeReceiverState(Long receiverId, MessageState oldState, MessageState newState);

    /**
     * 物理删除那些已删除的（即收件人和发件人 同时都删除了的）
     * 
     * @param deletedState
     */
    public Integer clearDeletedMessage(MessageState deletedState);

    /**
     * 更改状态
     * 
     * @param oldStates
     * @param newState
     * @param expireDays 当前时间-过期天数 时间之前的消息将改变状态
     */
    public Integer changeState(ArrayList<MessageState> oldStates, MessageState newState, int expireDays);

    /**
     * 统计用户收件箱未读消息
     * 
     * @param userId
     * @return
     */
    public Long countUnread(Long userId);

    public void markRead(final Long userId, final Long[] ids);
}
