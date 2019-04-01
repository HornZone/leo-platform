package com.leo.platform.pay.account.dao;

import java.util.Map;

import com.leo.platform.pay.account.entity.LpAccount;
import com.leo.platform.pay.common.dao.BaseDao;

/**
 * 账户dao
 */
public interface LpAccountDao extends BaseDao<LpAccount> {
    LpAccount getByAccountNo(String accountNo);

    LpAccount getByUserNo(Map<String, Object> map);
}