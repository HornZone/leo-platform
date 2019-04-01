package com.leo.platform.upms.service.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.UserLastOnlineDao;
import com.leo.platform.upms.entity.userlastonline.UserLastOnline;
import com.leo.platform.upms.service.user.UserLastOnlineService;

@Service
public class UserLastOnlineServiceImpl extends BaseServiceImpl<UserLastOnline, Long> implements UserLastOnlineService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(UserLastOnlineServiceImpl.class);
    @Autowired
    @Qualifier("userLastOnlineDaoImpl")
    private UserLastOnlineDao userLastOnlineDao;

    public UserLastOnline findByUserId(Long userId) {
        return userLastOnlineDao.findByUserId(userId);
    }

    public void lastOnline(UserLastOnline lastOnline) {
        UserLastOnline dbLastOnline = findByUserId(lastOnline.getUserId());

        if (dbLastOnline == null) {
            dbLastOnline = lastOnline;
        } else {
            UserLastOnline.merge(lastOnline, dbLastOnline);
        }

        /* 增加登陆次数 */
        dbLastOnline.incLoginCount();

        /* 增加总在线时长 */
        dbLastOnline.incTotalOnlineTime();
        // 相对于save or update
        save(dbLastOnline);
    }

    // /////////////////////////

    @Override
    public void afterConstruct() {
        super.setBaseDao(userLastOnlineDao);
    }
}
