package com.leo.platform.pay.account.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.account.dao.LpAccountDao;
import com.leo.platform.pay.account.entity.LpAccount;
import com.leo.platform.pay.account.service.LpAccountService;

/**
 * 账户service实现类
 */
@Service("lpAccountService")
public class LpAccountServiceImpl implements LpAccountService {

    @Autowired
    private LpAccountDao lpAccountDao;

    @Override
    public void saveData(LpAccount lpAccount) {
        lpAccountDao.insert(lpAccount);
    }

    @Override
    public void updateData(LpAccount lpAccount) {
        lpAccountDao.update(lpAccount);
    }

    @Override
    public LpAccount getDataById(String id) {
        return lpAccountDao.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, LpAccount lpAccount) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("accountNo", lpAccount.getAccountNo());
        return lpAccountDao.listPage(pageParam, paramMap);
    }

    @Override
    public LpAccount getDataByUserNo(String userNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userNo", userNo);
        return lpAccountDao.getBy(paramMap);
    }
}