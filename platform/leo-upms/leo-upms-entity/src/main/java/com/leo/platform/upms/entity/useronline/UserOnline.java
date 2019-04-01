package com.leo.platform.upms.entity.useronline;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.shiro.session.mgt.OnlineSession;

import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.common.util.SerializableUtils;

@Entity
@Table(name = "sys_user_online")
public class UserOnline extends BaseBizEntity<String> {
    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 164750314219254029L;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username")
    private String username;
    @Column(name = "host")
    private String host;
    @Column(name = "system_host")
    private String systemHost;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "status")
    private String status;
    @Column(name = "start_timestamp")
    private Date startTimestamp;
    @Column(name = "last_access_time")
    private Date lastAccessTime;
    @Column(name = "timeout")
    private Long timeout;
    @Column(name = "session")
    private String session;

    public UserOnline(Long userId, String username, String host, String systemHost, String userAgent, String status,
        Date startTimestamp, Date lastAccessTime, Long timeout) {
        super();
        this.userId = userId;
        this.username = username;
        this.host = host;
        this.systemHost = systemHost;
        this.userAgent = userAgent;
        this.status = status;
        this.startTimestamp = startTimestamp;
        this.lastAccessTime = lastAccessTime;
        this.timeout = timeout;
    }

    public UserOnline() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return this.session;
    }

    /*
     * public OnlineSession getSession() throws Exception { return (OnlineSession)
     * SerializableUtils.readObject(session); }
     */

    public void setSession(OnlineSession session) throws Exception {
        this.session = SerializableUtils.writeObject(session);
    }

    public OnlineSession getSessionObjectFromSeriable() throws Exception {
        return (OnlineSession)SerializableUtils.readObject(session);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    public static final UserOnline fromOnlineSession(OnlineSession session) throws Exception {
        UserOnline online = new UserOnline();
        online.setId(String.valueOf(session.getId()));
        online.setUserId(session.getUserId());
        online.setUsername(session.getUsername());
        online.setStartTimestamp(session.getStartTimestamp());
        online.setLastAccessTime(session.getLastAccessTime());
        online.setTimeout(session.getTimeout());
        online.setHost(session.getHost());
        online.setUserAgent(session.getUserAgent());
        online.setSystemHost(session.getSystemHost());
        online.setSession(session);

        return online;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserOnline other = (UserOnline)obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }
}
