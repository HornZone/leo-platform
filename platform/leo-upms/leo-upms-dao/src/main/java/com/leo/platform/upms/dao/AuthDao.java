package com.leo.platform.upms.dao;

import java.util.List;
import java.util.Set;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.upms.entity.auth.Auth;

/**
 * xxDao主要是实现了对应实体的Dao，具有业务针对性
 * 
 * @author Administrator
 * 
 */
public interface AuthDao extends BaseDao<Auth, Long> {
    void clear();

    Auth findByUserId(Long userId);

    Auth findByGroupId(Long groupId);

    Auth findByOrganizationIdAndJobId(Long organizationId, Long jobId);

    // ///////// 委托给AuthRepositoryImpl实现
    public List<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds,
        Set<Long[]> organizationJobIds);
}
