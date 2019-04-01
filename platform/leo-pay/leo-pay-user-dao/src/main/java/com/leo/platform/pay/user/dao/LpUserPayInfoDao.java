package com.leo.platform.pay.user.dao;

import com.leo.platform.pay.common.dao.BaseDao;
import com.leo.platform.pay.user.entity.LpUserPayInfo;

/**
 * 用户第三方支付信息dao
 */
public interface LpUserPayInfoDao extends BaseDao<LpUserPayInfo> {

    /**
     * 通过商户编号获取商户第三方支付信息
     * 
     * @param userNo
     * @param payWayCode
     * @return
     */
    public LpUserPayInfo getByUserNo(String userNo, String payWayCode);

}