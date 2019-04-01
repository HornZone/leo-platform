package com.leo.platform.upms.service.group;

import java.util.Map;
import java.util.Set;

import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.group.Group;

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
 * @date 2016年11月1日 上午8:40:12
 * 
 */
public interface GroupService extends BaseService<Group, Long> {

    public Set<Map<String, Object>> findIdAndNames(String groupName);

    /**
     * 获取可用的的分组编号列表
     * 
     * @param userId
     * @param organizationIds
     * @return
     */
    public Set<Long> findShowGroupIds(Long userId, Set<Long> organizationIds);
}
