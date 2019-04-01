package com.leo.platform.upms.service.organization;

import java.util.Set;

import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.job.Job;

public interface JobService extends BaseService<Job, Long> {
    /**
     * 过滤仅获取可显示的数据
     * 
     * @param jobIds
     * @param organizationJobIds
     */
    public void filterForCanShow(Set<Long> jobIds, Set<Long[]> organizationJobIds);

    /**
     * 新增用于parentIds和树形结构输出的函数
     */
    public Set<Long> findAncestorIds(Iterable<Long> currentIds);

    public Set<Long> findAncestorIds(Long currentId);
}
