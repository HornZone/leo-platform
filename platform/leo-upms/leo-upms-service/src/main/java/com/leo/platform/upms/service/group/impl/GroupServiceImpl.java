package com.leo.platform.upms.service.group.impl;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.GroupDao;
import com.leo.platform.upms.entity.group.Group;
import com.leo.platform.upms.service.group.GroupRelationService;
import com.leo.platform.upms.service.group.GroupService;

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
 * @date 2016年10月31日 下午9:48:21
 * 
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<Group, Long> implements GroupService {

    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    @Autowired
    @Qualifier("groupRelationServiceImpl")
    private GroupRelationService groupRelationService;

    @Autowired
    @Qualifier("groupDaoImpl")
    private GroupDao groupDao;

    public Set<Map<String, Object>> findIdAndNames(String groupName) {

        return Sets.newHashSet(Lists.transform(groupDao.findIdAndNames(groupName),
            new Function<Group, Map<String, Object>>() {
                @Override
                public Map<String, Object> apply(Group input) {
                    Map<String, Object> data = Maps.newHashMap();
                    data.put("label", input.getName());
                    data.put("value", input.getId());
                    return data;
                }
            }));
    }

    /**
     * 获取可用的的分组编号列表
     * 
     * @param userId
     * @param organizationIds
     * @return
     */
    public Set<Long> findShowGroupIds(Long userId, Set<Long> organizationIds) {
        Set<Long> groupIds = Sets.newHashSet();
        groupIds.addAll(groupDao.findDefaultGroupIds());
        groupIds.addAll(groupRelationService.findGroupIds(userId, organizationIds));

        // 如果分组数量很多 建议此处查询时直接带着是否可用的标识去查
        for (Group group : findAll()) {
            if (Boolean.FALSE.equals(group.getShow())) {
                groupIds.remove(group.getId());
            }
        }

        return groupIds;
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(groupDao);
    }
}
