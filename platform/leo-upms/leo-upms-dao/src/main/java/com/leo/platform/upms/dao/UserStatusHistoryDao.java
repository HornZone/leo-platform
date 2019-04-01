package com.leo.platform.upms.dao;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.upms.entity.userstatushistory.UserStatusHistory;
import com.leo.platform.upms.model.userstatushistory.UserStatusHistoryModel;

public interface UserStatusHistoryDao extends BaseDao<UserStatusHistory, Long> {
    PageList<UserStatusHistory> getPageList(UserStatusHistoryModel userStatusHistoryModel);
}
