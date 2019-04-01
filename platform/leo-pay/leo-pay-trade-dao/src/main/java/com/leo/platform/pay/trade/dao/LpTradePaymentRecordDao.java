package com.leo.platform.pay.trade.dao;

import java.util.List;
import java.util.Map;

import com.leo.platform.pay.common.dao.BaseDao;
import com.leo.platform.pay.trade.entity.LpTradePaymentRecord;

/**
 * <b>功能说明:商户支付记录,dao层接口</b>
 */
public interface LpTradePaymentRecordDao extends BaseDao<LpTradePaymentRecord> {

    /**
     * 根据银行订单号获取支付信息
     * 
     * @param bankOrderNo
     * @return
     */
    LpTradePaymentRecord getByBankOrderNo(String bankOrderNo);

    /**
     * 根据商户编号及商户订单号获取支付成功的结果
     * 
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    LpTradePaymentRecord getSuccessRecordByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo);

    /**
     * 根据支付流水号查询支付记录
     * 
     * @param trxNo
     * @return
     */
    LpTradePaymentRecord getByTrxNo(String trxNo);

    List<Map<String, String>> getPaymentReport(String merchantNo);

    List<Map<String, String>> getPayWayReport(String merchantNo);

}
