package com.leo.platform.pay.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.platform.common.enums.PublicEnum;
import com.leo.platform.common.enums.PublicStatusEnum;
import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.pay.user.dao.LpPayProductDao;
import com.leo.platform.pay.user.entity.LpPayProduct;
import com.leo.platform.pay.user.entity.LpPayWay;
import com.leo.platform.pay.user.entity.LpUserPayConfig;
import com.leo.platform.pay.user.service.LpPayProductService;
import com.leo.platform.pay.user.service.LpPayWayService;
import com.leo.platform.pay.user.service.LpUserPayConfigService;
import com.platform.pay.user.exception.PayBizException;

/**
 * 支付产品service实现类
 */
@Service("lpPayProductService")
public class LpPayProductServiceImpl implements LpPayProductService {

    @Autowired
    private LpPayProductDao lpPayProductDao;

    @Autowired
    private LpPayWayService lpPayWayService;

    @Autowired
    private LpUserPayConfigService lpUserPayConfigService;

    @Override
    public void saveData(LpPayProduct lpPayProduct) {
        lpPayProductDao.insert(lpPayProduct);
    }

    @Override
    public void updateData(LpPayProduct lpPayProduct) {
        lpPayProductDao.update(lpPayProduct);
    }

    @Override
    public LpPayProduct getDataById(String id) {
        return lpPayProductDao.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, LpPayProduct rpPayProduct) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("auditStatus", rpPayProduct.getAuditStatus());
        paramMap.put("productName", rpPayProduct.getProductName());
        return lpPayProductDao.listPage(pageParam, paramMap);
    }

    /**
     * 根据产品编号获取支付产品
     */
    @Override
    public LpPayProduct getByProductCode(String productCode, String auditStatus) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productCode", productCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("auditStatus", auditStatus);
        return lpPayProductDao.getBy(paramMap);
    }

    /**
     * 创建支付产品
     * 
     * @param productCode
     * @param productName
     */
    @Override
    public void createPayProduct(String productCode, String productName) throws PayBizException {
        LpPayProduct rpPayProduct = getByProductCode(productCode, null);
        if (rpPayProduct != null) {
            throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EXIST, "支付产品已存在");
        }
        rpPayProduct = new LpPayProduct();
        rpPayProduct.setStatus(PublicStatusEnum.ACTIVE.name());
        rpPayProduct.setCreateTime(new Date());
        rpPayProduct.setId(StringUtil.get32UUID());
        rpPayProduct.setProductCode(productCode);
        rpPayProduct.setProductName(productName);
        rpPayProduct.setAuditStatus(PublicEnum.NO.name());
        saveData(rpPayProduct);
    }

    /**
     * 删除支付产品
     * 
     * @param productCode
     */
    @Override
    public void deletePayProduct(String productCode) throws PayBizException {

        List<LpPayWay> payWayList = lpPayWayService.listByProductCode(productCode);
        if (!payWayList.isEmpty()) {
            throw new PayBizException(PayBizException.PAY_PRODUCT_HAS_DATA, "支付产品已关联支付方式，无法删除！");
        }

        List<LpUserPayConfig> payConfigList = lpUserPayConfigService.listByProductCode(productCode);
        if (!payConfigList.isEmpty()) {
            throw new PayBizException(PayBizException.PAY_PRODUCT_HAS_DATA, "支付产品已关联用户，无法删除！");
        }

        LpPayProduct rpPayProduct = getByProductCode(productCode, null);
        if (rpPayProduct.getAuditStatus().equals(PublicEnum.YES.name())) {
            throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EFFECTIVE, "支付产品已生效，无法删除！");
        }

        rpPayProduct.setStatus(PublicStatusEnum.UNACTIVE.name());
        updateData(rpPayProduct);
    }

    /**
     * 获取所有支付产品
     * 
     * @param productCode
     */
    @Override
    public List<LpPayProduct> listAll() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return lpPayProductDao.listBy(paramMap);
    }

    /**
     * 审核
     * 
     * @param productCode
     * @param auditStatus
     */
    @Override
    public void audit(String productCode, String auditStatus) throws PayBizException {
        LpPayProduct lpPayProduct = getByProductCode(productCode, null);
        if (lpPayProduct == null) {
            throw new PayBizException(PayBizException.PAY_PRODUCT_IS_NOT_EXIST, "支付产品不存在！");
        }

        if (auditStatus.equals(PublicEnum.YES.name())) {
            // 检查是否已设置支付方式
            List<LpPayWay> payWayList = lpPayWayService.listByProductCode(productCode);
            if (payWayList.isEmpty()) {
                throw new PayBizException(PayBizException.PAY_TYPE_IS_NOT_EXIST, "支付方式未设置，无法操作！");
            }

        } else if (auditStatus.equals(PublicEnum.NO.name())) {
            // 检查是否已有支付配置
            List<LpUserPayConfig> payConfigList = lpUserPayConfigService.listByProductCode(productCode);
            if (!payConfigList.isEmpty()) {
                throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_EXIST, "支付产品已关联用户支付配置，无法操作！");
            }
        }
        lpPayProduct.setAuditStatus(auditStatus);
        lpPayProduct.setEditTime(new Date());
        updateData(lpPayProduct);
    }
}