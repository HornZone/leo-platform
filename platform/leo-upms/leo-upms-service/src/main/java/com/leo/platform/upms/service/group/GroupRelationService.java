package com.leo.platform.upms.service.group;

import java.util.Set;

import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.grouprelation.GroupRelation;

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
 * @date 2016年11月1日 上午9:06:05
 * 
 */
public interface GroupRelationService extends BaseService<GroupRelation, Long> {

    public void appendRelation(Long groupId, Long[] organizationIds);

    public void appendRelation(Long groupId, Long[] userIds, Long[] startUserIds, Long[] endUserIds);

    public Set<Long> findGroupIds(Long userId, Set<Long> organizationIds);
}
