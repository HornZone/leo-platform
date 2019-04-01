package com.leo.platform.pay.trade.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.platform.common.enums.PublicEnum;
import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.common.util.DateUtils;
import com.leo.platform.pay.trade.dao.LpTradePaymentOrderDao;
import com.leo.platform.pay.trade.dao.LpTradePaymentRecordDao;
import com.leo.platform.pay.trade.entity.LpTradePaymentOrder;
import com.leo.platform.pay.trade.entity.LpTradePaymentRecord;
import com.leo.platform.pay.trade.enums.TradeStatusEnum;
import com.leo.platform.pay.trade.service.LpTradePaymentQueryService;
import com.leo.platform.pay.trade.utils.MerchantApiUtil;
import com.leo.platform.pay.trade.vo.OrderPayResultVo;
import com.leo.platform.pay.trade.vo.PaymentOrderQueryParam;
import com.leo.platform.pay.user.entity.LpUserPayConfig;
import com.leo.platform.pay.user.service.LpUserPayConfigService;
import com.platform.pay.user.exception.UserBizException;

/**
 * <b>功能说明:交易模块查询类实现</b>
 */

@Service("lpTradePaymentQueryService")
public class LpTradePaymentQueryServiceImpl implements LpTradePaymentQueryService {
    @Autowired
    private LpTradePaymentRecordDao lpTradePaymentRecordDao;

    @Autowired
    private LpTradePaymentOrderDao lpTradePaymentOrderDao;

    @Autowired
    private LpUserPayConfigService lpUserPayConfigService;

    /**
     * 根据参数查询交易记录List
     * 
     * @param paramMap
     * @return
     */
    public List<LpTradePaymentRecord> listPaymentRecord(Map<String, Object> paramMap) {
        return lpTradePaymentRecordDao.listByColumn(paramMap);
    }

    /**
     * 根据商户支付KEY 及商户订单号 查询支付结果
     * 
     * @param payKey 商户支付KEY
     * @param orderNo 商户订单号
     * @return
     */
    @Override
    public OrderPayResultVo getPayResult(String payKey, String orderNo) {

        LpUserPayConfig lpUserPayConfig = lpUserPayConfigService.getByPayKey(payKey);
        if (lpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = lpUserPayConfig.getUserNo();// 商户编号
        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);

        LpTradePaymentRecord lpTradePaymentRecord =
            lpTradePaymentRecordDao.getSuccessRecordByMerchantNoAndMerchantOrderNo(lpTradePaymentOrder.getMerchantNo(),
                lpTradePaymentOrder.getMerchantOrderNo());

        OrderPayResultVo orderPayResultVo = new OrderPayResultVo();// 返回结果
        if (lpTradePaymentOrder != null && TradeStatusEnum.SUCCESS.name().equals(lpTradePaymentOrder.getStatus())) {// 支付记录为空,或者支付状态非成功
            orderPayResultVo.setStatus(PublicEnum.YES.name());// 设置支付状态
            orderPayResultVo.setOrderPrice(lpTradePaymentOrder.getOrderAmount());// 设置支付订单
            orderPayResultVo.setProductName(lpTradePaymentOrder.getProductName());// 设置产品名称
            String url =
                getMerchantNotifyUrl(lpTradePaymentRecord, lpTradePaymentOrder, lpTradePaymentRecord.getReturnUrl(),
                    TradeStatusEnum.SUCCESS);
            orderPayResultVo.setReturnUrl(url);
        }

        return orderPayResultVo;
    }

