package com.leo.platform.pay.user.service;

import com.leo.platform.pay.user.entity.LpUserBankAccount;

/**
 * 用户银行账户service接口
 */
public interface LpUserBankAccountService {

    /**
     * 保存
     */
    void saveData(LpUserBankAccount rpUserBankAccount);

    /**
     * 更新
     */
    void updateData(LpUserBankAccount rpUserBankAccount);

    /**
     * 根据用户编号获取银行账户
     */
    LpUserBankAccount getByUserNo(String userNo);

    /**
     * 创建或更新
     * 
     * @param rpUserBankAccount
     */
    void createOrUpdate(LpUserBankAccount rpUserBankAccount);
}