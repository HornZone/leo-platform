package com.leo.platform.pay.account.service;

import java.math.BigDecimal;

import com.leo.platform.pay.account.entity.LpAccount;
import com.leo.platform.pay.common.exception.BizException;

/**
 * 账户操作service接口
 */
public interface LpAccountTransactionService {

    /** 加款:有银行流水 **/
    LpAccount creditToAccount(String userNo, BigDecimal amount, String requestNo, String bankTrxNo, String trxType,
        String remark) throws BizException;

    /** 减款 :有银行流水 **/
    LpAccount debitToAccount(String userNo, BigDecimal amount, String requestNo, String bankTrxNo, String trxType,
        String remark) throws BizException;

    /** 加款 **/
    LpAccount creditToAccount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark)
        throws BizException;

    /** 减款 **/
    LpAccount debitToAccount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark)
        throws BizException;

    /** 冻结 **/
    LpAccount freezeAmount(String userNo, BigDecimal freezeAmount) throws BizException;

    /** 结算成功：解冻+减款 **/
    LpAccount unFreezeAmount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark)
        throws BizException;

    /** 结算失败：解冻 **/
    LpAccount unFreezeSettAmount(String userNo, BigDecimal amount) throws BizException;

    /** 更新账户历史中的结算状态，并且累加可结算金额 **/
    void settCollectSuccess(String accountNo, String collectDate, int riskDay, BigDecimal totalAmount)
        throws BizException;

}