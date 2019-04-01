package com.leo.platform.pay.account.service;

import java.util.Map;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.account.entity.LpSettRecord;
import com.leo.platform.pay.common.exception.BizException;

/**
 * 结算查询service接口
 */
public interface LpSettQueryService {

    /**
     * 根据参数分页查询结算记录
     * 
     * @param pageParam
     * @param params ：map的key为 ：accountNo、userNo、settStatus...可以参考实体
     * 
     * @return
     * @throws BizException
     */
    public PageBean querySettRecordListPage(PageParam pageParam, Map<String, Object> params) throws BizException;

    /**
     * 根据参数分页查询结算日汇总记录
     * 
     * @param pageParam
     * @param params ：map的key为 ：accountNo...可以参考实体
     * 
     * @return
     * @throws BizException
     */
    public PageBean querySettDailyCollectListPage(PageParam pageParam, Map<String, Object> params) throws BizException;

    /**
     * 根据id获取数据
     * 
     * @param id
     * @return
     */
    public LpSettRecord getDataById(String id);
}