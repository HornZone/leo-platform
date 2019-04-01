package org.apache.shiro.session.mgt.sessionListener;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class OnlineSessionListener implements SessionListener {

    /**
     * <p>
     * session监听器类，监听session的创建和删除等内容
     * </p>
     * 会话创建 是回调时，会调用这个方法，即会话已经启动（创建）调用了创建方法后了，所以此时获取nikename没有意义，建议放在sessionFactory的创建session方法中 以下所有的方法都是这样子的
     * <p>
     * Title: onStart
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param session
     * @see org.apache.shiro.session.SessionListener#onStart(org.apache.shiro.session.Session)
     */
    @Override
    public void onStart(Session session) {
        // 需要将登录用户的详细信息主要是nickname，和username保存到session用来给，保存数据用,
        // 也可以使用sessionDAO进行session数据保存到数据库或者缓存中
        String username = (String)SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 退出会话
     * <p>
     * Title: onStop
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param session
     * @see org.apache.shiro.session.SessionListener#onStop(org.apache.shiro.session.Session)
     */
    @Override
    public void onStop(Session session) {
        // TODO Auto-generated method stub

    }

    /**
     * 会话过期
     * <p>
     * Title: onExpiration
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param session
     * @see org.apache.shiro.session.SessionListener#onExpiration(org.apache.shiro.session.Session)
     */
    @Override
    public void onExpiration(Session session) {
        // TODO Auto-generated method stub

    }

}
