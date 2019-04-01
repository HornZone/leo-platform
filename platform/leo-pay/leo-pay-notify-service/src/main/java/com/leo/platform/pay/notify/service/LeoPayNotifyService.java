package com.leo.platform.pay.notify.service;

import java.util.Map;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.notify.entity.LeoPayNotifyRecord;
import com.leo.platform.pay.notify.entity.LeoPayNotifyRecordLog;

/** 
 */

public interface LeoPayNotifyService {

    /**
     * 发送消息通知
     * 
     * @param notifyUrl 通知地址
     * @param merchantOrderNo 商户订单号
     * @param merchantNo 商户编号
     */
    public void notifySend(String notifyUrl, String merchantOrderNo, String merchantNo);

    /**
     * 订单通知
     * 
     * @param bankOrderNo 订单编号
     */
    void orderSend(String bankOrderNo);

    /**
     * 通过ID获取通知记录
     * 
     * @param id
     * @return
     */
    public LeoPayNotifyRecord getNotifyRecordById(String id);

    /**
     * 根据商户编号,商户订单号,通知类型获取通知记录
     * 
     * @param merchantNo 商户编号
     * @param merchantOrderNo 商户订单号
     * @param notifyType 消息类型
     * @return
     */
    public LeoPayNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo,
        String merchantOrderNo, String notifyType);

    public PageBean<LeoPayNotifyRecord> queryNotifyRecordListPage(PageParam pageParam, Map<String, Object> paramMap);

    /**
     * 创建消息通知
     * 
     * @param rpNotifyRecord
     */
    public long createNotifyRecord(LeoPayNotifyRecord rpNotifyRecord);

    /**
     * 修改消息通知
     * 
     * @param rpNotifyRecord
     */
    public void updateNotifyRecord(LeoPayNotifyRecord rpNotifyRecord);

    /**
     * 创建消息通知记录
     * 
     * @param rpNotifyRecordLog
     * @return
     */
    public long createNotifyRecordLog(LeoPayNotifyRecordLog rpNotifyRecordLog);

}
