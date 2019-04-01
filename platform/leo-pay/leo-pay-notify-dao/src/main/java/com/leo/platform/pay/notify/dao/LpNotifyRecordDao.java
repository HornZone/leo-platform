package com.leo.platform.pay.notify.dao;

import com.leo.platform.pay.common.dao.BaseDao;
import com.leo.platform.pay.notify.entity.LeoPayNotifyRecord;

/** 
 */
public interface LpNotifyRecordDao extends BaseDao<LeoPayNotifyRecord> {

    LeoPayNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo,
        String notifyType);

    int deleteByPrimaryKey(String id);

    int insertSelective(LeoPayNotifyRecord record);

    LeoPayNotifyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKey(LeoPayNotifyRecord record);
}