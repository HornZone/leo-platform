package com.leo.platform.pay.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leo.platform.common.enums.PayWayEnum;
import com.leo.platform.common.enums.PublicEnum;
import com.leo.platform.common.enums.PublicStatusEnum;
import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.pay.user.dao.LpUserPayConfigDao;
import com.leo.platform.pay.user.entity.LpPayProduct;
import com.leo.platform.pay.user.entity.LpPayWay;
import com.leo.platform.pay.user.entity.LpUserPayConfig;
import com.leo.platform.pay.user.entity.LpUserPayInfo;
import com.leo.platform.pay.user.service.LpPayProductService;
import com.leo.platform.pay.user.service.LpPayWayService;
import com.leo.platform.pay.user.service.LpUserPayConfigService;
import com.leo.platform.pay.user.service.LpUserPayInfoService;
import com.platform.pay.user.exception.PayBizException;

/**
 * 用户支付配置service实现类
 */
@Service("lpUserPayConfigService")
public class LpUserPayConfigServiceImpl implements LpUserPayConfigService {

    @Autowired
    private LpUserPayConfigDao lpUserPayConfigDao;
    @Autowired
    private LpPayProductService lpPayProductService;
    @Autowired
    private LpPayWayService lpPayWayService;
    @Autowired
    private LpUserPayInfoService lpUserPayInfoService;

    @Override
    public void saveData(LpUserPayConfig lpUserPayConfig) {
        lpUserPayConfigDao.insert(lpUserPayConfig);
    }

    @Override
    public void updateData(LpUserPayConfig lpUserPayConfig) {
        lpUserPayConfigDao.update(lpUserPayConfig);
    }

