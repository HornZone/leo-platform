package com.leo.platform.upms.service.auth;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.leo.platform.common.model.page.PageList;
import com.leo.platform.upms.entity.role.Role;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.entity.useronline.UserOnline;

/**
 ** 分组、组织机构、用户、新增、修改、删除时evict缓存
 * <p/>
 * 获取用户授权的角色及组装好的权限
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-5-1 下午2:38
 * <p>
 * Version: 1.0
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
 * @date 2016年11月22日 下午9:26:25
 * 
 */
public interface UserAuthService {

    Set<Role> findRoles(User user);

    Set<String> findStringPermissions(User user);

    public Set<String> findStringRoles(User user);

    PageList<UserOnline> findExpiredUserOnlineList(Date expiredDate);

    void batchOffline(List<String> needOfflineIdList);

}
