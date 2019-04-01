package com.leo.platform.upms.service.auth;

import java.util.List;
import java.util.Set;

import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.auth.Auth;

/**
 * 用户权限service类
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
 * @date 2016年11月20日 上午11:23:10
 * 
 */
public interface AuthService extends BaseService<Auth, Long> {

    List<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds,
        Set<Long[]> organizationJobIds);

}