    private String getMerchantNotifyUrl(LpTradePaymentRecord lpTradePaymentRecord,
        LpTradePaymentOrder lpTradePaymentOrder, String sourceUrl, TradeStatusEnum tradeStatusEnum) {

        LpUserPayConfig lpUserPayConfig = lpUserPayConfigService.getByUserNo(lpTradePaymentRecord.getMerchantNo());
        if (lpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        Map<String, Object> paramMap = new HashMap<>();

        String payKey = lpUserPayConfig.getPayKey();// 企业支付KEY
        paramMap.put("payKey", payKey);
        String productName = lpTradePaymentRecord.getProductName(); // 商品名称
        paramMap.put("productName", productName);
        String orderNo = lpTradePaymentRecord.getMerchantOrderNo(); // 订单编号
        paramMap.put("orderNo", orderNo);
        BigDecimal orderPrice = lpTradePaymentRecord.getOrderAmount(); // 订单金额 , 单位:元
        paramMap.put("orderPrice", orderPrice);
        String payWayCode = lpTradePaymentRecord.getPayWayCode(); // 支付方式编码 支付宝: ALIPAY 微信:WEIXIN
        paramMap.put("payWayCode", payWayCode);
        paramMap.put("tradeStatus", tradeStatusEnum);// 交易状态
        String orderDateStr = DateUtils.formatDate(lpTradePaymentOrder.getOrderDate(), "yyyyMMdd"); // 订单日期
        paramMap.put("orderDate", orderDateStr);
        String orderTimeStr = DateUtils.formatDate(lpTradePaymentOrder.getOrderTime(), "yyyyMMddHHmmss"); // 订单时间
        paramMap.put("orderTime", orderTimeStr);
        String remark = lpTradePaymentRecord.getRemark(); // 支付备注
        paramMap.put("remark", remark);
        String trxNo = lpTradePaymentRecord.getTrxNo();// 支付流水号
        paramMap.put("trxNo", trxNo);

        String field1 = lpTradePaymentOrder.getField1(); // 扩展字段1
        paramMap.put("field1", field1);
        String field2 = lpTradePaymentOrder.getField2(); // 扩展字段2
        paramMap.put("field2", field2);
        String field3 = lpTradePaymentOrder.getField3(); // 扩展字段3
        paramMap.put("field3", field3);
        String field4 = lpTradePaymentOrder.getField4(); // 扩展字段4
        paramMap.put("field4", field4);
        String field5 = lpTradePaymentOrder.getField5(); // 扩展字段5
        paramMap.put("field5", field5);

        String paramStr = MerchantApiUtil.getParamStr(paramMap);
        String sign = MerchantApiUtil.getSign(paramMap, lpUserPayConfig.getPaySecret());
        String notifyUrl = sourceUrl + "?" + paramStr + "&sign=" + sign;

        return notifyUrl;
    }

    /**
     * 根据银行订单号查询支付记录
     * 
     * @param bankOrderNo
     * @return
     */
    public LpTradePaymentRecord getRecordByBankOrderNo(String bankOrderNo) {
        return lpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
    }

    /**
     * 根据支付流水号查询支付记录
     * 
     * @param trxNo
     * @return
     */
    public LpTradePaymentRecord getRecordByTrxNo(String trxNo) {
        return lpTradePaymentRecordDao.getByTrxNo(trxNo);
    }

    /**
     * 分页查询支付订单
     * 
     * @param pageParam
     * @param paymentOrderQueryParam
     * @return
     */
    @Override
    public PageBean<LpTradePaymentOrder> listPaymentOrderPage(PageParam pageParam,
        PaymentOrderQueryParam paymentOrderQueryParam) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("merchantNo", paymentOrderQueryParam.getMerchantNo());// 商户编号
        paramMap.put("merchantName", paymentOrderQueryParam.getMerchantName());// 商户名称
        paramMap.put("merchantOrderNo", paymentOrderQueryParam.getMerchantOrderNo());// 商户订单号
        paramMap.put("fundIntoType", paymentOrderQueryParam.getFundIntoType());// 资金流入类型
        paramMap.put("merchantOrderNo", paymentOrderQueryParam.getOrderDateBegin());// 订单开始时间
        paramMap.put("merchantOrderNo", paymentOrderQueryParam.getOrderDateEnd());// 订单结束时间
        paramMap.put("payTypeName", paymentOrderQueryParam.getPayTypeName());// 支付类型
        paramMap.put("payWayName", paymentOrderQueryParam.getPayWayName());// 支付类型
        paramMap.put("status", paymentOrderQueryParam.getStatus());// 支付状态

        if (paymentOrderQueryParam.getOrderDateBegin() != null) {
            paramMap.put("orderDateBegin", paymentOrderQueryParam.getOrderDateBegin());// 支付状态
        }

        if (paymentOrderQueryParam.getOrderDateEnd() != null) {
            paramMap.put("orderDateEnd", paymentOrderQueryParam.getOrderDateEnd());// 支付状态
        }

        return lpTradePaymentOrderDao.listPage(pageParam, paramMap);
    }

    /**
     * 分页查询支付记录
     * 
     * @param pageParam
     * @param paymentOrderQueryParam
     * @return
     */
    @Override
    public PageBean<LpTradePaymentRecord> listPaymentRecordPage(PageParam pageParam,
        PaymentOrderQueryParam paymentOrderQueryParam) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("merchantNo", paymentOrderQueryParam.getMerchantNo());// 商户编号
        paramMap.put("merchantName", paymentOrderQueryParam.getMerchantName());// 商户名称
        paramMap.put("merchantOrderNo", paymentOrderQueryParam.getMerchantOrderNo());// 商户订单号
        paramMap.put("fundIntoType", paymentOrderQueryParam.getFundIntoType());// 资金流入类型
        paramMap.put("merchantOrderNo", paymentOrderQueryParam.getOrderDateBegin());// 订单开始时间
        paramMap.put("merchantOrderNo", paymentOrderQueryParam.getOrderDateEnd());// 订单结束时间
        paramMap.put("payTypeName", paymentOrderQueryParam.getPayTypeName());// 支付类型
        paramMap.put("payWayName", paymentOrderQueryParam.getPayWayName());// 支付类型
        paramMap.put("status", paymentOrderQueryParam.getStatus());// 支付状态

        if (paymentOrderQueryParam.getOrderDateBegin() != null) {
            paramMap.put("orderDateBegin", paymentOrderQueryParam.getOrderDateBegin());// 支付状态
        }

        if (paymentOrderQueryParam.getOrderDateEnd() != null) {
            paramMap.put("orderDateEnd", paymentOrderQueryParam.getOrderDateEnd());// 支付状态
        }

        return lpTradePaymentRecordDao.listPage(pageParam, paramMap);
    }

    /**
     * 获取交易流水报表
     * 
     * @param merchantNo
     * @return
     */
    public List<Map<String, String>> getPaymentReport(String merchantNo) {
        return lpTradePaymentRecordDao.getPaymentReport(merchantNo);
    }

    /**
     * 获取交易方式报表
     * 
     * @param merchantNo
     * @return
     */
    public List<Map<String, String>> getPayWayReport(String merchantNo) {
        return lpTradePaymentRecordDao.getPayWayReport(merchantNo);
    }
}
