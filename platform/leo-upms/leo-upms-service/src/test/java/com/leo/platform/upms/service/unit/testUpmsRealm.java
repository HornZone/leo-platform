package com.leo.platform.upms.service.unit;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.service.user.UserService;

public class testUpmsRealm extends BaseTest {
    @Autowired
    private SecurityManager securityManager;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    protected String username = "__z__hang123";
    protected String email = "zhang@163.com";
    protected String mobilePhoneNumber = "15612345678";
    protected String password = "12345";

    @Test
    public void testHelloworld() {

        // SecurityUtils.setSecurityManager(securityManager);
        // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        Session session;
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "12345");

        try {
            // 4、登录，即身份验证
            subject.login(token);
            session = subject.getSession();
        } catch (AuthenticationException e) {
            // 5、身份验证失败
        }

        Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

        // 6、退出
        subject.logout();
    }

    @Test
    public void testInsertUserWithLogin() {

        /* SecurityUtils.setSecurityManager(securityManager);  */
        // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "12345");

        try {
            // 4、登录，即身份验证
            subject.login(token);
            // 用登录的用户插入用户
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setMobilePhoneNumber(mobilePhoneNumber);
            user.setPassword(password);

            userService.create(user);
        } catch (AuthenticationException e) {
            // 5、身份验证失败
        }

        Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

        // 6、退出
        subject.logout();
    }
}
