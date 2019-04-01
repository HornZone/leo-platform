package com.leo.platform.upms.dao;

import java.util.List;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.upms.entity.useronline.UserOnline;
import com.leo.platform.upms.model.useronline.UserOnlineModel;

public interface UserOnlineDao extends BaseDao<UserOnline, String> {
    /* @Query("from UserOnline o where o.lastAccessTime < ?1 order by o.lastAccessTime asc") */
    PageList<UserOnline> findExpiredUserOnlineList(UserOnlineModel userOnlineModel);

    /*
     * @Modifying
     * 
     * @Query("delete from UserOnline o where o.id in (?1)")
     */
    void batchDelete(List<String> needExpiredIdList);
}
