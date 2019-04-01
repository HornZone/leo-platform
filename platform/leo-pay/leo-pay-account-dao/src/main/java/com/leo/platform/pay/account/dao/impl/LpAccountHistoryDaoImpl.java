 
package com.leo.platform.pay.account.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leo.platform.pay.account.dao.LpAccountHistoryDao;
import com.leo.platform.pay.account.entity.LpAccountHistory;
import com.leo.platform.pay.account.vo.DailyCollectAccountHistoryVo;
import com.leo.platform.pay.common.dao.impl.BaseDaoImpl;

/**
 * 账户历史dao实现类  
 */
@Repository
public class LpAccountHistoryDaoImpl extends BaseDaoImpl<LpAccountHistory> implements LpAccountHistoryDao {

    public List<LpAccountHistory> listPageByParams(Map<String, Object> params) {
        return this.listBy(params);
    }

    public List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(Map<String, Object> params) {
        return this.getSessionTemplate().selectList(getStatement("listDailyCollectAccountHistoryVo"), params);
    }

    /** 更新账户风险预存期外的账户历史记录记为结算完成 **/
    public void updateCompleteSettTo100(Map<String, Object> params) {
        this.getSessionTemplate().update(getStatement("updateCompleteSettTo100"), params);
    }
}