    @Override
    public LpUserPayConfig getDataById(String id) {
        return lpUserPayConfigDao.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, LpUserPayConfig lpUserPayConfig) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productCode", lpUserPayConfig.getProductCode());
        paramMap.put("userNo", lpUserPayConfig.getUserNo());
        paramMap.put("userName", lpUserPayConfig.getUserName());
        paramMap.put("productName", lpUserPayConfig.getProductName());
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return lpUserPayConfigDao.listPage(pageParam, paramMap);
    }

    /**
     * 根据商户编号获取已生效的支付配置
     * 
     * @param userNo
     * @return
     */
    @Override
    public LpUserPayConfig getByUserNo(String userNo) {
        return lpUserPayConfigDao.getByUserNo(userNo, PublicEnum.YES.name());
    }

    /**
     * 根据商户编号获取支付配置
     * 
     * @param userNo
     * @param auditStatus
     * @return
     */
    @Override
    public LpUserPayConfig getByUserNo(String userNo, String auditStatus) {
        return lpUserPayConfigDao.getByUserNo(userNo, auditStatus);
    }

    /**
     * 根据支付产品获取已生效数据
     */
    @Override
    public List<LpUserPayConfig> listByProductCode(String productCode) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productCode", productCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("auditStatus", PublicEnum.YES.name());
        return lpUserPayConfigDao.listBy(paramMap);
    }

    /**
     * 根据支付产品获取数据
     */
    @Override
    public List<LpUserPayConfig> listByProductCode(String productCode, String auditStatus) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productCode", productCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("auditStatus", auditStatus);
        return lpUserPayConfigDao.listBy(paramMap);
    }

    /**
     * 创建用户支付配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserPayConfig(String userNo, String userName, String productCode, String productName,
        Integer riskDay, String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey,
        String ali_partner, String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey,
        String ali_rsaPublicKey) throws PayBizException {

        createUserPayConfig(userNo, userName, productCode, productName, riskDay, fundIntoType, isAutoSett, appId,
            merchantId, partnerKey, ali_partner, ali_sellerId, ali_key, ali_appid, ali_rsaPrivateKey, ali_rsaPublicKey,
            null, null);
    }

    /**
     * 创建用户支付配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserPayConfig(String userNo, String userName, String productCode, String productName,
        Integer riskDay, String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey,
        String ali_partner, String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey,
        String ali_rsaPublicKey, String securityRating, String merchantServerIp) throws PayBizException {

        LpUserPayConfig payConfig = lpUserPayConfigDao.getByUserNo(userNo, null);
        if (payConfig != null) {
            throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_EXIST, "用户支付配置已存在");
        }

        LpUserPayConfig lpUserPayConfig = new LpUserPayConfig();
        lpUserPayConfig.setUserNo(userNo);
        lpUserPayConfig.setUserName(userName);
        lpUserPayConfig.setProductCode(productCode);
        lpUserPayConfig.setProductName(productName);
        lpUserPayConfig.setStatus(PublicStatusEnum.ACTIVE.name());
        lpUserPayConfig.setAuditStatus(PublicEnum.YES.name());
        lpUserPayConfig.setRiskDay(riskDay);
        lpUserPayConfig.setFundIntoType(fundIntoType);
        lpUserPayConfig.setIsAutoSett(isAutoSett);
        lpUserPayConfig.setPayKey(StringUtil.get32UUID());
        lpUserPayConfig.setPaySecret(StringUtil.get32UUID());
        lpUserPayConfig.setId(StringUtil.get32UUID());
        lpUserPayConfig.setSecurityRating(securityRating);// 安全等级
        lpUserPayConfig.setMerchantServerIp(merchantServerIp);
        saveData(lpUserPayConfig);

        // 查询支付产品下有哪些支付方式
        List<LpPayWay> payWayList = lpPayWayService.listByProductCode(productCode);
        Map<String, String> map = new HashMap<String, String>();
        // 过滤重复数据
        for (LpPayWay payWay : payWayList) {
            map.put(payWay.getPayWayCode(), payWay.getPayWayName());
        }

        for (String key : map.keySet()) {
            if (key.equals(PayWayEnum.WEIXIN.name())) {
                // 创建用户第三方支付信息
                LpUserPayInfo lpUserPayInfo = lpUserPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
                if (lpUserPayInfo == null) {
                    lpUserPayInfo = new LpUserPayInfo();
                    lpUserPayInfo.setId(StringUtil.get32UUID());
                    lpUserPayInfo.setCreateTime(new Date());
                    lpUserPayInfo.setAppId(appId);
                    lpUserPayInfo.setMerchantId(merchantId);
                    lpUserPayInfo.setPartnerKey(partnerKey);
                    lpUserPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    lpUserPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
                    lpUserPayInfo.setUserNo(userNo);
                    lpUserPayInfo.setUserName(userName);
                    lpUserPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
                    lpUserPayInfoService.saveData(lpUserPayInfo);
                } else {
                    lpUserPayInfo.setEditTime(new Date());
                    lpUserPayInfo.setAppId(appId);
                    lpUserPayInfo.setMerchantId(merchantId);
                    lpUserPayInfo.setPartnerKey(partnerKey);
                    lpUserPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    lpUserPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
                    lpUserPayInfoService.updateData(lpUserPayInfo);
                }

            } else if (key.equals(PayWayEnum.ALIPAY.name())) {
                // 创建用户第三方支付信息
                LpUserPayInfo lpUserPayInfo = lpUserPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
                if (lpUserPayInfo == null) {
                    lpUserPayInfo = new LpUserPayInfo();
                    lpUserPayInfo.setId(StringUtil.get32UUID());
                    lpUserPayInfo.setCreateTime(new Date());
                    lpUserPayInfo.setAppId(ali_partner);
                    lpUserPayInfo.setMerchantId(ali_sellerId);
                    lpUserPayInfo.setPartnerKey(ali_key);
                    lpUserPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
                    lpUserPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
                    lpUserPayInfo.setUserNo(userNo);
                    lpUserPayInfo.setUserName(userName);
                    lpUserPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
                    lpUserPayInfo.setOfflineAppId(ali_appid);
                    lpUserPayInfo.setRsaPrivateKey(ali_rsaPrivateKey);
                    lpUserPayInfo.setRsaPublicKey(ali_rsaPublicKey);
                    lpUserPayInfoService.saveData(lpUserPayInfo);
                } else {
                    lpUserPayInfo.setEditTime(new Date());
                    lpUserPayInfo.setAppId(ali_partner);
                    lpUserPayInfo.setMerchantId(ali_sellerId);
                    lpUserPayInfo.setPartnerKey(ali_key);
                    lpUserPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
                    lpUserPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
                    lpUserPayInfo.setOfflineAppId(ali_appid);
                    lpUserPayInfo.setRsaPrivateKey(ali_rsaPrivateKey);
                    lpUserPayInfo.setRsaPublicKey(ali_rsaPublicKey);
                    lpUserPayInfoService.updateData(lpUserPayInfo);
                }
            }
        }

    }

    /**
     * 删除支付产品
     * 
     * @param userNo
     */
    @Override
    public void deleteUserPayConfig(String userNo) throws PayBizException {

        LpUserPayConfig lpUserPayConfig = lpUserPayConfigDao.getByUserNo(userNo, null);
        if (lpUserPayConfig == null) {
            throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST, "用户支付配置不存在");
        }

        lpUserPayConfig.setStatus(PublicStatusEnum.UNACTIVE.name());
        lpUserPayConfig.setEditTime(new Date());
        updateData(lpUserPayConfig);
    }

    /**
     * 修改用户支付配置
     */
    @Override
    public void updateUserPayConfig(String userNo, String productCode, String productName, Integer riskDay,
        String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner,
        String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey)
        throws PayBizException {

        updateUserPayConfig(userNo, productCode, productName, riskDay, fundIntoType, isAutoSett, appId, merchantId,
            partnerKey, ali_partner, ali_sellerId, ali_key, ali_appid, ali_rsaPrivateKey, ali_rsaPublicKey, null, null);
    }

    /**
     * 修改用户支付配置
     */
    @Override
    public void updateUserPayConfig(String userNo, String productCode, String productName, Integer riskDay,
        String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner,
        String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey,
        String securityRating, String merchantServerIp) throws PayBizException {
        LpUserPayConfig lpUserPayConfig = lpUserPayConfigDao.getByUserNo(userNo, null);
        if (lpUserPayConfig == null) {
            throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST, "用户支付配置不存在");
        }

        lpUserPayConfig.setProductCode(productCode);
        lpUserPayConfig.setProductName(productName);
        lpUserPayConfig.setRiskDay(riskDay);
        lpUserPayConfig.setFundIntoType(fundIntoType);
        lpUserPayConfig.setIsAutoSett(isAutoSett);
        lpUserPayConfig.setEditTime(new Date());
        lpUserPayConfig.setSecurityRating(securityRating);// 安全等级
        lpUserPayConfig.setMerchantServerIp(merchantServerIp);
        updateData(lpUserPayConfig);

        // 查询支付产品下有哪些支付方式
        List<LpPayWay> payWayList = lpPayWayService.listByProductCode(productCode);
        Map<String, String> map = new HashMap<String, String>();
        // 过滤重复数据
        for (LpPayWay payWay : payWayList) {
            map.put(payWay.getPayWayCode(), payWay.getPayWayName());
        }

        for (String key : map.keySet()) {
            if (key.equals(PayWayEnum.WEIXIN.name())) {
                // 创建用户第三方支付信息
                LpUserPayInfo lpUserPayInfo = lpUserPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
                if (lpUserPayInfo == null) {
                    lpUserPayInfo = new LpUserPayInfo();
                    lpUserPayInfo.setId(StringUtil.get32UUID());
                    lpUserPayInfo.setCreateTime(new Date());
                    lpUserPayInfo.setAppId(appId);
                    lpUserPayInfo.setMerchantId(merchantId);
                    lpUserPayInfo.setPartnerKey(partnerKey);
                    lpUserPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    lpUserPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
                    lpUserPayInfo.setUserNo(userNo);
                    lpUserPayInfo.setUserName(lpUserPayConfig.getUserName());
                    lpUserPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
                    lpUserPayInfoService.saveData(lpUserPayInfo);
                } else {
                    lpUserPayInfo.setEditTime(new Date());
                    lpUserPayInfo.setAppId(appId);
                    lpUserPayInfo.setMerchantId(merchantId);
                    lpUserPayInfo.setPartnerKey(partnerKey);
                    lpUserPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    lpUserPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
                    lpUserPayInfoService.updateData(lpUserPayInfo);
                }

            } else if (key.equals(PayWayEnum.ALIPAY.name())) {
                // 创建用户第三方支付信息
                LpUserPayInfo lpUserPayInfo = lpUserPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
                if (lpUserPayInfo == null) {
                    lpUserPayInfo = new LpUserPayInfo();
                    lpUserPayInfo.setId(StringUtil.get32UUID());
                    lpUserPayInfo.setCreateTime(new Date());
                    lpUserPayInfo.setAppId(ali_partner);
                    lpUserPayInfo.setMerchantId(ali_sellerId);
                    lpUserPayInfo.setPartnerKey(ali_key);
                    lpUserPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
                    lpUserPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
                    lpUserPayInfo.setUserNo(userNo);
                    lpUserPayInfo.setUserName(lpUserPayConfig.getUserName());
                    lpUserPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
                    lpUserPayInfo.setOfflineAppId(ali_appid);
                    lpUserPayInfo.setRsaPrivateKey(ali_rsaPrivateKey);
                    lpUserPayInfo.setRsaPublicKey(ali_rsaPublicKey);
                    lpUserPayInfoService.saveData(lpUserPayInfo);
                } else {
                    lpUserPayInfo.setEditTime(new Date());
                    lpUserPayInfo.setAppId(ali_partner);
                    lpUserPayInfo.setMerchantId(ali_sellerId);
                    lpUserPayInfo.setPartnerKey(ali_key);
                    lpUserPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
                    lpUserPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
                    lpUserPayInfo.setOfflineAppId(ali_appid);
                    lpUserPayInfo.setRsaPrivateKey(ali_rsaPrivateKey);
                    lpUserPayInfo.setRsaPublicKey(ali_rsaPublicKey);
                    lpUserPayInfoService.updateData(lpUserPayInfo);
                }
            }
        }
    }

    /**
     * 审核
     * 
     * @param userNo
     * @param auditStatus
     */
    @Override
    public void audit(String userNo, String auditStatus) {
        LpUserPayConfig lpUserPayConfig = getByUserNo(userNo, null);
        if (lpUserPayConfig == null) {
            throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST, "支付配置不存在！");
        }

        if (auditStatus.equals(PublicEnum.YES.name())) {
            // 检查是否已关联生效的支付产品
            LpPayProduct lpPayProduct =
                lpPayProductService.getByProductCode(lpUserPayConfig.getProductCode(), PublicEnum.YES.name());
            if (lpPayProduct == null) {
                throw new PayBizException(PayBizException.PAY_PRODUCT_IS_NOT_EXIST, "未关联已生效的支付产品，无法操作！");
            }

            // 检查是否已设置第三方支付信息
        }
        lpUserPayConfig.setAuditStatus(auditStatus);
        lpUserPayConfig.setEditTime(new Date());
        updateData(lpUserPayConfig);
    }

    /**
     * 根据商户key获取已生效的支付配置
     * 
     * @param payKey
     * @return
     */
    public LpUserPayConfig getByPayKey(String payKey) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payKey", payKey);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("auditStatus", PublicEnum.YES.name());
        return lpUserPayConfigDao.getBy(paramMap);
    }
}