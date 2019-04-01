package com.leo.platform.upms.entity.userlastonline;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.upms.entity.useronline.UserOnline;

@Entity
@Table(name = "sys_user_last_online")
public class UserLastOnline extends BaseBizEntity<Long> {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username")
    private String username;
    @Column(name = "uid")
    private String uid;
    @Column(name = "host")
    private String host;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "system_host")
    private String systemHost;
    @Column(name = "last_login_timestamp")
    private Date lastLoginTimestamp;
    @Column(name = "last_stop_timestamp")
    private Date lastStopTimestamp;
    @Column(name = "login_count")
    private Long loginCount;
    @Column(name = "total_online_time")
    private Long totalOnlineTime;

    public UserLastOnline(Long userId, String username, String uid, String host, String userAgent, String systemHost,
        Date lastLoginTimestamp, Date lastStopTimestamp, Long loginCount, Long totalOnlineTime) {
        super();
        this.userId = userId;
        this.username = username;
        this.uid = uid;
        this.host = host;
        this.userAgent = userAgent;
        this.systemHost = systemHost;
        this.lastLoginTimestamp = lastLoginTimestamp;
        this.lastStopTimestamp = lastStopTimestamp;
        this.loginCount = loginCount;
        this.totalOnlineTime = totalOnlineTime;
    }

    public UserLastOnline() {}

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    public Date getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(Date lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    public Date getLastStopTimestamp() {
        return lastStopTimestamp;
    }

    public void setLastStopTimestamp(Date lastStopTimestamp) {
        this.lastStopTimestamp = lastStopTimestamp;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    public Long getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(Long totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    public void incLoginCount() {
        setLoginCount(getLoginCount() + 1);
    }

    public void incTotalOnlineTime() {
        long onlineTime = getLastStopTimestamp().getTime() - getLastLoginTimestamp().getTime();
        setTotalOnlineTime(getTotalOnlineTime() + onlineTime / 1000);
    }

    public static final UserLastOnline fromUserOnline(UserOnline online) {
        UserLastOnline lastOnline = new UserLastOnline();
        lastOnline.setHost(online.getHost());
        lastOnline.setUserId(online.getUserId());
        lastOnline.setUsername(online.getUsername());
        lastOnline.setUserAgent(online.getUserAgent());
        lastOnline.setSystemHost(online.getSystemHost());
        lastOnline.setUid(String.valueOf(online.getId()));
        lastOnline.setLastLoginTimestamp(online.getStartTimestamp());
        lastOnline.setLastStopTimestamp(online.getLastAccessTime());
        return lastOnline;
    }

    public static final void merge(UserLastOnline from, UserLastOnline to) {
        to.setHost(from.getHost());
        to.setUserId(from.getUserId());
        to.setUsername(from.getUsername());
        to.setUserAgent(from.getUserAgent());
        to.setSystemHost(from.getSystemHost());
        to.setUid(String.valueOf(from.getUid()));
        to.setLastLoginTimestamp(from.getLastLoginTimestamp());
        to.setLastStopTimestamp(from.getLastStopTimestamp());
    }
}
