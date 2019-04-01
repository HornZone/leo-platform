package com.leo.platform.pay.account.service;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.account.entity.LpAccount;

/**
 * 账户service接口
 */
public interface LpAccountService {

    /**
     * 保存
     */
    void saveData(LpAccount lpAccount);

    /**
     * 更新
     */
    void updateData(LpAccount lpAccount);

    /**
     * 根据id获取数据
     * 
     * @param id
     * @return
     */
    LpAccount getDataById(String id);

    /**
     * 获取分页数据
     * 
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, LpAccount lpAccount);

    /**
     * 根据userNo获取数据
     * 
     * @param userNo
     * @return
     */
    LpAccount getDataByUserNo(String userNo);

}