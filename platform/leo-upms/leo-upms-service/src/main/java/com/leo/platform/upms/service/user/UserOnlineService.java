package com.leo.platform.upms.service.user;

import java.util.Date;
import java.util.List;

import com.leo.platform.common.model.page.PageList;
import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.useronline.UserOnline;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2016年10月16日 上午10:50:54
 * 
 */
public interface UserOnlineService extends BaseService<UserOnline, String> {

    /**
     * 上线
     * 
     * @param userOnline
     */
    public void online(UserOnline userOnline);

    /**
     * 下线
     * 
     * @param sid
     */
    public void offline(String sid);

    /**
     * 批量下线
     * 
     * @param needOfflineIdList
     */
    public void batchOffline(List<String> needOfflineIdList);

    /**
     * 无效的UserOnline
     * 
     * @return
     */
    public PageList<UserOnline> findExpiredUserOnlineList(Date expiredDate);
}
