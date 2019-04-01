package com.leo.platform.upms.dao;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.upms.entity.userlastonline.UserLastOnline;

public interface UserLastOnlineDao extends BaseDao<UserLastOnline, Long> {
    UserLastOnline findByUserId(Long userId);
}
