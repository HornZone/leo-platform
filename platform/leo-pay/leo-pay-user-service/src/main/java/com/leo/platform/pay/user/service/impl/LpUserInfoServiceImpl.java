package com.leo.platform.pay.user.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leo.platform.common.enums.PublicStatusEnum;
import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.common.util.EncryptUtil;
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.pay.account.dao.LpSettRecordDao;
import com.leo.platform.pay.account.entity.LpAccount;
import com.leo.platform.pay.account.entity.LpSettRecord;
import com.leo.platform.pay.account.enums.SettModeTypeEnum;
import com.leo.platform.pay.account.enums.SettRecordStatusEnum;
import com.leo.platform.pay.account.exception.AccountBizException;
import com.leo.platform.pay.account.service.LpAccountQueryService;
import com.leo.platform.pay.account.service.LpAccountService;
import com.leo.platform.pay.account.service.LpAccountTransactionService;
import com.leo.platform.pay.account.util.AccountConfigUtil;
import com.leo.platform.pay.common.exception.BizException;
import com.leo.platform.pay.user.dao.LpUserInfoDao;
import com.leo.platform.pay.user.entity.LpUserBankAccount;
import com.leo.platform.pay.user.entity.LpUserInfo;
import com.leo.platform.pay.user.enums.BankAccountTypeEnum;
import com.leo.platform.pay.user.service.BuildNoService;
import com.leo.platform.pay.user.service.LpUserBankAccountService;
import com.leo.platform.pay.user.service.LpUserInfoService;
import com.platform.pay.user.exception.UserBizException;

/**
 * 用户信息service实现类
 */
@Service("lpUserInfoService")
public class LpUserInfoServiceImpl implements LpUserInfoService {

    @Autowired
    private LpUserInfoDao lpUserInfoDao;

    @Autowired
    private LpSettRecordDao lpSettRecordDao;

    @Autowired
    private BuildNoService buildNoService;

    @Autowired
    private LpUserBankAccountService lpUserBankAccountService;

    @Autowired
    private LpAccountTransactionService lpAccountTransactionService;

    @Autowired
    private LpAccountService lpAccountService;

    @Autowired
    private LpAccountQueryService lpAccountQueryService;

    @Autowired
    private LpUserInfoService lpUserInfoService;

    @Override
    public void saveData(LpUserInfo lpUserInfo) {
        lpUserInfoDao.insert(lpUserInfo);
    }

    @Override
    public void updateData(LpUserInfo lpUserInfo) {
        lpUserInfoDao.update(lpUserInfo);
    }

    @Override
    public LpUserInfo getDataById(String id) {
        return lpUserInfoDao.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, LpUserInfo lpUserInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userNo", lpUserInfo.getUserNo());
        return lpUserInfoDao.listPage(pageParam, paramMap);
    }

    /**
     * 用户线下注册
     * 
     * @param userName 用户名
     * @param mobile 手机号
     * @param password 密码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerOffline(String userName, String mobile, String password) {
        String userNo = buildNoService.buildUserNo();
        String accountNo = buildNoService.buildAccountNo();

        // 生成用户信息
        LpUserInfo lpUserInfo = new LpUserInfo();
        lpUserInfo.setAccountNo(accountNo);
        lpUserInfo.setCreateTime(new Date());
        lpUserInfo.setId(StringUtil.get32UUID());
        lpUserInfo.setStatus(PublicStatusEnum.ACTIVE.name());
        lpUserInfo.setUserName(userName);
        lpUserInfo.setUserNo(userNo);
        lpUserInfo.setMobile(mobile);
        lpUserInfo.setPassword(EncryptUtil.encodeMD5String(password));
        lpUserInfo.setPayPwd(EncryptUtil.encodeMD5String("123456"));
        lpUserInfo.setVersion(0);
        this.saveData(lpUserInfo);

        // 生成账户信息
        LpAccount lpAccount = new LpAccount();
        lpAccount.setAccountNo(accountNo);
        lpAccount.setAccountType("0");
        lpAccount.setCreateTime(new Date());
        lpAccount.setEditTime(new Date());
        lpAccount.setId(StringUtil.get32UUID());
        lpAccount.setStatus(PublicStatusEnum.ACTIVE.name());
        lpAccount.setUserNo(userNo);
        lpAccount.setBalance(new BigDecimal("0"));
        lpAccount.setSecurityMoney(new BigDecimal("0"));
        lpAccount.setSettAmount(new BigDecimal("0"));
        lpAccount.setTodayExpend(new BigDecimal("0"));
        lpAccount.setTodayIncome(new BigDecimal("0"));
        lpAccount.setUnbalance(new BigDecimal("0"));
        lpAccount.setTotalExpend(new BigDecimal("0"));
        lpAccount.setTotalIncome(new BigDecimal("0"));
        lpAccountService.saveData(lpAccount);
    }

    /**
     * 根据商户编号获取商户信息
     * 
     * @param merchantNo
     * @return
     */
    @Override
    public LpUserInfo getDataByMerchentNo(String merchantNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userNo", merchantNo);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return lpUserInfoDao.getBy(paramMap);
    }

