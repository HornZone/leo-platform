package com.leo.platform.pay.account.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leo.platform.common.enums.PublicStatusEnum;
import com.leo.platform.pay.account.dao.LpAccountDao;
import com.leo.platform.pay.account.entity.LpAccount;
import com.leo.platform.pay.common.dao.impl.BaseDaoImpl;

/**
 * 账户dao实现类
 */
@Repository
public class LpAccountDaoImpl extends BaseDaoImpl<LpAccount> implements LpAccountDao {

    public LpAccount getByAccountNo(String accountNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("accountNo", accountNo);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return this.getBy(paramMap);
    }

    public LpAccount getByUserNo(Map<String, Object> map) {
        return this.getSessionTemplate().selectOne(getStatement("getByUserNo"), map);
    }
}