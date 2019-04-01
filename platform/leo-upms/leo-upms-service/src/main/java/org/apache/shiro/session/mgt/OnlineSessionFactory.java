package org.apache.shiro.session.mgt;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leo.platform.common.util.IpUtils;
import com.leo.platform.upms.entity.useronline.UserOnline;

/**
 * 创建自定义的session， 添加一些自定义的数据 如 用户登录到的系统ip 用户状态（在线 隐身 强制退出） 等 比如当前所在系统等
 * <p>
 * session工厂类，用于生产session
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
 * @date 2016年10月15日 下午11:34:51
 * 
 */
public class OnlineSessionFactory implements SessionFactory {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(OnlineSessionFactory.class);

    @Override
    public Session createSession(SessionContext initData) {
        OnlineSession session = new OnlineSession();
        if (initData != null && initData instanceof WebSessionContext) {
            WebSessionContext sessionContext = (WebSessionContext)initData;
            HttpServletRequest request = (HttpServletRequest)sessionContext.getServletRequest();
            if (request != null) {
                session.setHost(IpUtils.getIpAddr(request));
                session.setUserAgent(request.getHeader("User-Agent"));
                session.setSystemHost(request.getLocalAddr() + ":" + request.getLocalPort());
            }
        }
        return session;
    }

    public Session createSession(UserOnline userOnline) throws Exception {
        return userOnline.getSessionObjectFromSeriable();
    }
}
