package com.leo.platform.pay.account.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.account.dao.LpAccountHistoryDao;
import com.leo.platform.pay.account.entity.LpAccountHistory;
import com.leo.platform.pay.account.service.LpAccountHistoryService;

/**
 * 账户历史service实现类
 */
@Service("lpAccountHistoryService")
public class LpAccountHistoryServiceImpl implements LpAccountHistoryService {

    @Autowired
    private LpAccountHistoryDao lpAccountHistoryDao;

    @Override
    public void saveData(LpAccountHistory lpAccountHistory) {
        lpAccountHistoryDao.insert(lpAccountHistory);
    }

    @Override
    public void updateData(LpAccountHistory lpAccountHistory) {
        lpAccountHistoryDao.update(lpAccountHistory);
    }

    @Override
    public LpAccountHistory getDataById(String id) {
        return lpAccountHistoryDao.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, LpAccountHistory lpAccountHistory) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("accountNo", lpAccountHistory.getAccountNo());
        return lpAccountHistoryDao.listPage(pageParam, paramMap);
    }
}