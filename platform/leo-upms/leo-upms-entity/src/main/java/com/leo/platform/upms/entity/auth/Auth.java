package com.leo.platform.upms.entity.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.entity.BaseBizEntity;

/**
 * 组织机构 工作职位 用户 角色 关系表
 * <p/>
 * 1、授权的五种情况
 * <p/>
 * 只给组织机构授权 (orgnizationId=? and jobId=0)
 * <p/>
 * 只给工作职务授权 (orgnizationId=0 and jobId=?)
 * <p/>
 * 给组织机构和工作职务都授权 (orgnizationId=? and jobId=?)
 * <p/>
 * 给用户授权 (userId=?)
 * <p/>
 * 给组授权 (groupId=?)
 * <p/>
 * 因此查询用户有没有权限 就是 where (orgnizationId=? and jobId=0) or (organizationId = 0 and jobId=?) or (orgnizationId=? and
 * jobId=?) or (userId=?) or (groupId=?)
 * <p/>
 * <p/>
 * 2、为了提高性能 放到一张表 此处不做关系映射（这样需要配合缓存）
 * <p/>
 * 3、如果另一方是可选的（如只选组织机构 或 只选工作职务） 那么默认0 使用0的目的是为了也让走索引
 * <p/>
 */
@Entity
@Table(name = "sys_auth")
public class Auth extends BaseBizEntity<Long> {
    @Column(name = "organization_id")
    private Long organizationId;
    @Column(name = "job_id")
    private Long jobId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "role_ids")
    private String roleIds;
    @Column(name = "type")
    private String type;

    public Auth(Long organizationId, Long jobId, Long userId, Long groupId, String roleIds, String type) {
        super();
        this.organizationId = organizationId;
        this.jobId = jobId;
        this.userId = userId;
        this.groupId = groupId;
        this.roleIds = roleIds;
        this.type = type;
    }

    public Auth() {};

    /**
     * 添加一个角色权限
     * 
     * @param roleId
     */
    public void addRoleId(String roleId) {
        StringBuilder sb = new StringBuilder(getRoleIds());
        sb.append(",");
        sb.append(roleId);
        setRoleIds(sb.toString());
    }

    /**
     * 添加一组角色权限
     * 
     * @param roleIds
     */
    public void addRoleIds(String roleIds) {

        StringBuilder sb = new StringBuilder(getRoleIds());
        sb.append(",");
        sb.append(roleIds);
        setRoleIds(sb.toString());
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
