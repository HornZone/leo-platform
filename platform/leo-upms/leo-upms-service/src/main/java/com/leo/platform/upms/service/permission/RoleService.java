package com.leo.platform.upms.service.permission;

import java.util.List;
import java.util.Set;

import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.role.Role;

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
 * @date 2016年11月1日 上午9:37:01
 * 
 */
public interface RoleService extends BaseService<Role, Long> {

    public Role update(Role role);

    /**
     * 获取可用的角色列表
     * 
     * @param roleIds
     * @return
     */
    public Set<Role> findShowRoles(List<Long> roleIds);
}
