package com.leo.platform.pay.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.platform.common.page.PageBean;
import com.leo.platform.common.page.PageParam;
import com.leo.platform.pay.user.dao.LpUserPayInfoDao;
import com.leo.platform.pay.user.entity.LpUserPayInfo;
import com.leo.platform.pay.user.service.LpUserPayInfoService;

/**
 * 用户第三方支付信息service实现类
 */
@Service("lpUserPayInfoService")
public class LpUserPayInfoServiceImpl implements LpUserPayInfoService {

    @Autowired
    private LpUserPayInfoDao lpUserPayInfoDao;

    @Override
    public void saveData(LpUserPayInfo lpUserPayInfo) {
        lpUserPayInfoDao.insert(lpUserPayInfo);
    }

    @Override
    public void updateData(LpUserPayInfo lpUserPayInfo) {
        lpUserPayInfoDao.update(lpUserPayInfo);
    }

    @Override
    public LpUserPayInfo getDataById(String id) {
        return lpUserPayInfoDao.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, LpUserPayInfo lpUserPayInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        return lpUserPayInfoDao.listPage(pageParam, paramMap);
    }

    /**
     * 通过商户编号获取商户支付配置信息
     * 
     * @param userNO
     * @return
     */
    @Override
    public LpUserPayInfo getByUserNo(String userNo, String payWayCode) {
        return lpUserPayInfoDao.getByUserNo(userNo, payWayCode);
    }
}