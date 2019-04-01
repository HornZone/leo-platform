package com.leo.platform.pay.user.service;

import java.util.List;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.user.entity.LpPayProduct;
import com.platform.pay.user.exception.PayBizException;

/**
 * 支付产品service接口
 */
public interface LpPayProductService {

    /**
     * 保存
     */
    void saveData(LpPayProduct LpPayProduct);

    /**
     * 更新
     */
    void updateData(LpPayProduct LpPayProduct);

    /**
     * 根据id获取数据
     * 
     * @param id
     * @return
     */
    LpPayProduct getDataById(String id);

    /**
     * 获取分页数据
     * 
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, LpPayProduct rpPayProduct);

    /**
     * 根据产品编号获取支付产品
     * 
     * @param productCode
     * @return
     */
    LpPayProduct getByProductCode(String productCode, String auditStatus);

    /**
     * 创建支付产品
     * 
     * @param productCode
     * @param productName
     */
    void createPayProduct(String productCode, String productName) throws PayBizException;

    /**
     * 删除支付产品
     * 
     * @param productCode
     */
    void deletePayProduct(String productCode) throws PayBizException;

    /**
     * 获取所有支付产品
     * 
     * @param productCode
     */
    List<LpPayProduct> listAll();

    /**
     * 审核
     * 
     * @param productCode
     * @param auditStatus
     */
    void audit(String productCode, String auditStatus) throws PayBizException;

}