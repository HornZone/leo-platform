package com.leo.platform.pay.user.service;

import java.math.BigDecimal;
import java.util.List;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.user.entity.LpUserInfo;

/**
 * 用户信息service接口
 */
public interface LpUserInfoService {

    /**
     * 保存
     */
    void saveData(LpUserInfo rpUserInfo);

    /**
     * 更新
     */
    void updateData(LpUserInfo rpUserInfo);

    /**
     * 根据id获取数据
     * 
     * @param id
     * @return
     */
    LpUserInfo getDataById(String id);

    /**
     * 获取分页数据
     * 
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, LpUserInfo rpUserInfo);

    /**
     * 用户线下注册
     * 
     * @param userName 用户名
     * @param mobile 手机号
     * @param password 密码
     * @return
     */
    void registerOffline(String userName, String mobile, String password);

    /**
     * 根据商户编号获取商户信息
     * 
     * @param merchantNo
     * @return
     */
    LpUserInfo getDataByMerchentNo(String merchantNo);

    /**
     * 根据手机号获取商户信息
     * 
     * @param mobile
     * @return
     */
    LpUserInfo getDataByMobile(String mobile);

    /**
     * 获取所有用户
     * 
     * @return
     */
    List<LpUserInfo> listAll();

    void launchSett(String userNo, BigDecimal settAmount);

    void launchAutoSett(String userNo);

}