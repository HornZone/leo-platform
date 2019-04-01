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
import com.leo.platform.upms.dao.OrganizationDao;
import com.leo.platform.upms.entity.organization.Organization;
import com.leo.platform.upms.service.organization.OrganizationService;

@Service
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, Long> implements OrganizationService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Autowired
    @Qualifier("organizationDaoImpl")
    private OrganizationDao organizationDao;

    /**
     * 过滤仅获取可显示的数据
     * 
     * @param organizationIds
     * @param organizationJobIds
     */
    public void filterForCanShow(Set<Long> organizationIds, Set<Long[]> organizationJobIds) {

        Iterator<Long> iter1 = organizationIds.iterator();

        while (iter1.hasNext()) {
            Long id = iter1.next();
            Organization o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getIsShow())) {
                iter1.remove();
            }
        }

        Iterator<Long[]> iter2 = organizationJobIds.iterator();

        /* organizationId = iter2[0],jobId = iter2[1] */
        while (iter2.hasNext()) {
            Long id = iter2.next()[0];
            Organization o = findOne(id);
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
        Organization m = findOne(currentId);
        if (m == null) {
            return ids;
        }

        /* 用/拆分，拆分后加入到ids中，表示它有多少父组织机构 */
        for (String idStr : StringUtils.tokenizeToStringArray(m.getParentIds(), "/")) {
            if (!StringUtils.isEmpty(idStr)) {
                ids.add(Long.valueOf(idStr));
            }
        }
        return ids;
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(organizationDao);
    }

    // /////////////////////////////////// 树形结构输出函数，用于字段parentids,parentid字段的使用函数
}
