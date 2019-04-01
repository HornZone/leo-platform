package com.leo.platform.pay.user.service;

/**
 * 生成编号service接口
 */
public interface BuildNoService {

    /** 获取用户编号 **/
    String buildUserNo();

    /** 获取账户编号 **/
    String buildAccountNo();

    /** 获取支付流水号 **/
    String buildTrxNo();

    /** 获取银行订单号 **/
    String buildBankOrderNo();

    /** 获取对账批次号 **/
    String buildReconciliationNo();

}
