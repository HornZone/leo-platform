package com.leo.platform.upms.service.user;

import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.entity.user.UserStatus;
import com.leo.platform.upms.entity.userstatushistory.UserStatusHistory;

public interface UserStatusHistoryService extends BaseService<UserStatusHistory, Long> {

    void log(User opUser, User user, UserStatus newStatus, String reason);

    UserStatusHistory findLastHistory(final User user);

    String getLastReason(User user);
}
