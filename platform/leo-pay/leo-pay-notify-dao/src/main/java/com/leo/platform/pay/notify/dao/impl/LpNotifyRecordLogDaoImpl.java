package com.leo.platform.pay.notify.dao.impl;

import org.springframework.stereotype.Repository;

import com.leo.platform.pay.common.dao.impl.BaseDaoImpl;
import com.leo.platform.pay.notify.dao.LpNotifyRecordLogDao;
import com.leo.platform.pay.notify.entity.LeoPayNotifyRecordLog;

/** 
 */
@Repository("lpNotifyRecordLogDao")
public class LpNotifyRecordLogDaoImpl extends BaseDaoImpl<LeoPayNotifyRecordLog> implements LpNotifyRecordLogDao {
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(LeoPayNotifyRecordLog record) {
        return 0;
    }

    @Override
    public LeoPayNotifyRecordLog selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKey(LeoPayNotifyRecordLog record) {
        return 0;
    }
}
