package com.leo.platform.pay.trade.dao;

import com.leo.platform.pay.common.dao.BaseDao;
import com.leo.platform.pay.trade.entity.LpTradePaymentOrder;

/**
 * <b>功能说明:商户支付订单,dao层接口</b>
 */
public interface LpTradePaymentOrderDao extends BaseDao<LpTradePaymentOrder> {

    /**
     * 根据商户编号及商户订单号获取支付订单信息
     * 
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    LpTradePaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo);

}
