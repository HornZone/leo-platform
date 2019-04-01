package com.leo.platform.pay.notify.dao;

import com.leo.platform.pay.common.dao.BaseDao;
import com.leo.platform.pay.notify.entity.LeoPayNotifyRecordLog;

/** 
 */
public interface LpNotifyRecordLogDao extends BaseDao<LeoPayNotifyRecordLog> {

    int deleteByPrimaryKey(String id);

    int insertSelective(LeoPayNotifyRecordLog record);

    LeoPayNotifyRecordLog selectByPrimaryKey(String id);

    int updateByPrimaryKey(LeoPayNotifyRecordLog record);
}