package com.leo.platform.pay.user.service;

import java.util.List;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.user.entity.LpPayWay;
import com.platform.pay.user.exception.PayBizException;

/**
 * 支付方式service接口
 */
public interface LpPayWayService {

    /**
     * 保存
     */
    void saveData(LpPayWay rpPayWay);

    /**
     * 更新
     */
    void updateData(LpPayWay rpPayWay);

    /**
     * 根据id获取数据
     * 
     * @param id
     * @return
     */
    LpPayWay getDataById(String id);

    /**
     * 根据支付方式、渠道编码获取数据
     * 
     * @param rpTypeCode
     * @return
     */
    LpPayWay getByPayWayTypeCode(String payProductCode, String payWayCode, String rpTypeCode);

    /**
     * 获取分页数据
     * 
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, LpPayWay rpPayWay);

    /**
     * 绑定支付费率
     * 
     * @param payWayCode
     * @param payTypeCode
     * @param payRate
     */
    void createPayWay(String payProductCode, String payWayCode, String payTypeCode, Double payRate)
        throws PayBizException;

    /**
     * 根据支付产品获取支付方式
     * 
     * @param payProductCode
     */
    List<LpPayWay> listByProductCode(String payProductCode);

    /**
     * 获取所有支付方式
     */
    List<LpPayWay> listAll();

}