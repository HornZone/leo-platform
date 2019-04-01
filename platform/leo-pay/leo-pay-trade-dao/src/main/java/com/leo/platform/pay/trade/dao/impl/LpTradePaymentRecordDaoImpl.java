package com.leo.platform.pay.trade.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leo.platform.pay.common.dao.impl.BaseDaoImpl;
import com.leo.platform.pay.trade.dao.LpTradePaymentRecordDao;
import com.leo.platform.pay.trade.entity.LpTradePaymentRecord;
import com.leo.platform.pay.trade.enums.TradeStatusEnum;

/**
 * <b>功能说明:商户支付记录,dao层实现类</b>
 * 
 */
@Repository("lpTradePaymentRecordDao")
public class LpTradePaymentRecordDaoImpl extends BaseDaoImpl<LpTradePaymentRecord> implements LpTradePaymentRecordDao {

    /**
     * 根据银行订单号获取支付信息
     * 
     * @param bankOrderNo
     * @return
     */
    @Override
    public LpTradePaymentRecord getByBankOrderNo(String bankOrderNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bankOrderNo", bankOrderNo);
        return super.getBy(paramMap);
    }

    /**
     * 根据商户编号及商户订单号获取支付结果
     * 
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    @Override
    public LpTradePaymentRecord
        getSuccessRecordByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", TradeStatusEnum.SUCCESS.name());
        paramMap.put("merchantNo", merchantNo);
        paramMap.put("merchantOrderNo", merchantOrderNo);
        return super.getBy(paramMap);
    }

    /**
     * 根据支付流水号查询支付记录
     * 
     * @param trxNo
     * @return
     */
    public LpTradePaymentRecord getByTrxNo(String trxNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("trxNo", trxNo);
        return super.getBy(paramMap);
    }

    public List<Map<String, String>> getPaymentReport(String merchantNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", TradeStatusEnum.SUCCESS.name());
        paramMap.put("merchantNo", merchantNo);
        return super.getSqlSession().selectList(getStatement("getPaymentReport"), paramMap);
    }

    public List<Map<String, String>> getPayWayReport(String merchantNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", TradeStatusEnum.SUCCESS.name());
        paramMap.put("merchantNo", merchantNo);
        return super.getSqlSession().selectList(getStatement("getPayWayReport"), paramMap);
    }

}
