package com.leo.platform.upms.service.user.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.common.model.page.PageList;
import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.UserOnlineDao;
import com.leo.platform.upms.entity.useronline.UserOnline;
import com.leo.platform.upms.model.useronline.UserOnlineModel;
import com.leo.platform.upms.service.user.UserOnlineService;

@Service
public class UserOnlineServiceImpl extends BaseServiceImpl<UserOnline, String> implements UserOnlineService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(UserOnlineServiceImpl.class);
    @Autowired
    @Qualifier("userOnlineDaoImpl")
    private UserOnlineDao userOnlineDao;

    /**
     * 上线
     * 
     * @param userOnline
     */
    public void online(UserOnline userOnline) {
        save(userOnline);
    }

    /**
     * 下线
     * 
     * @param sid
     */
    public void offline(String sid) {
        UserOnline userOnline = this.findOne(sid);
        if (userOnline != null) {
            delete(userOnline);
        }
        // 游客 无需记录上次访问记录
        // 此处使用数据库的触发器完成同步
        // if(userOnline.getUserId() == null) {
        // userLastOnlineService.lastOnline(UserLastOnline.fromUserOnline(userOnline));
        // }
    }

    /**
     * 批量下线
     * 
     * @param needOfflineIdList
     */
    public void batchOffline(List<String> needOfflineIdList) {
        userOnlineDao.batchDelete(needOfflineIdList);
    }

    /**
     * 无效的UserOnline
     * 
     * @return
     */
    public PageList<UserOnline> findExpiredUserOnlineList(Date expiredDate) {
        UserOnlineModel userOnlineModel = new UserOnlineModel(expiredDate);
        return userOnlineDao.findExpiredUserOnlineList(userOnlineModel);
    }

    // ////////////////////////////////////////////////////////////////

    @Override
    public void afterConstruct() {
        super.setBaseDao(userOnlineDao);
    }
}
