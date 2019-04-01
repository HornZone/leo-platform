package com.leo.platform.pay.account.dao;

import java.util.List;
import java.util.Map;

import com.leo.platform.pay.account.entity.LpAccountHistory;
import com.leo.platform.pay.account.vo.DailyCollectAccountHistoryVo;
import com.leo.platform.pay.common.dao.BaseDao;

/**
 * 账户历史dao
 */
public interface LpAccountHistoryDao extends BaseDao<LpAccountHistory> {
    List<LpAccountHistory> listPageByParams(Map<String, Object> params);

    List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(Map<String, Object> params);

    /** 更新账户风险预存期外的账户历史记录记为结算完成 **/
    void updateCompleteSettTo100(Map<String, Object> params);
}