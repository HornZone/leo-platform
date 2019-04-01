package com.leo.platform.upms.service.group.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.upms.dao.GroupRelationDao;

/**
 * 清理无关联的关系 1、Group-GroupRelation 2、GroupRelation-User 3、GroupRelation-Organization 4、GroupRelation-Job
 * <p/>
 * <p>
 * User: Caven_Zhou
 * <p>
 * Date: 13-5-13 下午5:35
 * <p>
 * Version: 1.0
 */
@Service
public class GroupClearRelationTask {

    @Autowired
    @Qualifier("groupRelationDaoImpl")
    private GroupRelationDao groupRelationDao;

    /**
     * 清除删除的分组对应的关系
     */
    public void clearDeletedGroupRelation() {
        groupRelationDao.clearDeletedGroupRelation();
    }

}
