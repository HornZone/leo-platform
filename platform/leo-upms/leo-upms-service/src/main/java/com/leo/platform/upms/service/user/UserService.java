package com.leo.platform.upms.service.user;

import java.util.Map;
import java.util.Set;

import com.leo.platform.common.model.page.PageList;
import com.leo.platform.server.BaseService;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.entity.user.UserStatus;
import com.leo.platform.upms.entity.userorganizationjob.UserOrganizationJob;
import com.leo.platform.upms.model.userorganizationjob.UserOrganizationJobModel;

/**
 * 
 * <p>
 * Title: 用户验证服务类
 * </p>
 * <p>
 * Description: 用于用户验证授权服务
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2016年10月14日 下午8:47:57
 * 
 */
public interface UserService extends BaseService<User, Long> {

    public User save(User user);

    public User update(User user);

    public UserOrganizationJob findUserOrganizationJob(UserOrganizationJob userOrganizationJob);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findByMobilePhoneNumber(String mobilePhoneNumber);

    public User changePassword(User user, String newPassword);

    public User changeStatus(User opUser, User user, UserStatus newStatus, String reason);

    public User login(String username, String password);

    public void changePassword(User opUser, Long[] ids, String newPassword);

    public void changeStatus(User opUser, Long[] ids, UserStatus newStatus, String reason);

    public Set<Map<String, Object>> findIdAndNames(String username);

    /**
     * 获取那些在用户-组织机构/工作职务中存在 但在组织机构/工作职务中不存在的
     * 
     * @param pageable
     * @return
     */
    PageList<UserOrganizationJob> findUserOrganizationJobOnNotExistsOrganizationOrJob(
        UserOrganizationJobModel userOrganizationJobModel);

    /**
     * 删除用户不存在的情况的UserOrganizationJob（比如手工从数据库物理删除）。。
     * 
     * @return
     */
    public void deleteUserOrganizationJobOnNotExistsUser();
}