    /**
     * 根据手机号获取商户信息
     * 
     * @param mobile
     * @return
     */
    @Override
    public LpUserInfo getDataByMobile(String mobile) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return lpUserInfoDao.getBy(paramMap);
    }

    /**
     * 获取所有用户
     * 
     * @return
     */
    @Override
    public List<LpUserInfo> listAll() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return lpUserInfoDao.listBy(paramMap);
    }

    /**
     * 发起结算--对应与接口
     * 
     * @param userNo
     * @param accountNo
     * @param settAmount
     * @param bankAccount
     */
    public void launchSett(String userNo, BigDecimal settAmount) {
        LpAccount account = lpAccountQueryService.getAccountByUserNo(userNo);
        LpUserInfo userInfo = getDataByMerchentNo(userNo);
        LpUserBankAccount lpUserBankAccount = lpUserBankAccountService.getByUserNo(userNo);
        BigDecimal availableAmount = account.getAvailableSettAmount();
        if (settAmount.compareTo(availableAmount) > 0) {
            // 金额超限
            throw AccountBizException.ACCOUNT_SUB_AMOUNT_OUTLIMIT;
        }
        if (lpUserBankAccount == null) {
            throw UserBizException.USER_BANK_ACCOUNT_IS_NULL;

        }

        String settType = SettModeTypeEnum.SELFHELP_SETTLE.name();
        this.launchSett(userNo, userInfo.getUserName(), account.getAccountNo(), settAmount, lpUserBankAccount, settType);

    }

    /**
     * 发起结算
     * 
     * @param userNo
     * @param accountNo
     * @param settAmount
     * @param bankAccount
     * @param settType 发起结算方式:手动、自动
     */

    @Transactional(rollbackFor = Exception.class)
    private void launchSett(String userNo, String userName, String accountNo, BigDecimal settAmount,
        LpUserBankAccount bankAccount, String settType) {

        // 所行查询账户
        LpSettRecord settRecord = new LpSettRecord();
        settRecord.setAccountNo(accountNo);
        settRecord.setCountry("中国");
        settRecord.setProvince(bankAccount.getProvince());
        settRecord.setCity(bankAccount.getCity());
        settRecord.setAreas(bankAccount.getAreas());
        settRecord.setBankAccountAddress(bankAccount.getStreet());
        settRecord.setBankAccountName(bankAccount.getBankAccountName());
        settRecord.setBankCode(bankAccount.getBankCode());
        settRecord.setBankName(bankAccount.getBankName());
        settRecord.setBankAccountNo(bankAccount.getBankAccountNo());
        settRecord.setBankAccountType(bankAccount.getBankAccountType());
        settRecord.setOperatorLoginname("");
        settRecord.setOperatorRealname("");
        settRecord.setRemitAmount(settAmount);
        settRecord.setRemitRequestTime(new Date());
        settRecord.setSettAmount(settAmount);
        settRecord.setSettFee(BigDecimal.ZERO);
        settRecord.setSettMode(settType);
        settRecord.setSettStatus(SettRecordStatusEnum.WAIT_CONFIRM.name());
        settRecord.setUserName(userName);
        settRecord.setUserNo(userNo);
        settRecord.setMobileNo(bankAccount.getMobileNo());
        settRecord.setEditTime(new Date());
        lpSettRecordDao.insert(settRecord);

        // 冻结准备结算出去的资金
        lpAccountTransactionService.freezeAmount(userNo, settAmount);
    }

    /**
     * 发起自动结算
     * 
     * @param userNo
     */
    public void launchAutoSett(String userNo) {
        LpUserInfo userInfo = lpUserInfoService.getDataByMerchentNo(userNo);
        LpAccount account = lpAccountQueryService.getAccountByUserNo(userNo);
        BigDecimal settAmount = account.getAvailableSettAmount();
        String settMinAmount = AccountConfigUtil.readConfig("sett_min_amount");
        if (settAmount.compareTo(new BigDecimal(settMinAmount)) == -1) {
            throw new BizException("每次发起结算的金额必须大于:" + settMinAmount);
        }

        LpUserBankAccount lpUserBankAccount = lpUserBankAccountService.getByUserNo(userNo);
        if (lpUserBankAccount == null) {
            throw new BizException("没有结算银行卡信息，请先绑定结算银行卡");
        }

        // 根据银行卡信息判断收款账户的类型
        String bankType = lpUserBankAccount.getBankAccountType();

        // 如果是对私账户，需要控制 1.单笔上线是否小于5W
        if (bankType.equals(BankAccountTypeEnum.PRIVATE_DEBIT_ACCOUNT.name())) {
            // 结算的金额最大值
            String settMaxAmount = AccountConfigUtil.readConfig("sett_max_amount");
            if (settAmount.compareTo(new BigDecimal(settMaxAmount)) == 1) {
                throw new BizException("每次发起结算的金额必须小于:" + settMaxAmount);
            }
        }
        // 结算记录中的userNo存企业表中企业代号
        String userName = userInfo.getUserName();
        String accountNo = account.getAccountNo();
        String settType = SettModeTypeEnum.REGULAR_SETTLE.name();
        this.launchSett(userNo, userName, accountNo, settAmount, lpUserBankAccount, settType);
    }

}