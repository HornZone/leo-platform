/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.leo.platform.pay.trade.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leo.platform.pay.account.service.LpAccountTransactionService;
import com.leo.platform.pay.notify.service.LeoPayNotifyService;
import com.leo.platform.pay.reconciliation.entity.LpAccountCheckMistake;
import com.leo.platform.pay.trade.dao.LpTradePaymentOrderDao;
import com.leo.platform.pay.trade.dao.LpTradePaymentRecordDao;
import com.leo.platform.pay.trade.entity.LpTradePaymentOrder;
import com.leo.platform.pay.trade.entity.LpTradePaymentRecord;
import com.leo.platform.pay.trade.enums.TradeStatusEnum;
import com.leo.platform.pay.trade.enums.TrxTypeEnum;
import com.leo.platform.pay.trade.exception.TradeBizException;
import com.leo.platform.pay.trade.service.LpTradeReconciliationService;

/**
 * <b>功能说明:交易模块对账差错实现</b>
 * 
 * @author Peter <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Service("lpTradeReconciliationService")
public class LpTradeReconciliationServiceImpl implements LpTradeReconciliationService {

    private static final Logger LOG = LoggerFactory.getLogger(LpTradeReconciliationServiceImpl.class);

    @Autowired
    private LpTradePaymentOrderDao lpTradePaymentOrderDao;
    @Autowired
    private LpTradePaymentRecordDao lpTradePaymentRecordDao;
    @Autowired
    private LeoPayNotifyService lpNotifyService;
    @Autowired
    private LpAccountTransactionService lpAccountTransactionService;

    /**
     * 平台成功，银行记录不存在，或者银行失败，以银行为准
     * 
     * @param trxNo 平台交易流水
     */

    // @Transactional(rollbackFor = Exception.class)
    public void bankMissOrBankFailBaseBank(String trxNo) {
        LOG.info("===== 把订单改为失败，并减款开始========");
        LpTradePaymentRecord record = lpTradePaymentRecordDao.getByTrxNo(trxNo);
        if (record == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "trxNo[" + trxNo + "]的支付记录不存在");
        }

        if (!record.getStatus().equals(TradeStatusEnum.SUCCESS.name())) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_STATUS_NOT_SUCCESS, "trxNo[" + trxNo
                + "]的支付记录状态不是success");
        }

        // 改支付记录状态
        record.setStatus(TradeStatusEnum.FAILED.name());
        record.setRemark("对账差错处理,订单改为失败，并减款.");
        lpTradePaymentRecordDao.update(record);

        // 改支付订单状态
        LpTradePaymentOrder order =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(record.getMerchantNo(),
                record.getMerchantOrderNo());
        order.setStatus(TradeStatusEnum.FAILED.name());
        order.setRemark("对账差错处理,订单改为失败，并减款.");
        lpTradePaymentOrderDao.update(order);

        // 减款
        lpAccountTransactionService.debitToAccount(record.getMerchantNo(),
            record.getOrderAmount().subtract(record.getPlatIncome()), record.getBankOrderNo(),
            TrxTypeEnum.ERRORHANKLE.name(), "对账差错处理,订单改为失败，并减款.");
        LOG.info("===== 把订单改为失败，并减款成功========");
    }

    /**
     * 银行支付成功，平台失败.
     * 
     * @param trxNo 平台交易流水
     * @param bankTrxNo 银行返回流水
     */
    @Transactional(rollbackFor = Exception.class)
    public void platFailBankSuccess(String trxNo, String bankTrxNo) {

        LOG.info("===== 银行支付成功，平台失败.========");

        LpTradePaymentRecord record = lpTradePaymentRecordDao.getByTrxNo(trxNo);
        if (record == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "trxNo[" + trxNo + "]的支付记录不存在");
        }

        record.setBankTrxNo(bankTrxNo);
        record.setBankReturnMsg("SUCCESS");
        record.setStatus(TradeStatusEnum.SUCCESS.name());
        lpTradePaymentRecordDao.update(record);

        LpTradePaymentOrder lpTradePaymentOrder =
            lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(record.getMerchantNo(),
                record.getMerchantOrderNo());
        lpTradePaymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());
        lpTradePaymentOrderDao.update(lpTradePaymentOrder);

        lpAccountTransactionService.creditToAccount(record.getMerchantNo(),
            record.getOrderAmount().subtract(record.getPlatIncome()), record.getBankOrderNo(), record.getBankTrxNo(),
            record.getTrxType(), record.getRemark());

        lpNotifyService.notifySend(record.getNotifyUrl(), record.getMerchantOrderNo(), record.getMerchantNo());

    }

    /**
     * 处理金额不匹配异常
     * 
     * @param mistake 差错记录
     * @param isBankMore 是否是银行金额多
     * @param baseOnBank 是否以银行为准
     */

    @Transactional(rollbackFor = Exception.class)
    public void handleAmountMistake(LpAccountCheckMistake mistake, boolean isBankMore) {

        LOG.info("=====开始处理金额差错,是否是银行金额多[" + isBankMore + "],且都是以银行数据为准========");
        String trxNo = mistake.getTrxNo();
        LpTradePaymentRecord record = lpTradePaymentRecordDao.getByTrxNo(trxNo);
        if (record == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "trxNo[" + trxNo + "]的支付记录不存在");
        }

        if (!record.getStatus().equals(TradeStatusEnum.SUCCESS.name())) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_STATUS_NOT_SUCCESS, "请先处理该订单状态不符的差错");
        }
        // 银行支付金额
        BigDecimal bankAmount = mistake.getBankAmount();
        // 银行成本
        BigDecimal bankFee = mistake.getBankFee();
        // 平台订单支付金额
        BigDecimal orderAmount = record.getOrderAmount();
        // 平台已收商户的手续费
        BigDecimal fee = record.getPlatIncome();
        // 实际需要手续费
        BigDecimal needFee =
            bankAmount.multiply(record.getFeeRate()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        // 订单金额差
        BigDecimal subOrderAmount = bankAmount.subtract(orderAmount).abs();
        // 手续费差
        BigDecimal subFee = needFee.subtract(fee).abs();

        /** 如果是银行金额多 ----加 **/
        if (isBankMore) {
            /** 以银行数据为准 **/

            record.setOrderAmount(bankAmount);
            record.setPlatCost(bankFee);
            record.setPlatIncome(needFee);
            record.setRemark("差错调整：订单金额加[" + subOrderAmount + "],手续费加[" + subFee + "],成本变成[" + bankFee + "]");
            lpTradePaymentRecordDao.update(record);

            LpTradePaymentOrder lpTradePaymentOrder =
                lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(record.getMerchantNo(),
                    record.getMerchantOrderNo());
            lpTradePaymentOrder.setOrderAmount(bankAmount);
            lpTradePaymentOrder.setRemark("差错处理:订单金额由[" + orderAmount + "]改为[" + bankAmount + "]");
            lpTradePaymentOrderDao.update(lpTradePaymentOrder);

            // 加款
            lpAccountTransactionService.creditToAccount(record.getMerchantNo(), subOrderAmount.subtract(subFee),
                record.getBankOrderNo(), record.getBankTrxNo(), TrxTypeEnum.ERRORHANKLE.name(), "差错处理加款。");
        }
        /** 平台金额多 -----减 **/
        else {
            /** 以银行数据为准 **/

            record.setOrderAmount(bankAmount);
            record.setPlatCost(bankFee);
            record.setPlatIncome(needFee);
            record.setRemark("差错调整：订单金额减[" + subOrderAmount + "],手续费减[" + subFee + "],成本变成[" + bankFee + "]");
            lpTradePaymentRecordDao.update(record);

            LpTradePaymentOrder lpTradePaymentOrder =
                lpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(record.getMerchantNo(),
                    record.getMerchantOrderNo());
            lpTradePaymentOrder.setOrderAmount(bankAmount);
            lpTradePaymentOrder.setRemark("差错处理:订单金额由[" + orderAmount + "]改为[" + bankAmount + "]");
            lpTradePaymentOrderDao.update(lpTradePaymentOrder);

            // 减款
            lpAccountTransactionService.debitToAccount(record.getMerchantNo(), subOrderAmount.subtract(subFee),
                record.getBankOrderNo(), record.getBankTrxNo(), TrxTypeEnum.ERRORHANKLE.name(), "差错处理减款。");
        }
    }

    /**
     * 处理手续费不匹配差错（默认以银行为准）
     * 
     * @param mistake
     */

    @Transactional(rollbackFor = Exception.class)
    public void handleFeeMistake(LpAccountCheckMistake mistake) {

        String trxNo = mistake.getTrxNo();
        LpTradePaymentRecord record = lpTradePaymentRecordDao.getByTrxNo(trxNo);
        if (record == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "trxNo[" + trxNo + "]的支付记录不存在");
        }

        if (!record.getStatus().equals(TradeStatusEnum.SUCCESS.name())) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_STATUS_NOT_SUCCESS, "请先处理该订单状态不符的差错");
        }

        BigDecimal oldBankFee = record.getPlatCost();
        BigDecimal bankFee = mistake.getBankFee();

        record.setPlatCost(bankFee);
        record.setRemark("差错处理:银行成本由[" + oldBankFee + "]改为[" + bankFee + "]");
        lpTradePaymentRecordDao.update(record);
    }
}
