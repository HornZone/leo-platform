package com.leo.platform.pay.notify.service.impl;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.leo.platform.common.config.MqConfig;
import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.notify.dao.LpNotifyRecordDao;
import com.leo.platform.pay.notify.dao.LpNotifyRecordLogDao;
import com.leo.platform.pay.notify.entity.LeoPayNotifyRecord;
import com.leo.platform.pay.notify.entity.LeoPayNotifyRecordLog;
import com.leo.platform.pay.notify.enums.NotifyStatusEnum;
import com.leo.platform.pay.notify.enums.NotifyTypeEnum;
import com.leo.platform.pay.notify.service.LeoPayNotifyService;

/** 
 */
@Service("leoPayNotifyService")
public class LeoPayNotifyServiceImpl implements LeoPayNotifyService {

    @Autowired
    private JmsTemplate notifyJmsTemplate;

    @Autowired
    private LpNotifyRecordDao lpNotifyRecordDao;

    @Autowired
    private LpNotifyRecordLogDao lpNotifyRecordLogDao;

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 发送消息通知
     * 
     * @param notifyUrl 通知地址
     * @param merchantOrderNo 商户订单号
     * @param merchantNo 商户编号
     */
    @Override
    public void notifySend(String notifyUrl, String merchantOrderNo, String merchantNo) {

        LeoPayNotifyRecord record = new LeoPayNotifyRecord();
        record.setNotifyTimes(0);
        record.setLimitNotifyTimes(5);
        record.setStatus(NotifyStatusEnum.CREATED.name());
        record.setUrl(notifyUrl);
        record.setMerchantOrderNo(merchantOrderNo);
        record.setMerchantNo(merchantNo);
        record.setNotifyType(NotifyTypeEnum.MERCHANT.name());

        Object toJSON = JSONObject.toJSON(record);
        final String str = toJSON.toString();

        notifyJmsTemplate.setDefaultDestinationName(MqConfig.MERCHANT_NOTIFY_QUEUE);
        notifyJmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(str);
            }
        });
    }

    /**
     * 订单通知
     * 
     * @param merchantOrderNo
     */
    @Override
    public void orderSend(String bankOrderNo) {
        final String orderNo = bankOrderNo;

        jmsTemplate.setDefaultDestinationName(MqConfig.ORDER_NOTIFY_QUEUE);
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderNo);
            }
        });
    }

    /**
     * 通过ID获取通知记录
     * 
     * @param id
     * @return
     */
    @Override
    public LeoPayNotifyRecord getNotifyRecordById(String id) {
        return lpNotifyRecordDao.getById(id);
    }

    /**
     * 根据商户编号,商户订单号,通知类型获取通知记录
     * 
     * @param merchantNo 商户编号
     * @param merchantOrderNo 商户订单号
     * @param notifyType 消息类型
     * @return
     */
    @Override
    public LeoPayNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo,
        String merchantOrderNo, String notifyType) {
        return lpNotifyRecordDao.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(merchantNo, merchantOrderNo,
            notifyType);
    }

    @Override
    public PageBean<LeoPayNotifyRecord> queryNotifyRecordListPage(PageParam pageParam, Map<String, Object> paramMap) {
        return lpNotifyRecordDao.listPage(pageParam, paramMap);
    }

    /**
     * 创建消息通知
     * 
     * @param lpNotifyRecord
     */
    @Override
    public long createNotifyRecord(LeoPayNotifyRecord lpNotifyRecord) {
        return lpNotifyRecordDao.insert(lpNotifyRecord);
    }

    /**
     * 修改消息通知
     * 
     * @param lpNotifyRecord
     */
    @Override
    public void updateNotifyRecord(LeoPayNotifyRecord lpNotifyRecord) {
        lpNotifyRecordDao.update(lpNotifyRecord);
    }

    /**
     * 创建消息通知记录
     * 
     * @param lpNotifyRecordLog
     * @return
     */
    @Override
    public long createNotifyRecordLog(LeoPayNotifyRecordLog lpNotifyRecordLog) {
        return lpNotifyRecordLogDao.insert(lpNotifyRecordLog);
    }

}
