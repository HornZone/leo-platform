package com.leo.platform.pay.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.leo.platform.common.enums.PayTypeEnum;
import com.leo.platform.common.enums.PayWayEnum;
import com.leo.platform.common.enums.PublicEnum;
import com.leo.platform.common.util.DateUtils;
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.pay.account.service.LpAccountTransactionService;
import com.leo.platform.pay.notify.service.LeoPayNotifyService;
import com.leo.platform.pay.trade.dao.LpTradePaymentOrderDao;
import com.leo.platform.pay.trade.dao.LpTradePaymentRecordDao;
import com.leo.platform.pay.trade.entity.LeoPayGoodsDetails;
import com.leo.platform.pay.trade.entity.LpTradePaymentOrder;
import com.leo.platform.pay.trade.entity.LpTradePaymentRecord;
import com.leo.platform.pay.trade.entity.weixinpay.WeiXinPrePay;
import com.leo.platform.pay.trade.enums.OrderFromEnum;
import com.leo.platform.pay.trade.enums.TradeStatusEnum;
import com.leo.platform.pay.trade.enums.TrxTypeEnum;
import com.leo.platform.pay.trade.enums.alipay.AliPayTradeStateEnum;
import com.leo.platform.pay.trade.enums.weixinpay.WeiXinTradeTypeEnum;
import com.leo.platform.pay.trade.enums.weixinpay.WeixinTradeStateEnum;
import com.leo.platform.pay.trade.exception.TradeBizException;
import com.leo.platform.pay.trade.service.LpTradePaymentManagerService;
import com.leo.platform.pay.trade.utils.MerchantApiUtil;
import com.leo.platform.pay.trade.utils.WeiXinPayUtils;
import com.leo.platform.pay.trade.utils.WeixinConfigUtil;
import com.leo.platform.pay.trade.utils.alipay.AliPayUtil;
import com.leo.platform.pay.trade.utils.alipay.config.AlipayConfigUtil;
import com.leo.platform.pay.trade.utils.alipay.util.AlipayNotify;
import com.leo.platform.pay.trade.utils.alipay.util.AlipaySubmit;
import com.leo.platform.pay.trade.utils.weixin.WeiXinPayUtil;
import com.leo.platform.pay.trade.vo.F2FPayResultVo;
import com.leo.platform.pay.trade.vo.LpPayGateWayPageShowVo;
import com.leo.platform.pay.trade.vo.OrderPayResultVo;
import com.leo.platform.pay.trade.vo.ProgramPayResultVo;
import com.leo.platform.pay.trade.vo.ScanPayResultVo;
import com.leo.platform.pay.user.entity.LpPayWay;
import com.leo.platform.pay.user.entity.LpUserInfo;
import com.leo.platform.pay.user.entity.LpUserPayConfig;
import com.leo.platform.pay.user.entity.LpUserPayInfo;
import com.leo.platform.pay.user.enums.FundInfoTypeEnum;
import com.leo.platform.pay.user.service.BuildNoService;
import com.leo.platform.pay.user.service.LpPayWayService;
import com.leo.platform.pay.user.service.LpUserInfoService;
import com.leo.platform.pay.user.service.LpUserPayConfigService;
import com.leo.platform.pay.user.service.LpUserPayInfoService;
import com.platform.pay.user.exception.UserBizException;

/**
 * <b>功能说明:交易模块管理实现类实现</b>
 * 
 */
