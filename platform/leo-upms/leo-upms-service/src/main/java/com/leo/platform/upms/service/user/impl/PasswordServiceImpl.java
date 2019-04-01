package com.leo.platform.upms.service.user.impl;

import javax.annotation.PostConstruct;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.leo.platform.common.security.Md5Utils;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.exception.UserPasswordNotMatchException;
import com.leo.platform.upms.exception.UserPasswordRetryLimitExceedException;
import com.leo.platform.upms.service.user.PasswordService;
import com.leo.platform.upms.util.UserLogUtils;

@Service
public class PasswordServiceImpl implements PasswordService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(PasswordServiceImpl.class);
    @Autowired
    private CacheManager ehcacheManager;

    private Cache loginRecordCache;
    /**
     * 读取配置文件中的属性，使用PreferencesPlaceholderConfigurer配置
     */
    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount = 10;

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @PostConstruct
    public void init() {
        loginRecordCache = ehcacheManager.getCache("loginRecordCache");
    }

    /* 认证 */
    public void validate(User user, String password) {
        String username = user.getUsername();

        int retryCount = 0;

        /* 每次输入密码会先缓存下来，方便进行密码尝试次数的认证 */
        Element cacheElement = loginRecordCache.get(username);
        if (cacheElement != null) {
            retryCount = (Integer)cacheElement.getObjectValue();
            if (retryCount >= maxRetryCount) {

                /* 记录格式 [ip][用户名][操作][错误消息] */
                UserLogUtils.log(username, "passwordError",
                    "password error, retry limit exceed! password: {},max retry count {}", password, maxRetryCount);
                throw new UserPasswordRetryLimitExceedException(maxRetryCount);
            }
        }

        if (!matches(user, password)) {
            loginRecordCache.put(new Element(username, ++retryCount));
            UserLogUtils.log(username, "passwordError", "password error! password: {} retry count: {}", password,
                retryCount);
            throw new UserPasswordNotMatchException();
        } else {

            /* 匹配到了，就清除掉重试记录缓存 */
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(User user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getUsername(), newPassword, user.getSalt()));
    }

    public void clearLoginRecordCache(String username) {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }

    public static void main(String[] args) {
        System.out.println(new PasswordServiceImpl().encryptPassword("zhang", "12345", "yDd1956wn1"));
    }
}
