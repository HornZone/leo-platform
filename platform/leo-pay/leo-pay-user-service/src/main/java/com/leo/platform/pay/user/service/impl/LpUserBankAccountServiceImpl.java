package com.leo.platform.pay.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.platform.common.enums.PublicEnum;
import com.leo.platform.common.enums.PublicStatusEnum;
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.pay.user.dao.LpUserBankAccountDao;
import com.leo.platform.pay.user.entity.LpUserBankAccount;
import com.leo.platform.pay.user.enums.BankCodeEnum;
import com.leo.platform.pay.user.service.LpUserBankAccountService;

/**
 * 用户银行账户service实现类
 */
@Service("lpUserBankAccountService")
public class LpUserBankAccountServiceImpl implements LpUserBankAccountService {

    @Autowired
    private LpUserBankAccountDao lpUserBankAccountDao;

    @Override
    public void saveData(LpUserBankAccount lpUserBankAccount) {
        lpUserBankAccountDao.insert(lpUserBankAccount);
    }

    @Override
    public void updateData(LpUserBankAccount lpUserBankAccount) {
        lpUserBankAccountDao.update(lpUserBankAccount);
    }

    /**
     * 根据用户编号获取银行账户
     */
    @Override
    public LpUserBankAccount getByUserNo(String userNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userNo", userNo);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return lpUserBankAccountDao.getBy(paramMap);
    }

    /**
     * 创建或更新
     * 
     * @param lpUserBankAccount
     */
    @Override
    public void createOrUpdate(LpUserBankAccount lpUserBankAccount) {
        LpUserBankAccount bankAccount = getByUserNo(lpUserBankAccount.getUserNo());
        if (bankAccount == null) {
            bankAccount = new LpUserBankAccount();
            bankAccount.setId(StringUtil.get32UUID());
            bankAccount.setCreateTime(new Date());
            bankAccount.setEditTime(new Date());
            bankAccount.setAreas(lpUserBankAccount.getAreas());
            bankAccount.setBankAccountName(lpUserBankAccount.getBankAccountName());
            bankAccount.setBankAccountNo(lpUserBankAccount.getBankAccountNo());
            bankAccount.setBankAccountType(lpUserBankAccount.getBankAccountType());
            bankAccount.setBankCode(lpUserBankAccount.getBankCode());
            bankAccount.setBankName(BankCodeEnum.getEnum(lpUserBankAccount.getBankCode()).getDesc());
            bankAccount.setCardNo(lpUserBankAccount.getCardNo());
            bankAccount.setCardType(lpUserBankAccount.getCardType());
            bankAccount.setCity(lpUserBankAccount.getCity());
            bankAccount.setIsDefault(PublicEnum.YES.name());
            bankAccount.setMobileNo(lpUserBankAccount.getMobileNo());
            bankAccount.setProvince(lpUserBankAccount.getProvince());
            bankAccount.setRemark(lpUserBankAccount.getRemark());
            bankAccount.setStatus(PublicStatusEnum.ACTIVE.name());
            bankAccount.setUserNo(lpUserBankAccount.getUserNo());
            bankAccount.setStreet(lpUserBankAccount.getStreet());
            lpUserBankAccountDao.insert(bankAccount);
        } else {
            bankAccount.setEditTime(new Date());
            bankAccount.setAreas(lpUserBankAccount.getAreas());
            bankAccount.setBankAccountName(lpUserBankAccount.getBankAccountName());
            bankAccount.setBankAccountNo(lpUserBankAccount.getBankAccountNo());
            bankAccount.setBankAccountType(lpUserBankAccount.getBankAccountType());
            bankAccount.setBankCode(lpUserBankAccount.getBankCode());
            bankAccount.setBankName(BankCodeEnum.getEnum(lpUserBankAccount.getBankCode()).getDesc());
            bankAccount.setCardNo(lpUserBankAccount.getCardNo());
            bankAccount.setCardType(lpUserBankAccount.getCardType());
            bankAccount.setCity(lpUserBankAccount.getCity());
            bankAccount.setIsDefault(PublicEnum.YES.name());
            bankAccount.setMobileNo(lpUserBankAccount.getMobileNo());
            bankAccount.setProvince(lpUserBankAccount.getProvince());
            bankAccount.setRemark(lpUserBankAccount.getRemark());
            bankAccount.setStatus(PublicStatusEnum.ACTIVE.name());
            bankAccount.setUserNo(lpUserBankAccount.getUserNo());
            bankAccount.setStreet(lpUserBankAccount.getStreet());
            lpUserBankAccountDao.update(bankAccount);
        }
    }
}