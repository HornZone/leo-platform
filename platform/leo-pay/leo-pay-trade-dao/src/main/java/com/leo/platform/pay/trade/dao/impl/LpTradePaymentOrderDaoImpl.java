package com.leo.platform.pay.trade.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leo.platform.pay.common.dao.impl.BaseDaoImpl;
import com.leo.platform.pay.trade.dao.LpTradePaymentOrderDao;
import com.leo.platform.pay.trade.entity.LpTradePaymentOrder;

/**
 * <b>功能说明:商户支付订单,dao层实现类</b>
 * 
 */
@Repository("lpTradePaymentOrderDao")
public class LpTradePaymentOrderDaoImpl extends BaseDaoImpl<LpTradePaymentOrder> implements LpTradePaymentOrderDao {

    /**
     * 根据商户编号及商户订单号获取支付订单信息
     * 
     * @param merchantNo 商户编号
     * @param merchantOrderNo 商户订单号
     * @return
     */
    @Override
    public LpTradePaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("merchantNo", merchantNo);
        paramMap.put("merchantOrderNo", merchantOrderNo);
        return super.getBy(paramMap);
    }

}
