package com.leo.platform.pay.user.dao;

import com.leo.platform.pay.common.dao.BaseDao;
import com.leo.platform.pay.user.entity.LpUserPayConfig;

/**
 * 用户支付配置dao
 */
public interface LpUserPayConfigDao extends BaseDao<LpUserPayConfig> {

    /**
     * 根据用户编号获取用户支付信息
     * 
     * @param userNo
     * @return
     */
    LpUserPayConfig getByUserNo(String userNo, String auditStatus);

}