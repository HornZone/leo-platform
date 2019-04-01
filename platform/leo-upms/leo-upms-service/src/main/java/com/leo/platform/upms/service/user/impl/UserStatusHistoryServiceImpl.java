package com.leo.platform.upms.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.common.model.page.FieldOrder;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.UserStatusHistoryDao;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.entity.user.UserStatus;
import com.leo.platform.upms.entity.userstatushistory.UserStatusHistory;
import com.leo.platform.upms.model.userstatushistory.UserStatusHistoryModel;
import com.leo.platform.upms.service.user.UserStatusHistoryService;

@Service
public class UserStatusHistoryServiceImpl extends BaseServiceImpl<UserStatusHistory, Long> implements
    UserStatusHistoryService {

    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(UserStatusHistoryServiceImpl.class);

    @Autowired
    @Qualifier("userStatusHistoryDaoImpl")
    private UserStatusHistoryDao userStatusHistoryDao;

    public void log(User opUser, User user, UserStatus newStatus, String reason) {
        UserStatusHistory history = new UserStatusHistory();
        history.setUser(user);
        history.setOpUser(opUser);
        history.setOpDate(new Date());
        history.setStatus(newStatus.toString());
        history.setReason(reason);
        super.save(history);
    }

    public UserStatusHistory findLastHistory(final User user) {
        // 根据user获取userstatushistory,如果有多个， 根据opDate排序后取最新的一个返回
        UserStatusHistoryModel userStatusHistoryModel = new UserStatusHistoryModel();
        FieldOrder fieldOrder = new FieldOrder("opDate");
        List<FieldOrder> fieldOrders = new ArrayList();
        fieldOrders.add(fieldOrder);

        userStatusHistoryModel.setOrderFields(fieldOrders);
        userStatusHistoryModel.setUserId(user.getId());
        userStatusHistoryModel.setObjectsPerPage(1);
        userStatusHistoryModel.setPageNumber(1);

        PageList<UserStatusHistory> userStatusHistories = userStatusHistoryDao.getPageList(userStatusHistoryModel);

        if (userStatusHistories.getList().size() >= 1) {
            return userStatusHistories.getList().get(0);
        }
        return null;
    }

    public String getLastReason(User user) {
        UserStatusHistory history = findLastHistory(user);
        if (history == null) {
            return "";
        }
        return history.getReason();
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(userStatusHistoryDao);
    }

    // /////////////////////////////////////////
}
