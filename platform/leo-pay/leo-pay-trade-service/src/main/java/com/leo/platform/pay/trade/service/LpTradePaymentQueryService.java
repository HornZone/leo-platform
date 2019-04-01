package com.leo.platform.pay.trade.service;

import java.util.List;
import java.util.Map;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.trade.entity.LpTradePaymentOrder;
import com.leo.platform.pay.trade.entity.LpTradePaymentRecord;
import com.leo.platform.pay.trade.vo.OrderPayResultVo;
import com.leo.platform.pay.trade.vo.PaymentOrderQueryParam;

/**
 * <b>功能说明:交易模块查询接口</b>
 */
public interface LpTradePaymentQueryService {

    /**
     * 根据参数查询交易记录List
     * 
     * @param paremMap
     * @return
     */
    public List<LpTradePaymentRecord> listPaymentRecord(Map<String, Object> paremMap);

    /**
     * 根据商户支付KEY 及商户订单号 查询支付结果
     * 
     * @param payKey 商户支付KEY
     * @param orderNo 商户订单号
     * @return
     */
    public OrderPayResultVo getPayResult(String payKey, String orderNo);

    /**
     * 根据银行订单号查询支付记录
     * 
     * @param bankOrderNo
     * @return
     */
    public LpTradePaymentRecord getRecordByBankOrderNo(String bankOrderNo);

    /**
     * 根据支付流水号查询支付记录
     * 
     * @param trxNo
     * @return
     */
    public LpTradePaymentRecord getRecordByTrxNo(String trxNo);

    /**
     * 分页查询支付订单
     * 
     * @param pageParam
     * @param paymentOrderQueryParam
     * @return
     */
    public PageBean<LpTradePaymentOrder> listPaymentOrderPage(PageParam pageParam,
        PaymentOrderQueryParam paymentOrderQueryParam);

    /**
     * 分页查询支付记录
     * 
     * @param pageParam
     * @param paymentOrderQueryParam
     * @return
     */
    public PageBean<LpTradePaymentRecord> listPaymentRecordPage(PageParam pageParam,
        PaymentOrderQueryParam paymentOrderQueryParam);

    /**
     * 获取交易流水报表
     * 
     * @param merchantNo
     * @return
     */
    public List<Map<String, String>> getPaymentReport(String merchantNo);

    /**
     * 获取交易方式报表
     * 
     * @param merchantNo
     * @return
     */
    public List<Map<String, String>> getPayWayReport(String merchantNo);

}
