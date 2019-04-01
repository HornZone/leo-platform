package com.leo.platform.pay.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.platform.common.enums.PayTypeEnum;
import com.leo.platform.common.enums.PayWayEnum;
import com.leo.platform.common.enums.PublicEnum;
import com.leo.platform.common.enums.PublicStatusEnum;
import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.pay.user.dao.LpPayWayDao;
import com.leo.platform.pay.user.entity.LpPayProduct;
import com.leo.platform.pay.user.entity.LpPayWay;
import com.leo.platform.pay.user.service.LpPayProductService;
import com.leo.platform.pay.user.service.LpPayWayService;
import com.platform.pay.user.exception.PayBizException;

/**
 * 支付方式service实现类
 */
@Service("lpPayWayService")
public class LpPayWayServiceImpl implements LpPayWayService {

    @Autowired
    private LpPayWayDao lpPayWayDao;

    @Autowired
    private LpPayProductService lpPayProductService;

    @Override
    public void saveData(LpPayWay lpPayWay) {
        lpPayWayDao.insert(lpPayWay);
    }

    @Override
    public void updateData(LpPayWay lpPayWay) {
        lpPayWayDao.update(lpPayWay);
    }

    @Override
    public LpPayWay getDataById(String id) {
        return lpPayWayDao.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, LpPayWay lpPayWay) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("payProductCode", lpPayWay.getPayProductCode());
        paramMap.put("payWayName", lpPayWay.getPayWayName());
        paramMap.put("payTypeName", lpPayWay.getPayTypeName());
        return lpPayWayDao.listPage(pageParam, paramMap);
    }

    @Override
    public LpPayWay getByPayWayTypeCode(String payProductCode, String payWayCode, String payTypeCode) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payProductCode", payProductCode);
        paramMap.put("payTypeCode", payTypeCode);
        paramMap.put("payWayCode", payWayCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return lpPayWayDao.getBy(paramMap);
    }

    /**
     * 绑定支付费率
     * 
     * @param payWayCode
     * @param payTypeCode
     * @param payRate
     */
    @Override
    public void createPayWay(String payProductCode, String payWayCode, String payTypeCode, Double payRate)
        throws PayBizException {
        LpPayWay payWay = getByPayWayTypeCode(payProductCode, payWayCode, payTypeCode);
        if (payWay != null) {
            throw new PayBizException(PayBizException.PAY_TYPE_IS_EXIST, "支付渠道已存在");
        }

        LpPayProduct lpPayProduct = lpPayProductService.getByProductCode(payProductCode, null);
        if (lpPayProduct.getAuditStatus().equals(PublicEnum.YES.name())) {
            throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EFFECTIVE, "支付产品已生效，无法绑定！");
        }

        LpPayWay lpPayWay = new LpPayWay();
        lpPayWay.setPayProductCode(payProductCode);
        lpPayWay.setPayRate(payRate);
        lpPayWay.setPayWayCode(payWayCode);
        lpPayWay.setPayWayName(PayWayEnum.getEnum(payWayCode).getDesc());
        lpPayWay.setPayTypeCode(payTypeCode);
        lpPayWay.setPayTypeName(PayTypeEnum.getEnum(payTypeCode).getDesc());
        lpPayWay.setStatus(PublicStatusEnum.ACTIVE.name());
        lpPayWay.setCreateTime(new Date());
        lpPayWay.setId(StringUtil.get32UUID());
        saveData(lpPayWay);
    }

    /**
     * 根据支付产品获取支付方式
     */
    @Override
    public List<LpPayWay> listByProductCode(String payProductCode) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payProductCode", payProductCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return lpPayWayDao.listBy(paramMap);
    }

    /**
     * 获取所有支付方式
     */
    @Override
    public List<LpPayWay> listAll() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return lpPayWayDao.listBy(paramMap);
    }
}