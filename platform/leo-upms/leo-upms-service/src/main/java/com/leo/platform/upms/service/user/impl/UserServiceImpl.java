package com.leo.platform.upms.service.user.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.UserDao;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.entity.user.UserStatus;
import com.leo.platform.upms.entity.userorganizationjob.UserOrganizationJob;
import com.leo.platform.upms.exception.UserBlockedException;
import com.leo.platform.upms.exception.UserNotExistsException;
import com.leo.platform.upms.exception.UserPasswordNotMatchException;
import com.leo.platform.upms.model.userorganizationjob.UserOrganizationJobModel;
import com.leo.platform.upms.service.user.PasswordService;
import com.leo.platform.upms.service.user.UserService;
import com.leo.platform.upms.service.user.UserStatusHistoryService;
import com.leo.platform.upms.util.UserLogUtils;

/**
 * 说明：对象的处理遵循这样的原则：比如User中有List<UserOrganizationJob> organizationJobs;这个成员是通过UserOrganizationJob表来建立的
 * 那么保存User的时候，需要考虑这个对象，对这个成员对象调用UserOrganizationJob的相关操作去保存他， User服务中的操作会拆开User对象然后对各个成员进行单独操作。
 */
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
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;

    @Autowired
    @Qualifier("userStatusHistoryServiceImpl")
    private UserStatusHistoryService userStatusHistoryService;

    @Autowired
    @Qualifier("passwordServiceImpl")
    private PasswordService passwordService;

    /**
     * <p>
     * Title: save
     * </p>
     * <p>
     * Description: 保存用户信息
     * </p>
     * 
     * @param user
     * @return
     */
    @Override
    public User save(User user) {
        if (user.getCreateDate() == null) {
            user.setCreateDate(new Date());
        }
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        // 处理List<UserOrganizationJob> userOrganizationJobs
        // 处理List<UserOrganizationJob> userOrganizationJobs
        return super.save(user);// save只保存序列化属性，对象属性没有保存
    }

    @Override
    /**
     * 
     * <p>
     * Title: update
     * </p>
     * <p>
     * Description: 更新用户信息
     * </p>
     * 
     * @param user
     * @return
     */
    public User update(User user) {

        List<UserOrganizationJob> localUserOrganizationJobs = user.getOrganizationJobs();
        for (int i = 0, l = localUserOrganizationJobs.size(); i < l; i++) {

            UserOrganizationJob localUserOrganizationJob = localUserOrganizationJobs.get(i);
            // 设置关系 防止丢失
            localUserOrganizationJob.setUser(user);

            UserOrganizationJob dbUserOrganizationJob = findUserOrganizationJob(localUserOrganizationJob);
            // 如果数据库表中没有用户组织关系对象，那么可能已经被删除
            // 那么更新这个用户的组织工作关系的时候，
            if (dbUserOrganizationJob != null) {// 出现先删除再添加的情况
                dbUserOrganizationJob.setJobId(localUserOrganizationJob.getJobId());
                dbUserOrganizationJob.setOrganizationId(localUserOrganizationJob.getOrganizationId());
                dbUserOrganizationJob.setUser(localUserOrganizationJob.getUser());
                localUserOrganizationJobs.set(i, dbUserOrganizationJob);
            }
        }
        return super.update(user);// 只保存序列化属性，对象属性没有保存
    }

    public UserOrganizationJob findUserOrganizationJob(UserOrganizationJob userOrganizationJob) {
        return userDao.findUserOrganization(userOrganizationJob.getUser(), userOrganizationJob.getOrganizationId(),
            userOrganizationJob.getJobId());
    }

    public User findByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        return userDao.findByUsername(username);
    }

    public User findByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return null;
        }
        return userDao.findByEmail(email);
    }

    public User findByMobilePhoneNumber(String mobilePhoneNumber) {
        if (StringUtils.isEmpty(mobilePhoneNumber)) {
            return null;
        }
        return userDao.findByMobilePhoneNumber(mobilePhoneNumber);
    }

    public User changePassword(User user, String newPassword) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), newPassword, user.getSalt()));
        update(user);
        return user;
    }

    public User changeStatus(User opUser, User user, UserStatus newStatus, String reason) {
        user.setStatus(newStatus.toString());
        update(user);
        userStatusHistoryService.log(opUser, user, newStatus, reason);
        return user;
    }

    public User login(String username, String password) {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            UserLogUtils.log(username, "loginError", "username is empty");
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 肯定错误
        if (password.length() < User.PASSWORD_MIN_LENGTH || password.length() > User.PASSWORD_MAX_LENGTH) {
            UserLogUtils.log(username, "loginError", "password length error! password is between {} and {}",
                User.PASSWORD_MIN_LENGTH, User.PASSWORD_MAX_LENGTH);

            throw new UserPasswordNotMatchException();
        }

        User user = null;

        // 此处需要走代理对象，目的是能走缓存切面
        UserService proxyUserService = (UserService)AopContext.currentProxy();
        if (maybeUsername(username)) {
            user = proxyUserService.findByUsername(username);
        }

        if (user == null && maybeEmail(username)) {
            user = proxyUserService.findByEmail(username);
        }

        if (user == null && maybeMobilePhoneNumber(username)) {
            user = proxyUserService.findByMobilePhoneNumber(username);
        }

        if (user == null || Boolean.TRUE.equals(user.getDeleted())) {
            UserLogUtils.log(username, "loginError", "user is not exists!");

            throw new UserNotExistsException();
        }

        passwordService.validate(user, password);

        if (user.getStatus().equals(UserStatus.blocked.toString())) {
            UserLogUtils.log(username, "loginError", "user is blocked!");
            throw new UserBlockedException(userStatusHistoryService.getLastReason(user));
        }

        UserLogUtils.log(username, "loginSuccess", "");
        return user;
    }

    private boolean maybeUsername(String username) {
        if (!username.matches(User.USERNAME_PATTERN)) {
            return false;
        }
        // 如果用户名不在指定范围内也是错误的
        if (username.length() < User.USERNAME_MIN_LENGTH || username.length() > User.USERNAME_MAX_LENGTH) {
            return false;
        }

        return true;
    }

    private boolean maybeEmail(String username) {
        if (!username.matches(User.EMAIL_PATTERN)) {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username) {
        if (!username.matches(User.MOBILE_PHONE_NUMBER_PATTERN)) {
            return false;
        }
        return true;
    }

    public void changePassword(User opUser, Long[] ids, String newPassword) {
        UserServiceImpl proxyUserService = (UserServiceImpl)AopContext.currentProxy();
        for (Long id : ids) {
            User user = findOne(id);
            proxyUserService.changePassword(user, newPassword);
            UserLogUtils.log(user.getUsername(), "changePassword", "admin user {} change password!",
                opUser.getUsername());

        }
    }

    public void changeStatus(User opUser, Long[] ids, UserStatus newStatus, String reason) {
        UserServiceImpl proxyUserService = (UserServiceImpl)AopContext.currentProxy();
        for (Long id : ids) {
            User user = findOne(id);
            proxyUserService.changeStatus(opUser, user, newStatus, reason);
            UserLogUtils.log(user.getUsername(), "changeStatus", "admin user {} change status!", opUser.getUsername());
        }
    }

    @Override
    public Set<Map<String, Object>> findIdAndNames(String username) {

        return Sets.newHashSet(Lists.transform(userDao.findIdAndNames(username),
            new Function<User, Map<String, Object>>() {
                @Override
                public Map<String, Object> apply(User input) {
                    Map<String, Object> data = Maps.newHashMap();
                    data.put("label", input.getUsername());
                    data.put("value", input.getId());
                    return data;
                }
            }));
    }

    /**
     * 获取那些在用户-组织机构/工作职务中存在 但在组织机构/工作职务中不存在的
     * 
     * @param pageable
     * @return
     */
    @Override
    public PageList<UserOrganizationJob> findUserOrganizationJobOnNotExistsOrganizationOrJob(
        UserOrganizationJobModel userOrganizationJobModel) {
        return userDao.findUserOrganizationJobOnNotExistsOrganizationOrJob(userOrganizationJobModel);
    }

    /**
     * 删除用户不存在的情况的UserOrganizationJob（比如手工从数据库物理删除）。。
     * 
     * @return
     */
    public void deleteUserOrganizationJobOnNotExistsUser() {
        userDao.deleteUserOrganizationJobOnNotExistsUser();
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(userDao);
    }
}
