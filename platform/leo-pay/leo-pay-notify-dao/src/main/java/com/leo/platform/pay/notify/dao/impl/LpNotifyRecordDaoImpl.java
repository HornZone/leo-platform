package com.leo.platform.pay.notify.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leo.platform.pay.common.dao.impl.BaseDaoImpl;
import com.leo.platform.pay.notify.dao.LpNotifyRecordDao;
import com.leo.platform.pay.notify.entity.LeoPayNotifyRecord;

/** 
 */
@Repository("lpNotifyRecordDao")
public class LpNotifyRecordDaoImpl extends BaseDaoImpl<LeoPayNotifyRecord> implements LpNotifyRecordDao {

    @Override
    public LeoPayNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo,
        String merchantOrderNo, String notifyType) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("merchantNo", merchantNo);
        paramMap.put("merchantOrderNo", merchantOrderNo);
        paramMap.put("notifyType", notifyType);

        return super.getBy(paramMap);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(LeoPayNotifyRecord record) {
        return 0;
    }

    @Override
    public LeoPayNotifyRecord selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKey(LeoPayNotifyRecord record) {
        return 0;
    }

}
