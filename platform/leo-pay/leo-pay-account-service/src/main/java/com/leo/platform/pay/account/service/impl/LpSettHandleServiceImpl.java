package com.leo.platform.pay.account.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leo.platform.common.util.DateUtils;
import com.leo.platform.pay.account.dao.LpSettDailyCollectDao;
import com.leo.platform.pay.account.dao.LpSettRecordDao;
import com.leo.platform.pay.account.entity.LpAccount;
import com.leo.platform.pay.account.entity.LpSettDailyCollect;
import com.leo.platform.pay.account.entity.LpSettRecord;
import com.leo.platform.pay.account.enums.SettDailyCollectStatusEnum;
import com.leo.platform.pay.account.enums.SettDailyCollectTypeEnum;
import com.leo.platform.pay.account.enums.SettRecordStatusEnum;
import com.leo.platform.pay.account.exception.SettBizException;
import com.leo.platform.pay.account.service.LpAccountQueryService;
import com.leo.platform.pay.account.service.LpAccountTransactionService;
import com.leo.platform.pay.account.service.LpSettHandleService;
import com.leo.platform.pay.account.vo.DailyCollectAccountHistoryVo;
import com.leo.platform.pay.trade.enums.TrxTypeEnum;

/**
 * 结算核心业务处理实现类
 */
@Service("lpSettHandleService")
public class LpSettHandleServiceImpl implements LpSettHandleService {
    @Autowired
    private LpSettDailyCollectDao lpSettDailyCollectDao;
    @Autowired
    private LpSettRecordDao lpSettRecordDao;
    @Autowired
    private LpAccountTransactionService lpAccountTransactionService;
    @Autowired
    private LpAccountQueryService lpAccountQueryService;

    /**
     * 按单个商户发起每日待结算数据统计汇总.<br/>
     * 
     * @param userNo 用户编号.
     * @param endDate 汇总结束日期.
     * @param riskDay 风险预存期.
     * @param userName 用户名称
     * @param codeNum 企业代号
     */
    @Transactional(rollbackFor = Exception.class)
    public void dailySettlementCollect(String userNo, Date endDate, int riskDay, String userName) {
        // 根据用户编号查询账户
        LpAccount account = lpAccountQueryService.getAccountByUserNo(userNo);
        // 汇总日期
        String endDateStr = DateUtils.formatDate(endDate, "yyyy-MM-dd");
        // 汇总账户历史
        List<DailyCollectAccountHistoryVo> accountHistoryList =
            lpAccountQueryService.listDailyCollectAccountHistoryVo(account.getAccountNo(), endDateStr, riskDay, null);
        // 遍历统计
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (DailyCollectAccountHistoryVo collectVo : accountHistoryList) {
            // 累加可结算金额
            totalAmount = totalAmount.add(collectVo.getTotalAmount());
            // 保存结算汇总记录
            LpSettDailyCollect dailyCollect = new LpSettDailyCollect();
            dailyCollect.setAccountNo(collectVo.getAccountNo());
            dailyCollect.setUserName(userName);
            dailyCollect.setCollectDate(collectVo.getCollectDate());
            dailyCollect.setCollectType(SettDailyCollectTypeEnum.ALL.name());
            dailyCollect.setTotalAmount(collectVo.getTotalAmount());
            dailyCollect.setTotalCount(collectVo.getTotalNum());
            dailyCollect.setSettStatus(SettDailyCollectStatusEnum.SETTLLED.name());
            dailyCollect.setRiskDay(collectVo.getRiskDay());
            dailyCollect.setRemark("");
            dailyCollect.setEditTime(new Date());
            lpSettDailyCollectDao.insert(dailyCollect);
        }

        // 更新账户历史中的结算状态，并且累加可结算金额
        lpAccountTransactionService.settCollectSuccess(userNo, endDateStr, riskDay, totalAmount);
    }

    /**
     * 结算审核
     */
    public void audit(String settId, String settStatus, String remark) {
        LpSettRecord settRecord = lpSettRecordDao.getById(settId);
        if (!settRecord.getSettStatus().equals(SettRecordStatusEnum.WAIT_CONFIRM.name())) {
            throw SettBizException.SETT_STATUS_ERROR;
        }
        settRecord.setSettStatus(settStatus);
        settRecord.setEditTime(new Date());
        settRecord.setRemark(remark);
        lpSettRecordDao.update(settRecord);

        if (settStatus.equals(SettRecordStatusEnum.CANCEL.name())) {// 审核不通过
            // 解冻金额
            lpAccountTransactionService.unFreezeSettAmount(settRecord.getUserNo(), settRecord.getSettAmount());
        }
    }

    /**
     * 打款
     */
    @Transactional(rollbackFor = Exception.class)
    public void remit(String settId, String settStatus, String remark) {
        LpSettRecord settRecord = lpSettRecordDao.getById(settId);
        if (!settRecord.getSettStatus().equals(SettRecordStatusEnum.CONFIRMED.name())) {
            throw SettBizException.SETT_STATUS_ERROR;
        }
        settRecord.setSettStatus(settStatus);
        settRecord.setEditTime(new Date());
        settRecord.setRemitRemark(remark);
        settRecord.setRemitAmount(settRecord.getSettAmount());
        settRecord.setRemitConfirmTime(new Date());
        settRecord.setRemitRequestTime(new Date());
        lpSettRecordDao.update(settRecord);

        if (settStatus.equals(SettRecordStatusEnum.REMIT_FAIL.name())) {// 打款失败
            // 解冻金额
            lpAccountTransactionService.unFreezeSettAmount(settRecord.getUserNo(), settRecord.getSettAmount());
        } else if (settStatus.equals(SettRecordStatusEnum.REMIT_SUCCESS.name())) {
            lpAccountTransactionService.unFreezeAmount(settRecord.getUserNo(), settRecord.getSettAmount(),
                settRecord.getId(), TrxTypeEnum.REMIT.name(), remark);
        }
    }

}
