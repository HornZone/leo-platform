package com.leo.platform.pay.user.service;

import java.util.List;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.user.entity.LpUserPayConfig;
import com.platform.pay.user.exception.PayBizException;

/**
 * 用户支付配置service接口
 */
public interface LpUserPayConfigService {

    /**
     * 保存
     */
    void saveData(LpUserPayConfig rpUserPayConfig);

    /**
     * 更新
     */
    void updateData(LpUserPayConfig rpUserPayConfig);

    /**
     * 根据id获取数据
     * 
     * @param id
     * @return
     */
    LpUserPayConfig getDataById(String id);

    /**
     * 获取分页数据
     * 
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, LpUserPayConfig rpUserPayConfig);

    /**
     * 根据商户编号获取已生效的支付配置
     * 
     * @param userNo
     * @return
     */
    LpUserPayConfig getByUserNo(String userNo);

    /**
     * 根据商户编号获取支付配置
     * 
     * @param userNo
     * @param auditStatus
     * @return
     */
    LpUserPayConfig getByUserNo(String userNo, String auditStatus);

    /**
     * 根据支付产品获取已生效数据
     */
    List<LpUserPayConfig> listByProductCode(String productCode);

    /**
     * 根据支付产品获取数据
     */
    List<LpUserPayConfig> listByProductCode(String productCode, String auditStatus);

    /**
     * 创建用户支付配置
     */
    void createUserPayConfig(String userNo, String userName, String productCode, String productName, Integer riskDay,
        String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner,
        String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey)
        throws PayBizException;

    /**
     * 创建用户支付配置
     */
    void createUserPayConfig(String userNo, String userName, String productCode, String productName, Integer riskDay,
        String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner,
        String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey,
        String securityRating, String merchantServerIp) throws PayBizException;

    /**
     * 删除支付产品
     * 
     * @param userNo
     */
    void deleteUserPayConfig(String userNo) throws PayBizException;

    /**
     * 修改用户支付配置
     */
    void updateUserPayConfig(String userNo, String productCode, String productName, Integer riskDay,
        String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner,
        String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey)
        throws PayBizException;

    /**
     * 修改用户支付配置
     */
    void updateUserPayConfig(String userNo, String productCode, String productName, Integer riskDay,
        String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner,
        String ali_sellerId, String ali_key, String ali_appid, String ali_rsaPrivateKey, String ali_rsaPublicKey,
        String securityRating, String merchantServerIp) throws PayBizException;

    /**
     * 审核
     * 
     * @param userNo
     * @param auditStatus
     */
    void audit(String userNo, String auditStatus);

    /**
     * 根据商户key获取已生效的支付配置
     * 
     * @param payKey
     * @return
     */
    LpUserPayConfig getByPayKey(String payKey);

}