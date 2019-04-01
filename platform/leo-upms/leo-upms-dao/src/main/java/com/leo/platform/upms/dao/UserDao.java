package com.leo.platform.upms.dao;

import java.util.List;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.entity.userorganizationjob.UserOrganizationJob;
import com.leo.platform.upms.model.userorganizationjob.UserOrganizationJobModel;

public interface UserDao extends BaseDao<User, Long> {

    // //////////////////////////////////////////////////////////////////
    /** 涉及到业务操作，即从es-master中的原版服务，使用自己构建的sql操作 **/
    User findByUsername(String username);

    User findByMobilePhoneNumber(String mobilePhoneNumber);

    User findByEmail(String email);

    /* @Query("from UserOrganizationJob where user=?1 and organizationId=?2 and jobId=?3") */
    UserOrganizationJob findUserOrganization(User user, Long organizationId, Long jobId);

    /*
     * @Query(
     * "select uoj from UserOrganizationJob uoj where not exists(select 1 from Job j where uoj.jobId=j.id) or not exists(select 1 from Organization o where uoj.organizationId=o.id)"
     * )
     */
    PageList<UserOrganizationJob> findUserOrganizationJobOnNotExistsOrganizationOrJob(
        UserOrganizationJobModel userOrganizationJobModel);

    /* @Modifying */
    /*
     * @Query(
     * "delete from UserOrganizationJob uoj where not exists(select 1 from User u where uoj.user=u)")
     */
    void deleteUserOrganizationJobOnNotExistsUser();

    List<User> findIdAndNames(String username);
}
