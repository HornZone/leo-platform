package com.leo.platform.upms.service.user.task;

import java.util.Collection;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.common.model.page.PageList;
import com.leo.platform.common.util.LogUtils;
import com.leo.platform.upms.dao.UserOrganizationJobDao;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.entity.userorganizationjob.UserOrganizationJob;
import com.leo.platform.upms.model.userorganizationjob.UserOrganizationJobModel;
import com.leo.platform.upms.service.organization.JobService;
import com.leo.platform.upms.service.organization.OrganizationService;
import com.leo.platform.upms.service.user.UserService;

/**
 * 清理无关联的User-Organization/Job关系 1、User-Organization/Job
 * <p/>
 * <p>
 * User: Caven_Zhou
 * <p>
 * Date: 13-5-13 下午5:09
 * <p>
 * Version: 1.0
 */
@Service()
public class UserClearRelationTask {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("organizationServiceImpl")
    private OrganizationService organizationService;

    @Autowired
    @Qualifier("jobServiceImpl")
    private JobService jobService;

    @Autowired
    @Qualifier("userOrganizationJobDaoImpl")
    private UserOrganizationJobDao userOrganizationJobDao;

    /**
     * 清除删除的用户-组织机构/工作职务对应的关系
     */
    public void clearDeletedUserRelation() {

        // 删除用户不存在的情况的UserOrganizationJob（比如手工从数据库物理删除）。。
        userService.deleteUserOrganizationJobOnNotExistsUser();

        PageList<UserOrganizationJob> page = null;

        int pn = 0;
        do {
            UserOrganizationJobModel userOrganizationModel = new UserOrganizationJobModel();
            userOrganizationModel.setPageNumber(pn++);
            page = userService.findUserOrganizationJobOnNotExistsOrganizationOrJob(userOrganizationModel);

            // 开启新事物清除
            try {
                UserClearRelationTask userClearRelationTask = (UserClearRelationTask)AopContext.currentProxy();
                userClearRelationTask.doClear(page.getList());
            } catch (Exception e) {
                // 出异常也无所谓
                LogUtils.logError("clear user relation error", e);

            }
            // 清空会话
            userOrganizationJobDao.clear();

        } while (page.hasNextPage());

    }

    public void doClear(Collection<UserOrganizationJob> userOrganizationJobColl) {
        for (UserOrganizationJob userOrganizationJob : userOrganizationJobColl) {

            User user = userOrganizationJob.getUser();

            if (!organizationService.exists(userOrganizationJob.getOrganizationId())) {
                user.getOrganizationJobs().remove(userOrganizationJob);// 如果是组织机构删除了 直接移除
            } else if (!jobService.exists(userOrganizationJob.getJobId())) {
                user.getOrganizationJobs().remove(userOrganizationJob);
                userOrganizationJob.setJobId(null);
                user.getOrganizationJobs().add(userOrganizationJob);
            }
            // 不加也可 加上的目的是为了清缓存
            userService.update(user);
        }

    }
}
