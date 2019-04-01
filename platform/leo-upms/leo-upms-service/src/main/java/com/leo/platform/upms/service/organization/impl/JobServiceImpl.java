package com.leo.platform.upms.service.organization.impl;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Sets;
import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.JobDao;
import com.leo.platform.upms.entity.job.Job;
import com.leo.platform.upms.service.organization.JobService;

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
 * @date 2016年11月1日 上午9:24:58
 * 
 */
@Service
public class JobServiceImpl extends BaseServiceImpl<Job, Long> implements JobService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    @Qualifier("jobDaoImpl")
    private JobDao jobDao;

    /**
     * 过滤仅获取可显示的数据
     * 
     * @param jobIds
     * @param organizationJobIds
     */
    public void filterForCanShow(Set<Long> jobIds, Set<Long[]> organizationJobIds) {

        Iterator<Long> iter1 = jobIds.iterator();

        while (iter1.hasNext()) {
            Long id = iter1.next();
            Job o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getIsShow())) {
                iter1.remove();
            }
        }

        Iterator<Long[]> iter2 = organizationJobIds.iterator();

        /* organizationId = iter2[0],jobId = iter2[1] */
        while (iter2.hasNext()) {
            Long id = iter2.next()[1];
            Job o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getIsShow())) {
                iter2.remove();
            }
        }

    }

    // ////////////////////////// 树形结构输出函数，用于字段parentids,parentid字段的使用函数
    @Override
    public Set<Long> findAncestorIds(Iterable<Long> currentIds) {
        Set<Long> parents = Sets.newHashSet();
        for (Long currentId : currentIds) {
            parents.addAll(findAncestorIds(currentId));
        }
        return parents;
    }

    @Override
    public Set<Long> findAncestorIds(Long currentId) {
        Set ids = Sets.newHashSet();
        Job m = findOne(currentId);
        if (m == null) {
            return ids;
        }
        /* 根据/拆分parentids，放到ids中，表示有多少父职务 */
        for (String idStr : StringUtils.tokenizeToStringArray(m.getParentIds(), "/")) {
            if (!StringUtils.isEmpty(idStr)) {
                ids.add(Long.valueOf(idStr));
            }
        }
        return ids;
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(jobDao);
    }

    // /////////////////////////////////// 树形结构输出函数，用于字段parentids,parentid字段的使用函数
}
