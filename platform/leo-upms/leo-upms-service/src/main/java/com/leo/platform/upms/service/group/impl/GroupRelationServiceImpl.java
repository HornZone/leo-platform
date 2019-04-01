package com.leo.platform.upms.service.group.impl;

import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.GroupRelationDao;
import com.leo.platform.upms.entity.grouprelation.GroupRelation;
import com.leo.platform.upms.service.group.GroupRelationService;

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
 * @date 2016年10月31日 下午9:49:34
 * 
 */
@Service
public class GroupRelationServiceImpl extends BaseServiceImpl<GroupRelation, Long> implements GroupRelationService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(GroupRelationServiceImpl.class);

    @Autowired
    @Qualifier("groupRelationDaoImpl")
    private GroupRelationDao groupRelationDao;

    public void appendRelation(Long groupId, Long[] organizationIds) {
        if (ArrayUtils.isEmpty(organizationIds)) {
            return;
        }
        for (Long organizationId : organizationIds) {
            if (organizationId == null) {
                continue;
            }
            GroupRelation r = groupRelationDao.findByGroupIdAndOrganizationId(groupId, organizationId);
            if (r == null) {
                r = new GroupRelation();
                r.setGroupId(groupId);
                r.setOrganizationId(organizationId);
                save(r);
            }
        }
    }

    /* 用户群组可以直接添加范围，比如群组1 从用户编号1-20 */
    public void appendRelation(Long groupId, Long[] userIds, Long[] startUserIds, Long[] endUserIds) {
        if (ArrayUtils.isEmpty(userIds) && ArrayUtils.isEmpty(startUserIds)) {
            return;
        }
        if (!ArrayUtils.isEmpty(userIds)) {
            for (Long userId : userIds) {
                if (userId == null) {
                    continue;
                }
                GroupRelation r = groupRelationDao.findByGroupIdAndUserId(groupId, userId);
                if (r == null) {
                    r = new GroupRelation();
                    r.setGroupId(groupId);
                    r.setUserId(userId);
                    save(r);
                }
            }
        }

        if (!ArrayUtils.isEmpty(startUserIds)) {
            for (int i = 0, l = startUserIds.length; i < l; i++) {
                Long startUserId = startUserIds[i];
                Long endUserId = endUserIds[i];
                // 范围查 如果在指定范围内 就没必要再新增一个 如当前是[10,20] 如果数据库有[9,21]
                GroupRelation r =
                    groupRelationDao.findByGroupIdAndStartUserIdLessThanEqualAndEndUserIdGreaterThanEqual(groupId,
                        startUserId, endUserId);

                if (r == null) {
                    // 删除范围内的
                    groupRelationDao.deleteInRange(startUserId, endUserId);
                    r = new GroupRelation();
                    r.setGroupId(groupId);
                    r.setStartUserId(startUserId);
                    r.setEndUserId(endUserId);
                    save(r);
                }

            }
        }
    }

    public Set<Long> findGroupIds(Long userId, Set<Long> organizationIds) {
        if (organizationIds.isEmpty()) {
            return Sets.newHashSet(groupRelationDao.findGroupIds(userId));
        }

        return Sets.newHashSet(groupRelationDao.findGroupIds(userId, organizationIds));
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(groupRelationDao);
    }
}
