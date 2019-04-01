package com.leo.platform.upms.dao;

import java.util.List;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.upms.entity.group.Group;

public interface GroupDao extends BaseDao<Group, Long> {
    /* @Query("select id from Group where defaultGroup=true and show=true") */
    List<Long> findDefaultGroupIds();

    List<Group> findIdAndNames(String groupName);
}
