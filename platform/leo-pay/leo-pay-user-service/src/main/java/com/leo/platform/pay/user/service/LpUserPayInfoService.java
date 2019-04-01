package com.leo.platform.pay.user.service;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.user.entity.LpUserPayInfo;

/**
 * 用户第三方支付信息service接口
 */
public interface LpUserPayInfoService {

    /**
     * 保存
     */
    void saveData(LpUserPayInfo rpUserPayInfo);

    /**
     * 更新
     */
    void updateData(LpUserPayInfo rpUserPayInfo);

    /**
     * 根据id获取数据
     * 
     * @param id
     * @return
     */
    LpUserPayInfo getDataById(String id);

    /**
     * 获取分页数据
     * 
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, LpUserPayInfo rpUserPayInfo);

    /**
     * 通过商户编号获取商户支付配置信息
     * 
     * @param userNo
     * @param payWayCode
     * @return
     */
    public LpUserPayInfo getByUserNo(String userNo, String payWayCode);

}