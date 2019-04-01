package com.leo.platform.upms.dao;

import java.util.List;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.entity.userorganizationjob.UserOrganizationJob;
import com.leo.platform.upms.model.userorganizationjob.UserOrganizationJobModel;

public interface UserOrganizationJobDao extends BaseDao<UserOrganizationJob, Long> {
    /**
     * 根据用户对象，组织id，工作id，查找用户组织关系对象。 @Title: findUserOrganization @Description: TODO(这里用一句话描述这个方法的作用) @param @param user @param @param
     * organizationId @param @param jobId @param @return 设定文件 @return UserOrganizationJob 返回类型 @throws
     */
    UserOrganizationJob findUserOrganization(User user, Long organizationId, Long jobId);

    PageList<UserOrganizationJob> findUserOrganizationJobOnNotExistsOrganizationOrJob(
        UserOrganizationJobModel userOrganizationJobModel);

    void deleteUserOrganizationJobOnNotExistsUser();

    // //////////////////////////////////////////////////////////////
    /** 单表操作使用tk.mapper **/
    void delete(List<Long> ids);

    List<UserOrganizationJob> findAll();

    void clear();
}
