package com.leo.platform.upms.service.message.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.MessageDao;
import com.leo.platform.upms.entity.message.Message;
import com.leo.platform.upms.enums.MessageState;
import com.leo.platform.upms.service.message.MessageService;

/**
 * User: Caven_Zhou
 * 
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
 * @date 2017年1月2日 上午8:33:25
 * 
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long> implements MessageService {

    @Autowired
    @Qualifier("messageDaoImpl")
    private MessageDao messageDao;

    /**
     * 改变发件人 消息的原状态为目标状态
     * 
     * @param senderId
     * @param oldState
     * @param newState
     */
    public Integer changeSenderState(Long senderId, MessageState oldState, MessageState newState) {
        Date changeDate = new Date();
        return messageDao.changeSenderState(senderId, oldState, newState, changeDate);
    }

    /**
     * 改变收件人人 消息的原状态为目标状态
     * 
     * @param receiverId
     * @param oldState
     * @param newState
     */
    public Integer changeReceiverState(Long receiverId, MessageState oldState, MessageState newState) {
        Date changeDate = new Date();
        return messageDao.changeReceiverState(receiverId, oldState, newState, changeDate);
    }

    /**
     * 物理删除那些已删除的（即收件人和发件人 同时都删除了的）
     * 
     * @param deletedState
     */
    public Integer clearDeletedMessage(MessageState deletedState) {
        return messageDao.clearDeletedMessage(deletedState);
    }

    /**
     * 更改状态,在changeDate-expireDays的这个日期前的消息，并且消息状态为oldStates中的一种，将会改变成新的消息状态newState，以及新的消息改变时间；
     * 
     * @param oldStates
     * @param newState
     * @param expireDays 当前时间-过期天数 时间之前的消息将改变状态
     */
    public Integer changeState(ArrayList<MessageState> oldStates, MessageState newState, int expireDays) {
        Date changeDate = new Date();
        Integer count =
            messageDao.changeSenderState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
        count +=
            messageDao.changeReceiverState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
        return count;
    }

    /**
     * 统计用户收件箱未读消息
     * 
     * @param userId
     * @return
     */
    public Long countUnread(Long userId) {
        return messageDao.countUnread(userId, MessageState.in_box);
    }

    public void markRead(final Long userId, final Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return;
        }
        messageDao.markRead(userId, Arrays.asList(ids));
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(messageDao);
    }
}
