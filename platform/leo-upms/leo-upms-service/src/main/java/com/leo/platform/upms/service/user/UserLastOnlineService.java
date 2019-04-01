package com.leo.platform.upms.service.user;

import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.userlastonline.UserLastOnline;

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
 * @date 2016年10月31日 上午9:03:41
 * 
 */
public interface UserLastOnlineService extends BaseService<UserLastOnline, Long> {

    public UserLastOnline findByUserId(Long userId);

    public void lastOnline(UserLastOnline lastOnline);
}
