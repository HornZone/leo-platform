package com.leo.platform.pay.account.service;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.account.entity.LpAccountHistory;

/**
 * 账户历史service接口
 */
public interface LpAccountHistoryService {

    /**
     * 保存
     */
    void saveData(LpAccountHistory lpAccountHistory);

    /**
     * 更新
     */
    void updateData(LpAccountHistory lpAccountHistory);

    /**
     * 根据id获取数据
     * 
     * @param id
     * @return
     */
    LpAccountHistory getDataById(String id);

    /**
     * 获取分页数据
     * 
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, LpAccountHistory lpAccountHistory);

}