@Service("lpTradePaymentManagerService")
public class LpTradePaymentManagerServiceImpl implements LpTradePaymentManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(LpTradePaymentManagerServiceImpl.class);

    @Autowired
    private LpTradePaymentOrderDao lpTradePaymentOrderDao;

    @Autowired
    private LpTradePaymentRecordDao lpTradePaymentRecordDao;

    @Autowired
    private LpUserInfoService lpUserInfoService;

    @Autowired
    private LpUserPayInfoService lpUserPayInfoService;

    @Autowired
    private LpUserPayConfigService lpUserPayConfigService;

    @Autowired
    private LpPayWayService lpPayWayService;

    @Autowired
    private BuildNoService buildNoService;

    @Autowired
    private LeoPayNotifyService lpNotifyService;

    @Autowired
    private LpAccountTransactionService lpAccountTransactionService;

    /*@Autowired
    private AliF2FPaySubmit aliF2FPaySubmit;*/

    /**
     * 初始化直连扫码支付数据,直连扫码支付初始化方法规则 1:根据(商户编号 + 商户订单号)确定订单是否存在 1.1:如果订单存在,抛异常,提示订单已存在 1.2:如果订单不存在,创建支付订单 2:创建支付记录
     * 3:根据相应渠道方法 4:调转到相应支付渠道扫码界面
     * 
     * @param payKey 商户支付KEY
     * @param productName 产品名称
     * @param orderNo 商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice 订单金额(元)
     * @param payWayCode 支付方式编码
     * @param orderIp 下单IP
     * @param orderPeriod 订单有效期(分钟)
     * @param returnUrl 支付结果页面通知地址
     * @param notifyUrl 支付结果后台通知地址
     * @param remark 支付备注
     * @param field1 扩展字段1
     * @param field2 扩展字段2
     * @param field3 扩展字段3
     * @param field4 扩展字段4
     * @param field5 扩展字段5
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScanPayResultVo initDirectScanPay(String payKey, String productName, String orderNo, Date orderDate,
        Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, Integer orderPeriod,
        String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4,
        String field5) {

        LpUserPayConfig lpUserPayConfig = lpUserPayConfigService.getByPayKey(payKey);
        if (lpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        // 根据支付产品及支付方式获取费率
        LpPayWay payWay = null;
        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {
            payWay =
                lpPayWayService.getByPayWayTypeCode(lpUserPayConfig.getProductCode(), payWayCode,
                    PayTypeEnum.SCANPAY.name());
            payType = PayTypeEnum.SCANPAY;
        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {
            payWay =
                lpPayWayService.getByPayWayTypeCode(lpUserPayConfig.getProductCode(), payWayCode,
                    PayTypeEnum.DIRECT_PAY.name());
            payType = PayTypeEnum.DIRECT_PAY;
        }

        if (payWay == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = lpUserPayConfig.getUserNo();// 商户编号
        LpUserInfo lpUserInfo = lpUserInfoService.getDataByMerchentNo(merchantNo);
        if (lpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (lpTradePaymentOrder == null) {
            lpTradePaymentOrder =
                sealLpTradePaymentOrder(merchantNo, lpUserInfo.getUserName(), productName, orderNo, orderDate,
                    orderTime, orderPrice, payWayCode, PayWayEnum.getEnum(payWayCode).getDesc(), payType,
                    lpUserPayConfig.getFundIntoType(), orderIp, orderPeriod, returnUrl, notifyUrl, remark, field1,
                    field2, field3, field4, field5);
            lpTradePaymentOrderDao.insert(lpTradePaymentOrder);
        } else {
            if (TradeStatusEnum.SUCCESS.name().equals(lpTradePaymentOrder.getStatus())) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
            }
            if (lpTradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0) {
                lpTradePaymentOrder.setOrderAmount(orderPrice);// 如果金额不一致,修改金额为最新的金额
            }
        }

        return getScanPayResultVo(lpTradePaymentOrder, payWay);

    }

    /**
     * 条码支付,对应的是支付宝的条码支付或者微信的刷卡支付
     * 
     * @param payKey 商户支付key
     * @param authCode 支付授权码
     * @param productName 产品名称
     * @param orderNo 商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice 订单金额(元)
     * @param payWayCode 支付方式
     * @param orderIp 下单IP
     * @param remark 支付备注
     * @param field1 扩展字段1
     * @param field2 扩展字段2
     * @param field3 扩展字段3
     * @param field4 扩展字段4
     * @param field5 扩展字段5
     * @return
     */
    @Override
    public F2FPayResultVo f2fPay(String payKey, String authCode, String productName, String orderNo, Date orderDate,
        Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, String remark, String field1,
        String field2, String field3, String field4, String field5) {

        LpUserPayConfig lpUserPayConfig = lpUserPayConfigService.getByPayKey(payKey);
        if (lpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        if (StringUtil.isEmpty(authCode)) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "支付授权码不能为空");
        }
        // 根据支付产品及支付方式获取费率
        LpPayWay payWay = null;
        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {
            payWay =
                lpPayWayService.getByPayWayTypeCode(lpUserPayConfig.getProductCode(), payWayCode,
                    PayTypeEnum.MICRO_PAY.name());
            payType = PayTypeEnum.MICRO_PAY;
        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {
            payWay =
                lpPayWayService.getByPayWayTypeCode(lpUserPayConfig.getProductCode(), payWayCode,
                    PayTypeEnum.F2F_PAY.name());
            payType = PayTypeEnum.F2F_PAY;
        }
        if (payWay == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = lpUserPayConfig.getUserNo();// 商户编号
        LpUserInfo lpUserInfo = lpUserInfoService.getDataByMerchentNo(merchantNo);
        if (lpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        // 根据商户号和订单号去查询订单是否存在
        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (lpTradePaymentOrder == null) {
            // 订单不存在，创建订单
            lpTradePaymentOrder =
                sealLpTradePaymentOrder(merchantNo, lpUserInfo.getUserName(), productName, orderNo, orderDate,
                    orderTime, orderPrice, payWayCode, PayWayEnum.getEnum(payWayCode).getDesc(), payType,
                    lpUserPayConfig.getFundIntoType(), orderIp, 5, "f2fPay", "f2fPay", remark, field1, field2, field3,
                    field4, field5);
            lpTradePaymentOrderDao.insert(lpTradePaymentOrder);
        } else {
            // 订单已存在，订单金额与传入金额不相等
            if (lpTradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "错误的订单");
            }
            // 订单已存在，且订单状态为支付成功
            if (TradeStatusEnum.SUCCESS.name().equals(lpTradePaymentOrder.getStatus())) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
            }
        }

        return getF2FPayResultVo(lpTradePaymentOrder, payWay, payKey, lpUserPayConfig.getPaySecret(), authCode, null);
    }

    /**
     * 通过支付订单及商户费率生成支付记录
     * 
     * @param lpTradePaymentOrder 支付订单
     * @param payWay 商户支付配置
     * @return
     */
    private F2FPayResultVo getF2FPayResultVo(LpTradePaymentOrder lpTradePaymentOrder, LpPayWay payWay, String payKey,
        String merchantPaySecret, String authCode, List<LeoPayGoodsDetails> roncooPayGoodsDetailses) {

        F2FPayResultVo f2FPayResultVo = new F2FPayResultVo();
        String payWayCode = payWay.getPayWayCode();// 支付方式

        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWay.getPayWayCode())) {
            payType = PayTypeEnum.MICRO_PAY;
        } else if (PayWayEnum.ALIPAY.name().equals(payWay.getPayWayCode())) {
            payType = PayTypeEnum.F2F_PAY;
        }

        lpTradePaymentOrder.setPayTypeCode(payType.name());// 支付类型
        lpTradePaymentOrder.setPayTypeName(payType.getDesc());// 支付方式
        lpTradePaymentOrder.setPayWayCode(payWay.getPayWayCode());// 支付通道编号
        lpTradePaymentOrder.setPayWayName(payWay.getPayWayName());// 支付通道名称

        // 生成支付流水
        LpTradePaymentRecord lpTradePaymentRecord =
            sealLpTradePaymentRecord(lpTradePaymentOrder.getMerchantNo(), lpTradePaymentOrder.getMerchantName(),
                lpTradePaymentOrder.getProductName(), lpTradePaymentOrder.getMerchantOrderNo(),
                lpTradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(), payWay.getPayWayName(), payType,
                lpTradePaymentOrder.getFundIntoType(), BigDecimal.valueOf(payWay.getPayRate()),
                lpTradePaymentOrder.getOrderIp(), lpTradePaymentOrder.getReturnUrl(),
                lpTradePaymentOrder.getNotifyUrl(), lpTradePaymentOrder.getRemark(), lpTradePaymentOrder.getField1(),
                lpTradePaymentOrder.getField2(), lpTradePaymentOrder.getField3(), lpTradePaymentOrder.getField4(),
                lpTradePaymentOrder.getField5());
        lpTradePaymentRecordDao.insert(lpTradePaymentRecord);

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {// 微信支付
            LpUserPayInfo lpUserPayInfo =
                lpUserPayInfoService.getByUserNo(lpTradePaymentOrder.getMerchantNo(), payWayCode);
            if (lpUserPayInfo == null) {
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "商户支付配置有误");
            }
            Map<String, Object> wxResultMap =
                WeiXinPayUtil.micropay(lpTradePaymentRecord.getBankOrderNo(), lpTradePaymentOrder.getProductName(),
                    lpTradePaymentRecord.getOrderAmount(), lpTradePaymentRecord.getOrderIp(), authCode);
            if (wxResultMap == null || wxResultMap.isEmpty()) {
                // 返回结果为空，支付结果未知需要轮询
                lpNotifyService.orderSend(lpTradePaymentRecord.getBankOrderNo());
            } else {
                if ("YES".equals(wxResultMap.get("verify"))) {
                    // 验签成功
                    if ("SUCCESS".equals(wxResultMap.get("return_code"))
                        && "SUCCESS".equals(wxResultMap.get("result_code"))) {
                        // 通讯成功且业务结果为成功
                        completeSuccessOrder(lpTradePaymentRecord, String.valueOf(wxResultMap.get("transaction_id")),
                            new Date(), "支付成功");
                    } else if ("SUCCESS".equals(wxResultMap.get("return_code"))
                        && !StringUtil.isEmpty(wxResultMap.get("err_code"))
                        && !"BANKERROR".equals(wxResultMap.get("err_code"))
                        && !"USERPAYING".equals(wxResultMap.get("err_code"))
                        && !"SYSTEMERROR".equals(wxResultMap.get("err_code"))) {
                        // 支付失败
                        completeFailOrder(lpTradePaymentRecord, String.valueOf(wxResultMap.get("err_code_des")));
                    } else {
                        // 返回结果未知，需要轮询
                        lpNotifyService.orderSend(lpTradePaymentRecord.getBankOrderNo());
                    }
                } else {
                    completeFailOrder(lpTradePaymentRecord, "签名校验失败!");
                    // 验签失败
                }
            }

        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {// 支付宝支付
            LpUserPayInfo lpUserPayInfo =
                lpUserPayInfoService.getByUserNo(lpTradePaymentOrder.getMerchantNo(), payWayCode);
            if (lpUserPayInfo == null) {
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "商户支付配置有误");
            }
            Map<String, Object> resultMap =
                AliPayUtil.tradePay(lpTradePaymentRecord.getBankOrderNo(), authCode,
                    lpTradePaymentOrder.getProductName(), lpTradePaymentRecord.getOrderAmount(), "",
                    roncooPayGoodsDetailses);
            // 支付条码支付--统一根据订单轮询去确认支付结果
            lpNotifyService.orderSend(lpTradePaymentRecord.getBankOrderNo());
        } else {
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "错误的支付方式");
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        f2FPayResultVo.setStatus(lpTradePaymentRecord.getStatus());// 支付结果
        paramMap.put("status", lpTradePaymentRecord.getStatus());

        f2FPayResultVo.setField1(lpTradePaymentRecord.getField1());// 扩展字段1
        paramMap.put("field1", lpTradePaymentRecord.getField1());

        f2FPayResultVo.setField2(lpTradePaymentRecord.getField2());// 扩展字段2
        paramMap.put("field2", lpTradePaymentRecord.getField2());

        f2FPayResultVo.setField3(lpTradePaymentRecord.getField3());// 扩展字段3
        paramMap.put("field3", lpTradePaymentRecord.getField3());

        f2FPayResultVo.setField4(lpTradePaymentRecord.getField4());// 扩展字段4
        paramMap.put("field4", lpTradePaymentRecord.getField4());

        f2FPayResultVo.setField5(lpTradePaymentRecord.getField5());// 扩展字段5
        paramMap.put("field5", lpTradePaymentRecord.getField5());

        f2FPayResultVo.setOrderIp(lpTradePaymentRecord.getOrderIp());// 下单ip
        paramMap.put("orderIp", lpTradePaymentRecord.getOrderIp());

        f2FPayResultVo.setOrderNo(lpTradePaymentRecord.getMerchantOrderNo());// 商户订单号
        paramMap.put("merchantOrderNo", lpTradePaymentRecord.getMerchantOrderNo());

        f2FPayResultVo.setPayKey(payKey);// 支付号
        paramMap.put("payKey", payKey);

        f2FPayResultVo.setProductName(lpTradePaymentRecord.getProductName());// 产品名称
        paramMap.put("productName", lpTradePaymentRecord.getProductName());

        f2FPayResultVo.setRemark(lpTradePaymentRecord.getRemark());// 支付备注
        paramMap.put("remark", lpTradePaymentRecord.getRemark());

        f2FPayResultVo.setTrxNo(lpTradePaymentRecord.getTrxNo());// 交易流水号
        paramMap.put("trxNo", lpTradePaymentRecord.getTrxNo());

        String sign = MerchantApiUtil.getSign(paramMap, merchantPaySecret);

        f2FPayResultVo.setSign(sign);
        return f2FPayResultVo;
    }

    /**
     * 支付成功方法
     * 
     * @param lpTradePaymentRecord
     */
    @Transactional(rollbackFor = Exception.class)
    void completeSuccessOrder(LpTradePaymentRecord lpTradePaymentRecord, String bankTrxNo, Date timeEnd,
        String bankReturnMsg) {
        LOG.info("订单支付成功!");
        lpTradePaymentRecord.setPaySuccessTime(timeEnd);
        lpTradePaymentRecord.setBankTrxNo(bankTrxNo);// 设置银行流水号
        lpTradePaymentRecord.setBankReturnMsg(bankReturnMsg);
        lpTradePaymentRecord.setStatus(TradeStatusEnum.SUCCESS.name());
        lpTradePaymentRecordDao.update(lpTradePaymentRecord);

        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(lpTradePaymentRecord.getMerchantNo(),
                lpTradePaymentRecord.getMerchantOrderNo());
        lpTradePaymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());
        lpTradePaymentOrder.setTrxNo(lpTradePaymentRecord.getTrxNo());// 设置支付平台支付流水号
        lpTradePaymentOrderDao.update(lpTradePaymentOrder);

        if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(lpTradePaymentRecord.getFundIntoType())) {
            lpAccountTransactionService.creditToAccount(lpTradePaymentRecord.getMerchantNo(), lpTradePaymentRecord
                .getOrderAmount().subtract(lpTradePaymentRecord.getPlatIncome()),
                lpTradePaymentRecord.getBankOrderNo(), lpTradePaymentRecord.getBankTrxNo(), lpTradePaymentRecord
                    .getTrxType(), lpTradePaymentRecord.getRemark());
        }

        if (PayTypeEnum.F2F_PAY.name().equals(lpTradePaymentOrder.getPayTypeCode())) {// 支付宝
            // 条码支付实时返回支付结果,不需要商户通知（修改后，条码支付结果通过订单轮询去确认订单状态，成功后通知商户）
            String notifyUrl =
                getMerchantNotifyUrl(lpTradePaymentRecord, lpTradePaymentOrder, lpTradePaymentRecord.getNotifyUrl(),
                    TradeStatusEnum.SUCCESS);
            lpNotifyService.notifySend(notifyUrl, lpTradePaymentRecord.getMerchantOrderNo(),
                lpTradePaymentRecord.getMerchantNo());
            // return;
        } else {
            String notifyUrl =
                getMerchantNotifyUrl(lpTradePaymentRecord, lpTradePaymentOrder, lpTradePaymentRecord.getNotifyUrl(),
                    TradeStatusEnum.SUCCESS);
            lpNotifyService.notifySend(notifyUrl, lpTradePaymentRecord.getMerchantOrderNo(),
                lpTradePaymentRecord.getMerchantNo());
        }
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
        BigDecimal orderPrice = lpTradePaymentRecord.getOrderAmount(); // 订单金额 ,
        // 单位:元
        paramMap.put("orderPrice", orderPrice);
        String payWayCode = lpTradePaymentRecord.getPayWayCode(); // 支付方式编码 支付宝:
        // ALIPAY
        // 微信:WEIXIN
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
     * 支付失败方法
     * 
     * @param lpTradePaymentRecord
     */
    private void completeFailOrder(LpTradePaymentRecord lpTradePaymentRecord, String bankReturnMsg) {
        lpTradePaymentRecord.setBankReturnMsg(bankReturnMsg);
        lpTradePaymentRecord.setStatus(TradeStatusEnum.FAILED.name());
        lpTradePaymentRecordDao.update(lpTradePaymentRecord);

        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(lpTradePaymentRecord.getMerchantNo(),
                lpTradePaymentRecord.getMerchantOrderNo());
        lpTradePaymentOrder.setStatus(TradeStatusEnum.FAILED.name());
        lpTradePaymentOrderDao.update(lpTradePaymentOrder);

        String notifyUrl =
            getMerchantNotifyUrl(lpTradePaymentRecord, lpTradePaymentOrder, lpTradePaymentRecord.getNotifyUrl(),
                TradeStatusEnum.FAILED);
        lpNotifyService.notifySend(notifyUrl, lpTradePaymentRecord.getMerchantOrderNo(),
            lpTradePaymentRecord.getMerchantNo());
    }

    /**
     * 初始化非直连扫码支付数据,非直连扫码支付初始化方法规则 1:根据(商户编号 + 商户订单号)确定订单是否存在 1.1:如果订单存在且为未支付,抛异常提示订单已存在 1.2:如果订单不存在,创建支付订单
     * 2:获取商户支付配置,跳转到支付网关,选择支付方式
     * 
     * @param payKey 商户支付KEY
     * @param productName 产品名称
     * @param orderNo 商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice 订单金额(元)
     * @param orderIp 下单IP
     * @param orderPeriod 订单有效期(分钟)
     * @param returnUrl 支付结果页面通知地址
     * @param notifyUrl 支付结果后台通知地址
     * @param remark 支付备注
     * @param field1 扩展字段1
     * @param field2 扩展字段2
     * @param field3 扩展字段3
     * @param field4 扩展字段4
     * @param field5 扩展字段5
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LpPayGateWayPageShowVo initNonDirectScanPay(String payKey, String productName, String orderNo,
        Date orderDate, Date orderTime, BigDecimal orderPrice, String orderIp, Integer orderPeriod, String returnUrl,
        String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {

        LpUserPayConfig lpUserPayConfig = lpUserPayConfigService.getByPayKey(payKey);
        if (lpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = lpUserPayConfig.getUserNo();// 商户编号
        LpUserInfo lpUserInfo = lpUserInfoService.getDataByMerchentNo(merchantNo);
        if (lpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        List<LpPayWay> payWayList = lpPayWayService.listByProductCode(lpUserPayConfig.getProductCode());
        if (payWayList == null || payWayList.size() <= 0) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "支付产品配置有误");
        }

        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (lpTradePaymentOrder == null) {
            lpTradePaymentOrder =
                sealLpTradePaymentOrder(merchantNo, lpUserInfo.getUserName(), productName, orderNo, orderDate,
                    orderTime, orderPrice, null, null, null, lpUserPayConfig.getFundIntoType(), orderIp, orderPeriod,
                    returnUrl, notifyUrl, remark, field1, field2, field3, field4, field5);
            lpTradePaymentOrderDao.insert(lpTradePaymentOrder);
        } else {

            if (TradeStatusEnum.SUCCESS.name().equals(lpTradePaymentOrder.getStatus())) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
            }

            if (lpTradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0) {
                lpTradePaymentOrder.setOrderAmount(orderPrice);// 如果金额不一致,修改金额为最新的金额
                lpTradePaymentOrderDao.update(lpTradePaymentOrder);
            }
        }

        LpPayGateWayPageShowVo payGateWayPageShowVo = new LpPayGateWayPageShowVo();
        payGateWayPageShowVo.setProductName(lpTradePaymentOrder.getProductName());// 产品名称
        payGateWayPageShowVo.setMerchantName(lpTradePaymentOrder.getMerchantName());// 商户名称
        payGateWayPageShowVo.setOrderAmount(lpTradePaymentOrder.getOrderAmount());// 订单金额
        payGateWayPageShowVo.setMerchantOrderNo(lpTradePaymentOrder.getMerchantOrderNo());// 商户订单号
        payGateWayPageShowVo.setPayKey(payKey);// 商户支付key

        Map<String, PayWayEnum> payWayEnumMap = new HashMap<String, PayWayEnum>();
        for (LpPayWay payWay : payWayList) {
            payWayEnumMap.put(payWay.getPayWayCode(), PayWayEnum.getEnum(payWay.getPayWayCode()));
        }

        payGateWayPageShowVo.setPayWayEnumMap(payWayEnumMap);

        return payGateWayPageShowVo;

    }

    /**
     * 非直连扫码支付,选择支付方式后,去支付
     * 
     * @param payKey
     * @param orderNo
     * @param payWayCode
     * @return
     */
    @Override
    public ScanPayResultVo toNonDirectScanPay(String payKey, String orderNo, String payWayCode) {

        LpUserPayConfig lpUserPayConfig = lpUserPayConfigService.getByPayKey(payKey);
        if (lpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        // 根据支付产品及支付方式获取费率
        LpPayWay payWay = null;
        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {
            payWay =
                lpPayWayService.getByPayWayTypeCode(lpUserPayConfig.getProductCode(), payWayCode,
                    PayTypeEnum.SCANPAY.name());
        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {
            payWay =
                lpPayWayService.getByPayWayTypeCode(lpUserPayConfig.getProductCode(), payWayCode,
                    PayTypeEnum.DIRECT_PAY.name());
        }

        if (payWay == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = lpUserPayConfig.getUserNo();// 商户编号
        LpUserInfo lpUserInfo = lpUserInfoService.getDataByMerchentNo(merchantNo);
        if (lpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        // 根据商户订单号获取订单信息
        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (lpTradePaymentOrder == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单不存在");
        }

        if (TradeStatusEnum.SUCCESS.name().equals(lpTradePaymentOrder.getStatus())) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
        }

        return getScanPayResultVo(lpTradePaymentOrder, payWay);

    }

    /**
     * 通过支付订单及商户费率生成支付记录
     * 
     * @param lpTradePaymentOrder 支付订单
     * @param payWay 商户支付配置
     * @return
     */
    private ScanPayResultVo getScanPayResultVo(LpTradePaymentOrder lpTradePaymentOrder, LpPayWay payWay) {

        ScanPayResultVo scanPayResultVo = new ScanPayResultVo();
        String payWayCode = payWay.getPayWayCode();// 支付方式

        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWay.getPayWayCode())) {
            payType = PayTypeEnum.SCANPAY;
        } else if (PayWayEnum.ALIPAY.name().equals(payWay.getPayWayCode())) {
            payType = PayTypeEnum.DIRECT_PAY;
        }

        lpTradePaymentOrder.setPayTypeCode(payType.name());
        lpTradePaymentOrder.setPayTypeName(payType.getDesc());
        lpTradePaymentOrder.setPayWayCode(payWay.getPayWayCode());
        lpTradePaymentOrder.setPayWayName(payWay.getPayWayName());
        lpTradePaymentOrderDao.update(lpTradePaymentOrder);

        LpTradePaymentRecord lpTradePaymentRecord =
            sealLpTradePaymentRecord(lpTradePaymentOrder.getMerchantNo(), lpTradePaymentOrder.getMerchantName(),
                lpTradePaymentOrder.getProductName(), lpTradePaymentOrder.getMerchantOrderNo(),
                lpTradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(), payWay.getPayWayName(), payType,
                lpTradePaymentOrder.getFundIntoType(), BigDecimal.valueOf(payWay.getPayRate()),
                lpTradePaymentOrder.getOrderIp(), lpTradePaymentOrder.getReturnUrl(),
                lpTradePaymentOrder.getNotifyUrl(), lpTradePaymentOrder.getRemark(), lpTradePaymentOrder.getField1(),
                lpTradePaymentOrder.getField2(), lpTradePaymentOrder.getField3(), lpTradePaymentOrder.getField4(),
                lpTradePaymentOrder.getField5());
        lpTradePaymentRecordDao.insert(lpTradePaymentRecord);

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {// 微信支付
            String appid = "";
            String mch_id = "";
            String partnerKey = "";
            if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(lpTradePaymentOrder.getFundIntoType())) {// 商户收款
                // 根据资金流向获取配置信息
                LpUserPayInfo lpUserPayInfo =
                    lpUserPayInfoService.getByUserNo(lpTradePaymentOrder.getMerchantNo(), payWayCode);
                appid = lpUserPayInfo.getAppId();
                mch_id = lpUserPayInfo.getMerchantId();
                partnerKey = lpUserPayInfo.getPartnerKey();
            } else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(lpTradePaymentOrder.getFundIntoType())) {// 平台收款
                appid = WeixinConfigUtil.readConfig("appId");
                mch_id = WeixinConfigUtil.readConfig("mch_id");
                partnerKey = WeixinConfigUtil.readConfig("partnerKey");
            }

            WeiXinPrePay weiXinPrePay =
                sealWeixinPerPay(appid, mch_id, lpTradePaymentOrder.getProductName(), lpTradePaymentOrder.getRemark(),
                    lpTradePaymentRecord.getBankOrderNo(), lpTradePaymentOrder.getOrderAmount(),
                    lpTradePaymentOrder.getOrderTime(), lpTradePaymentOrder.getOrderPeriod(),
                    WeiXinTradeTypeEnum.NATIVE, lpTradePaymentRecord.getBankOrderNo(), "",
                    lpTradePaymentOrder.getOrderIp());
            String prePayXml = WeiXinPayUtils.getPrePayXml(weiXinPrePay, partnerKey);
            LOG.info("扫码支付，微信请求报文:{}", prePayXml);
            // 调用微信支付的功能,获取微信支付code_url
            Map<String, Object> prePayRequest =
                WeiXinPayUtils.httpXmlRequest(WeixinConfigUtil.readConfig("prepay_url"), "POST", prePayXml);
            LOG.info("扫码支付，微信返回报文:{}", prePayRequest.toString());
            if (WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("return_code"))
                && WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("result_code"))) {
                String weiXinPrePaySign =
                    WeiXinPayUtils.geWeiXintPrePaySign(appid, mch_id, weiXinPrePay.getDeviceInfo(),
                        WeiXinTradeTypeEnum.NATIVE.name(), prePayRequest, partnerKey);
                String codeUrl = String.valueOf(prePayRequest.get("code_url"));
                LOG.info("预支付生成成功,{}", codeUrl);
                if (prePayRequest.get("sign").equals(weiXinPrePaySign)) {
                    lpTradePaymentRecord.setBankReturnMsg(prePayRequest.toString());
                    lpTradePaymentRecordDao.update(lpTradePaymentRecord);
                    scanPayResultVo.setCodeUrl(codeUrl);// 设置微信跳转地址
                    scanPayResultVo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    scanPayResultVo.setProductName(lpTradePaymentOrder.getProductName());
                    scanPayResultVo.setOrderAmount(lpTradePaymentOrder.getOrderAmount());
                } else {
                    throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "微信返回结果签名异常");
                }
            } else {
                throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "请求微信异常");
            }
        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {// 支付宝支付

            // 把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", AlipayConfigUtil.service);
            sParaTemp.put("partner", AlipayConfigUtil.partner);
            sParaTemp.put("seller_id", AlipayConfigUtil.seller_id);
            sParaTemp.put("_input_charset", AlipayConfigUtil.input_charset);
            sParaTemp.put("payment_type", AlipayConfigUtil.payment_type);
            sParaTemp.put("notify_url", AlipayConfigUtil.notify_url);
            sParaTemp.put("return_url", AlipayConfigUtil.return_url);
            sParaTemp.put("anti_phishing_key", AlipayConfigUtil.anti_phishing_key);
            sParaTemp.put("exter_invoke_ip", AlipayConfigUtil.exter_invoke_ip);
            sParaTemp.put("out_trade_no", lpTradePaymentRecord.getBankOrderNo());
            sParaTemp.put("subject", lpTradePaymentOrder.getProductName());
            sParaTemp.put("total_fee",
                String.valueOf(lpTradePaymentOrder.getOrderAmount().setScale(2, BigDecimal.ROUND_HALF_UP)));// 小数点后两位
            sParaTemp.put("body", "");
            LOG.info("扫码支付，支付宝请求参数:{}", sParaTemp);

            // 获取请求页面数据
            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
            LOG.info("扫码支付，支付宝返回报文:{}", sHtmlText);

            lpTradePaymentRecord.setBankReturnMsg(sHtmlText);
            lpTradePaymentRecordDao.update(lpTradePaymentRecord);
            scanPayResultVo.setCodeUrl(sHtmlText);// 设置支付宝跳转地址
            scanPayResultVo.setPayWayCode(PayWayEnum.ALIPAY.name());
            scanPayResultVo.setProductName(lpTradePaymentOrder.getProductName());
            scanPayResultVo.setOrderAmount(lpTradePaymentOrder.getOrderAmount());

        } else {
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "错误的支付方式");
        }
        lpNotifyService.orderSend(lpTradePaymentRecord.getBankOrderNo());
        return scanPayResultVo;
    }

    /**
     * 完成扫码支付(支付宝即时到账支付)
     * 
     * @param payWayCode
     * @param notifyMap
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String completeScanPay(String payWayCode, Map<String, String> notifyMap) {
        LOG.info("接收到{}支付结果{}", payWayCode, notifyMap);

        String returnStr = null;
        String bankOrderNo = notifyMap.get("out_trade_no");
        // 根据银行订单号获取支付信息
        LpTradePaymentRecord lpTradePaymentRecord = lpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (lpTradePaymentRecord == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, ",非法订单,订单不存在");
        }

        if (TradeStatusEnum.SUCCESS.name().equals(lpTradePaymentRecord.getStatus())) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单为成功状态");
        }
        String merchantNo = lpTradePaymentRecord.getMerchantNo();// 商户编号

        // 根据支付订单获取配置信息
        String fundIntoType = lpTradePaymentRecord.getFundIntoType();// 获取资金流入类型
        String partnerKey = "";

        if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(fundIntoType)) {// 商户收款
            // 根据资金流向获取配置信息
            LpUserPayInfo lpUserPayInfo = lpUserPayInfoService.getByUserNo(merchantNo, PayWayEnum.WEIXIN.name());
            partnerKey = lpUserPayInfo.getPartnerKey();

        } else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(fundIntoType)) {// 平台收款
            partnerKey = WeixinConfigUtil.readConfig("partnerKey");

            LpUserPayConfig lpUserPayConfig = lpUserPayConfigService.getByUserNo(merchantNo);
            if (lpUserPayConfig == null) {
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
            }
            // 根据支付产品及支付方式获取费率
            LpPayWay payWay =
                lpPayWayService.getByPayWayTypeCode(lpUserPayConfig.getProductCode(),
                    lpTradePaymentRecord.getPayWayCode(), lpTradePaymentRecord.getPayTypeCode());
            if (payWay == null) {
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
            }
        }

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {
            String sign = notifyMap.remove("sign");
            if (WeiXinPayUtils.notifySign(notifyMap, sign, partnerKey)) {// 根据配置信息验证签名
                if (WeixinTradeStateEnum.SUCCESS.name().equals(notifyMap.get("result_code"))) {// 业务结果
                    // 成功
                    String timeEndStr = notifyMap.get("time_end");
                    Date timeEnd = null;
                    if (!StringUtil.isEmpty(timeEndStr)) {
                        timeEnd = DateUtils.getDateFromString(timeEndStr, "yyyyMMddHHmmss");// 订单支付完成时间
                    }
                    completeSuccessOrder(lpTradePaymentRecord, notifyMap.get("transaction_id"), timeEnd,
                        notifyMap.toString());
                    returnStr =
                        "<xml>\n" + "  <return_code><![CDATA[SUCCESS]]></return_code>\n"
                            + "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
                } else {
                    completeFailOrder(lpTradePaymentRecord, notifyMap.toString());
                }
            } else {
                throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "微信签名失败");
            }

        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {
            if (AlipayNotify.verify(notifyMap)) {// 验证成功
                String tradeStatus = notifyMap.get("trade_status");
                if (AliPayTradeStateEnum.TRADE_FINISHED.name().equals(tradeStatus)) {
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    // 如果有做过处理，不执行商户的业务程序

                    // 注意：
                    // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                } else if (AliPayTradeStateEnum.TRADE_SUCCESS.name().equals(tradeStatus)) {

                    String gmtPaymentStr = notifyMap.get("gmt_payment");// 付款时间
                    Date timeEnd = null;
                    if (!StringUtil.isEmpty(gmtPaymentStr)) {
                        timeEnd = DateUtils.getDateFromString(gmtPaymentStr, "yyyy-MM-dd HH:mm:ss");
                    }
                    completeSuccessOrder(lpTradePaymentRecord, notifyMap.get("trade_no"), timeEnd, notifyMap.toString());
                    returnStr = "success";
                } else {
                    completeFailOrder(lpTradePaymentRecord, notifyMap.toString());
                    returnStr = "fail";
                }
            } else {// 验证失败
                throw new TradeBizException(TradeBizException.TRADE_ALIPAY_ERROR, "支付宝签名异常");
            }
        } else {
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "错误的支付方式");
        }

        LOG.info("返回支付通道{}信息{}", payWayCode, returnStr);
        return returnStr;
    }

    /**
     * 支付成功后,又是会出现页面通知早与后台通知 现页面通知,暂时不做数据处理功能,只生成页面通知URL
     * 
     * @param payWayCode
     * @param resultMap
     * @return
     */
    @Override
    public OrderPayResultVo completeScanPayByResult(String payWayCode, Map<String, String> resultMap) {

        OrderPayResultVo orderPayResultVo = new OrderPayResultVo();

        String bankOrderNo = resultMap.get("out_trade_no");
        // 根据银行订单号获取支付信息
        LpTradePaymentRecord lpTradePaymentRecord = lpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (lpTradePaymentRecord == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, ",非法订单,订单不存在");
        }

        orderPayResultVo.setOrderPrice(lpTradePaymentRecord.getOrderAmount());// 订单金额
        orderPayResultVo.setProductName(lpTradePaymentRecord.getProductName());// 产品名称

        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(lpTradePaymentRecord.getMerchantNo(),
                lpTradePaymentRecord.getMerchantOrderNo());

        String trade_status = resultMap.get("trade_status");
        // 计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(resultMap);
        if (verify_result) {// 验证成功
            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                String resultUrl =
                    getMerchantNotifyUrl(lpTradePaymentRecord, lpTradePaymentOrder,
                        lpTradePaymentRecord.getReturnUrl(), TradeStatusEnum.SUCCESS);
                orderPayResultVo.setReturnUrl(resultUrl);
                orderPayResultVo.setStatus(TradeStatusEnum.SUCCESS.name());
            } else {
                String resultUrl =
                    getMerchantNotifyUrl(lpTradePaymentRecord, lpTradePaymentOrder,
                        lpTradePaymentRecord.getReturnUrl(), TradeStatusEnum.FAILED);
                orderPayResultVo.setReturnUrl(resultUrl);
                orderPayResultVo.setStatus(TradeStatusEnum.FAILED.name());
            }
        } else {
            throw new TradeBizException(TradeBizException.TRADE_ALIPAY_ERROR, "支付宝签名异常");
        }
        return orderPayResultVo;
    }

    /**
     * 支付订单实体封装
     * 
     * @param merchantNo 商户编号
     * @param merchantName 商户名称
     * @param productName 产品名称
     * @param orderNo 商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice 订单金额
     * @param payWay 支付方式
     * @param payWayName 支付方式名称
     * @param payType 支付类型
     * @param fundIntoType 资金流入类型
     * @param orderIp 下单IP
     * @param orderPeriod 订单有效期
     * @param returnUrl 页面通知地址
     * @param notifyUrl 后台通知地址
     * @param remark 支付备注
     * @param field1 扩展字段1
     * @param field2 扩展字段2
     * @param field3 扩展字段3
     * @param field4 扩展字段4
     * @param field5 扩展字段5
     * @return
     */
    private LpTradePaymentOrder sealLpTradePaymentOrder(String merchantNo, String merchantName, String productName,
        String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWay, String payWayName,
        PayTypeEnum payType, String fundIntoType, String orderIp, Integer orderPeriod, String returnUrl,
        String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {

        LpTradePaymentOrder lpTradePaymentOrder = new LpTradePaymentOrder();
        lpTradePaymentOrder.setProductName(productName);// 商品名称
        if (StringUtil.isEmpty(orderNo)) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "订单号错误");
        }

        lpTradePaymentOrder.setMerchantOrderNo(orderNo);// 订单号

        if (orderPrice == null || orderPrice.doubleValue() <= 0) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "订单金额错误");
        }

        lpTradePaymentOrder.setOrderAmount(orderPrice);// 订单金额

        if (StringUtil.isEmpty(merchantName)) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "商户名称错误");
        }
        lpTradePaymentOrder.setMerchantName(merchantName);// 商户名称

        if (StringUtil.isEmpty(merchantNo)) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "商户编号错误");
        }
        lpTradePaymentOrder.setMerchantNo(merchantNo);// 商户编号

        if (orderDate == null) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "下单日期错误");
        }
        lpTradePaymentOrder.setOrderDate(orderDate);// 下单日期

        if (orderTime == null) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "下单时间错误");
        }
        lpTradePaymentOrder.setOrderTime(orderTime);// 下单时间
        lpTradePaymentOrder.setOrderIp(orderIp);// 下单IP
        lpTradePaymentOrder.setOrderRefererUrl("");// 下单前页面

        if (StringUtil.isEmpty(returnUrl)) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "页面通知地址错误");
        }
        lpTradePaymentOrder.setReturnUrl(returnUrl);// 页面通知地址

        if (StringUtil.isEmpty(notifyUrl)) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "后台通知地址错误");
        }
        lpTradePaymentOrder.setNotifyUrl(notifyUrl);// 后台通知地址

        if (orderPeriod == null || orderPeriod <= 0) {
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "订单有效期错误");
        }
        lpTradePaymentOrder.setOrderPeriod(orderPeriod);// 订单有效期

        Date expireTime = DateUtils.addMinute(orderTime, orderPeriod);// 订单过期时间
        lpTradePaymentOrder.setExpireTime(expireTime);// 订单过期时间
        lpTradePaymentOrder.setPayWayCode(payWay);// 支付通道编码
        lpTradePaymentOrder.setPayWayName(payWayName);// 支付通道名称
        lpTradePaymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态
        // 等待支付

        if (payType != null) {
            lpTradePaymentOrder.setPayTypeCode(payType.name());// 支付类型
            lpTradePaymentOrder.setPayTypeName(payType.getDesc());// 支付方式
        }
        lpTradePaymentOrder.setFundIntoType(fundIntoType);// 资金流入方向

        lpTradePaymentOrder.setRemark(remark);// 支付备注
        lpTradePaymentOrder.setField1(field1);// 扩展字段1
        lpTradePaymentOrder.setField2(field2);// 扩展字段2
        lpTradePaymentOrder.setField3(field3);// 扩展字段3
        lpTradePaymentOrder.setField4(field4);// 扩展字段4
        lpTradePaymentOrder.setField5(field5);// 扩展字段5

        return lpTradePaymentOrder;
    }

    /**
     * 封装支付流水记录实体
     * 
     * @param merchantNo 商户编号
     * @param merchantName 商户名称
     * @param productName 产品名称
     * @param orderNo 商户订单号
     * @param orderPrice 订单金额
     * @param payWay 支付方式编码
     * @param payWayName 支付方式名称
     * @param payType 支付类型
     * @param fundIntoType 资金流入方向
     * @param feeRate 支付费率
     * @param orderIp 订单IP
     * @param returnUrl 页面通知地址
     * @param notifyUrl 后台通知地址
     * @param remark 备注
     * @param field1 扩展字段1
     * @param field2 扩展字段2
     * @param field3 扩展字段3
     * @param field4 扩展字段4
     * @param field5 扩展字段5
     * @return
     */
    private LpTradePaymentRecord sealLpTradePaymentRecord(String merchantNo, String merchantName, String productName,
        String orderNo, BigDecimal orderPrice, String payWay, String payWayName, PayTypeEnum payType,
        String fundIntoType, BigDecimal feeRate, String orderIp, String returnUrl, String notifyUrl, String remark,
        String field1, String field2, String field3, String field4, String field5) {
        LpTradePaymentRecord lpTradePaymentRecord = new LpTradePaymentRecord();
        lpTradePaymentRecord.setProductName(productName);// 产品名称
        lpTradePaymentRecord.setMerchantOrderNo(orderNo);// 产品编号

        String trxNo = buildNoService.buildTrxNo();
        lpTradePaymentRecord.setTrxNo(trxNo);// 支付流水号

        String bankOrderNo = buildNoService.buildBankOrderNo();
        lpTradePaymentRecord.setBankOrderNo(bankOrderNo);// 银行订单号
        lpTradePaymentRecord.setMerchantName(merchantName);
        lpTradePaymentRecord.setMerchantNo(merchantNo);// 商户编号
        lpTradePaymentRecord.setOrderIp(orderIp);// 下单IP
        lpTradePaymentRecord.setOrderRefererUrl("");// 下单前页面
        lpTradePaymentRecord.setReturnUrl(returnUrl);// 页面通知地址
        lpTradePaymentRecord.setNotifyUrl(notifyUrl);// 后台通知地址
        lpTradePaymentRecord.setPayWayCode(payWay);// 支付通道编码
        lpTradePaymentRecord.setPayWayName(payWayName);// 支付通道名称
        lpTradePaymentRecord.setTrxType(TrxTypeEnum.EXPENSE.name());// 交易类型
        lpTradePaymentRecord.setOrderFrom(OrderFromEnum.USER_EXPENSE.name());// 订单来源
        lpTradePaymentRecord.setOrderAmount(orderPrice);// 订单金额
        lpTradePaymentRecord.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态
        // 等待支付

        lpTradePaymentRecord.setPayTypeCode(payType.name());// 支付类型
        lpTradePaymentRecord.setPayTypeName(payType.getDesc());// 支付方式
        lpTradePaymentRecord.setFundIntoType(fundIntoType);// 资金流入方向

        if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(fundIntoType)) {// 平台收款
            // 需要修改费率
            // 成本
            // 利润
            // 收入
            // 以及修改商户账户信息
            BigDecimal orderAmount = lpTradePaymentRecord.getOrderAmount();// 订单金额
            BigDecimal platIncome =
                orderAmount.multiply(feeRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP); // 平台收入
            // =
            // 订单金额
            // *
            // 支付费率(设置的费率除以100为真实费率)
            BigDecimal platCost =
                orderAmount.multiply(BigDecimal.valueOf(Double.valueOf(WeixinConfigUtil.readConfig("pay_rate"))))
                    .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);// 平台成本
            // =
            // 订单金额
            // *
            // 微信费率(设置的费率除以100为真实费率)
            BigDecimal platProfit = platIncome.subtract(platCost);// 平台利润 = 平台收入
            // - 平台成本

            lpTradePaymentRecord.setFeeRate(feeRate);// 费率
            lpTradePaymentRecord.setPlatCost(platCost);// 平台成本
            lpTradePaymentRecord.setPlatIncome(platIncome);// 平台收入
            lpTradePaymentRecord.setPlatProfit(platProfit);// 平台利润

        }

        lpTradePaymentRecord.setRemark(remark);// 支付备注
        lpTradePaymentRecord.setField1(field1);// 扩展字段1
        lpTradePaymentRecord.setField2(field2);// 扩展字段2
        lpTradePaymentRecord.setField3(field3);// 扩展字段3
        lpTradePaymentRecord.setField4(field4);// 扩展字段4
        lpTradePaymentRecord.setField5(field5);// 扩展字段5
        return lpTradePaymentRecord;
    }

    /**
     * 封装预支付实体
     * 
     * @param appId 公众号ID
     * @param mchId 商户号
     * @param productName 商品描述
     * @param remark 支付备注
     * @param bankOrderNo 银行订单号
     * @param orderPrice 订单价格
     * @param orderTime 订单下单时间
     * @param orderPeriod 订单有效期
     * @param weiXinTradeTypeEnum 微信支付方式
     * @param productId 商品ID
     * @param openId 用户标识
     * @param orderIp 下单IP
     * @return
     */
    private WeiXinPrePay sealWeixinPerPay(String appId, String mchId, String productName, String remark,
        String bankOrderNo, BigDecimal orderPrice, Date orderTime, Integer orderPeriod,
        WeiXinTradeTypeEnum weiXinTradeTypeEnum, String productId, String openId, String orderIp) {
        WeiXinPrePay weiXinPrePay = new WeiXinPrePay();

        weiXinPrePay.setAppid(appId);
        weiXinPrePay.setMchId(mchId);
        weiXinPrePay.setBody(productName);// 商品描述
        weiXinPrePay.setAttach(remark);// 支付备注
        weiXinPrePay.setOutTradeNo(bankOrderNo);// 银行订单号

        Integer totalFee = orderPrice.multiply(BigDecimal.valueOf(100d)).intValue();
        weiXinPrePay.setTotalFee(totalFee);// 订单金额
        weiXinPrePay.setTimeStart(DateUtils.formatDate(orderTime, "yyyyMMddHHmmss"));// 订单开始时间
        weiXinPrePay.setTimeExpire(DateUtils.formatDate(DateUtils.addMinute(orderTime, orderPeriod), "yyyyMMddHHmmss"));// 订单到期时间
        weiXinPrePay.setNotifyUrl(WeixinConfigUtil.readConfig("notify_url"));// 通知地址
        weiXinPrePay.setTradeType(weiXinTradeTypeEnum);// 交易类型
        weiXinPrePay.setProductId(productId);// 商品ID
        weiXinPrePay.setOpenid(openId);// 用户标识
        weiXinPrePay.setSpbillCreateIp(orderIp);// 下单IP

        return weiXinPrePay;
    }

    /**
     * 处理交易记录 如果交易记录是成功或者本地未支付,查询上游已支付,返回TRUE 如果上游支付结果为未支付,返回FALSE
     * 
     * @param bankOrderNo 银行订单号
     * @return
     */
    @Override
    public boolean processingTradeRecord(String bankOrderNo) {

        LpTradePaymentRecord byBankOrderNo = lpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        LOG.info("订单号:[{}],交易类型：[{}]", byBankOrderNo.getBankOrderNo(), byBankOrderNo.getPayWayCode());
        if (byBankOrderNo == null) {
            LOG.info("不存在该银行订单号[{}]对应的交易记录", bankOrderNo);
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "非法订单号");
        }

        if (!TradeStatusEnum.WAITING_PAYMENT.name().equals(byBankOrderNo.getStatus())) {
            LOG.info("该银行订单号[{}]对应的交易记录状态为:{},不需要再处理", bankOrderNo, byBankOrderNo.getStatus());
            return true;
        } else {
            // 判断微信 支付宝 交易类型
            if (byBankOrderNo.getPayWayCode().equals(PayWayEnum.WEIXIN.name())) {
                Map<String, Object> resultMap = WeiXinPayUtils.orderQuery(byBankOrderNo.getBankOrderNo());
                Object returnCode = resultMap.get("return_code");
                // 查询失败
                if (null == returnCode || "FAIL".equals(returnCode)) {
                    return false;
                }
                // 当trade_state为SUCCESS时才返回result_code
                if ("SUCCESS".equals(resultMap.get("trade_state"))) {
                    completeSuccessOrder(byBankOrderNo, byBankOrderNo.getBankTrxNo(), new Date(), "订单交易成功");
                    return true;
                }
            } else if (byBankOrderNo.getPayWayCode().equals(PayWayEnum.ALIPAY.name())) {
                // 支付宝
                if (PayTypeEnum.DIRECT_PAY.name().equals(byBankOrderNo.getPayTypeCode())) {
                    // 支付宝--即时到账
                    LOG.info("支付宝--即时到账订单查询!订单号:[{}]", byBankOrderNo.getBankOrderNo());
                    Map<String, Object> resultMap = AliPayUtil.singleTradeQuery(byBankOrderNo.getBankOrderNo());
                    if (resultMap.isEmpty() || !"T".equals(resultMap.get("is_success"))) {
                        return false;
                    }
                    // 当返回状态为“TRADE_FINISHED”交易成功结束和“TRADE_SUCCESS”支付成功时更新交易状态
                    if ("TRADE_SUCCESS".equals(resultMap.get("trade_status"))
                        || "TRADE_FINISHED".equals(resultMap.get("trade_status"))) {
                        completeSuccessOrder(byBankOrderNo, byBankOrderNo.getBankTrxNo(), new Date(), "订单交易成功");
                        return true;
                    }
                } else if (PayTypeEnum.F2F_PAY.name().equals(byBankOrderNo.getPayTypeCode())) {
                    // 支付宝--条码支付
                    LOG.info("支付宝--条码支付订单查询!订单号:[{}]", byBankOrderNo.getBankOrderNo());
                    Map<String, Object> resultMap = AliPayUtil.tradeQuery(byBankOrderNo.getBankOrderNo());
                    if (!"10000".equals(resultMap.get("code"))) {
                        return false;
                    }
                    // 当返回状态为“TRADE_FINISHED”交易成功结束和“TRADE_SUCCESS”支付成功时更新交易状态
                    if ("TRADE_SUCCESS".equals(resultMap.get("tradeStatus"))
                        || "TRADE_FINISHED".equals(resultMap.get("tradeStatus"))) {
                        completeSuccessOrder(byBankOrderNo, byBankOrderNo.getBankTrxNo(), new Date(), "订单交易成功");
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public ProgramPayResultVo programPay(String payKey, String openId, String productName, String orderNo,
        Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, String notifyUrl,
        String remark, String field1, String field2, String field3, String field4, String field5) {
        LpUserPayConfig lpUserPayConfig = lpUserPayConfigService.getByPayKey(payKey);
        if (lpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        // 根据支付产品及支付方式获取费率
        LpPayWay payWay = null;
        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {
            payType = PayTypeEnum.WX_PROGRAM_PAY;
            payWay = lpPayWayService.getByPayWayTypeCode(lpUserPayConfig.getProductCode(), payWayCode, payType.name());
        } else {
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "暂不支持此支付方式");
        }
        if (payWay == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = lpUserPayConfig.getUserNo();// 商户编号
        LpUserInfo lpUserInfo = lpUserInfoService.getDataByMerchentNo(merchantNo);
        if (lpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        // 生产订单记录
        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (lpTradePaymentOrder == null) {
            lpTradePaymentOrder =
                sealLpTradePaymentOrder(merchantNo, lpUserInfo.getUserName(), productName, orderNo, orderDate,
                    orderTime, orderPrice, payWayCode, PayWayEnum.getEnum(payWayCode).getDesc(), payType,
                    lpUserPayConfig.getFundIntoType(), orderIp, 10, payType.name(), notifyUrl, remark, field1, field2,
                    field3, field4, field5);
            lpTradePaymentOrderDao.insert(lpTradePaymentOrder);
        } else {
            if (TradeStatusEnum.SUCCESS.name().equals(lpTradePaymentOrder.getStatus())) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
            }
            if (lpTradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0) {
                lpTradePaymentOrder.setOrderAmount(orderPrice);// 如果金额不一致,修改金额为最新的金额
            }
        }

        return getProgramPayResultVo(lpTradePaymentOrder, payWay, lpUserPayConfig.getPaySecret(), openId, null);
    }

    /**
     * 通过支付订单及商户费率生成支付记录
     * 
     * @param tradePaymentOrder 支付订单
     * @param payWay 商户支付配置
     * @return
     */
    private ProgramPayResultVo getProgramPayResultVo(LpTradePaymentOrder tradePaymentOrder, LpPayWay payWay,
        String merchantPaySecret, String openId, List<LeoPayGoodsDetails> roncooPayGoodsDetailses) {

        ProgramPayResultVo resultVo = new ProgramPayResultVo();
        String payWayCode = payWay.getPayWayCode();// 支付方式

        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWay.getPayWayCode())) {
            payType = PayTypeEnum.WX_PROGRAM_PAY;
        } else if (PayWayEnum.ALIPAY.name().equals(payWay.getPayWayCode())) {
            // TODO 支付宝小程序支付，需要自定义枚举
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "暂不支持此支付方式");
        }

        tradePaymentOrder.setPayTypeCode(payType.name());// 支付类型
        tradePaymentOrder.setPayTypeName(payType.getDesc());// 支付方式
        tradePaymentOrder.setPayWayCode(payWay.getPayWayCode());// 支付通道编号
        tradePaymentOrder.setPayWayName(payWay.getPayWayName());// 支付通道名称

        // 生成支付流水
        LpTradePaymentRecord lpTradePaymentRecord =
            sealLpTradePaymentRecord(tradePaymentOrder.getMerchantNo(), tradePaymentOrder.getMerchantName(),
                tradePaymentOrder.getProductName(), tradePaymentOrder.getMerchantOrderNo(),
                tradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(), payWay.getPayWayName(), payType,
                tradePaymentOrder.getFundIntoType(), BigDecimal.valueOf(payWay.getPayRate()),
                tradePaymentOrder.getOrderIp(), tradePaymentOrder.getReturnUrl(), tradePaymentOrder.getNotifyUrl(),
                tradePaymentOrder.getRemark(), tradePaymentOrder.getField1(), tradePaymentOrder.getField2(),
                tradePaymentOrder.getField3(), tradePaymentOrder.getField4(), tradePaymentOrder.getField5());
        lpTradePaymentRecordDao.insert(lpTradePaymentRecord);

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {// 微信支付
            Map<String, Object> resultMap =
                WeiXinPayUtil.appletPay(lpTradePaymentRecord.getBankOrderNo(), lpTradePaymentRecord.getProductName(),
                    lpTradePaymentRecord.getOrderAmount(), lpTradePaymentRecord.getOrderIp(),
                    lpTradePaymentRecord.getNotifyUrl(), openId, roncooPayGoodsDetailses);
            if (resultMap == null || resultMap.isEmpty()) {
                resultVo.setStatus(PublicEnum.NO.name());
                resultVo.setBankReturnMsg("请求支付失败!");
            } else {
                if ("YES".equals(resultMap.get("verify"))) {
                    if ("SUCCESS".equals(resultMap.get("return_code"))
                        && "SUCCESS".equals(resultMap.get("result_code"))) {
                        resultVo.setStatus(PublicEnum.YES.name());
                        resultVo.setBankReturnMsg(String.valueOf(resultMap.get("return_msg")));

                        Object prepayId = resultMap.get("prepay_id");
                        Object appid = resultMap.get("appid");
                        SortedMap<String, Object> returnMap = new TreeMap<>();
                        returnMap.put("appId", appid);// appId
                        returnMap.put("timeStamp", System.currentTimeMillis());// 当前时间戳
                        returnMap.put("nonceStr", WeiXinPayUtil.getnonceStr());// 随机数
                        returnMap.put("package", "prepay_id=" + prepayId);//
                        returnMap.put("signType", "MD5");// 签名方式
                        returnMap.put("paySign", WeiXinPayUtil.getSign(returnMap, WeixinConfigUtil.partnerKey));
                        String jsonString = JSON.toJSONString(returnMap);
                        resultVo.setPayMessage(jsonString);
                        // 请求成功，发起轮询
                        lpNotifyService.orderSend(lpTradePaymentRecord.getBankOrderNo());
                    } else {
                        resultVo.setStatus(PublicEnum.NO.name());
                        resultVo.setBankReturnMsg(String.valueOf(resultMap.get("return_msg")));
                    }

                } else {
                    resultVo.setStatus(PublicEnum.NO.name());
                    resultVo.setBankReturnMsg("请求微信返回信息验签不通过！");
                }
            }
        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {// 支付宝支付
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "暂不支持此支付方式");
        }

        Map<String, Object> paramMap = new HashMap<>();
        if (!StringUtil.isEmpty(resultVo.getPayMessage())) {
            paramMap.put("payMessage", resultVo.getPayMessage());// 支付信息
        }
        resultVo.setStatus(lpTradePaymentRecord.getStatus());// 支付结果
        paramMap.put("status", lpTradePaymentRecord.getStatus());
        String sign = MerchantApiUtil.getSign(paramMap, merchantPaySecret);
        resultVo.setSign(sign);
        return resultVo;
    }
}