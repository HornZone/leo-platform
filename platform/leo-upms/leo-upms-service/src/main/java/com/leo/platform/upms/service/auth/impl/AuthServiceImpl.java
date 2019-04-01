package com.leo.platform.upms.service.auth.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.platform.server.BaseServiceImpl;
import com.leo.platform.upms.dao.AuthDao;
import com.leo.platform.upms.entity.auth.Auth;
import com.leo.platform.upms.entity.group.Group;
import com.leo.platform.upms.entity.user.User;
import com.leo.platform.upms.service.auth.AuthService;
import com.leo.platform.upms.service.group.GroupService;
import com.leo.platform.upms.service.user.UserService;

@Service
public class AuthServiceImpl extends BaseServiceImpl<Auth, Long> implements AuthService {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("groupServiceImpl")
    private GroupService groupService;

    @Autowired
    @Qualifier("authDaoImpl")
    private AuthDao authDao;

    /**
     * 为userids指定的用户授予权限
     * 
     * @param userIds
     * @param m
     */
    public void addUserAuth(Long[] userIds, Auth m) {

        if (ArrayUtils.isEmpty(userIds)) {
            return;
        }

        for (Long userId : userIds) {

            User user = userService.findOne(userId);
            if (user == null) {
                continue;
            }

            Auth auth = authDao.findByUserId(userId);
            if (auth != null) {
                auth.addRoleIds(m.getRoleIds());
                continue;
            }
            auth = new Auth();
            auth.setUserId(userId);
            auth.setType(m.getType());
            auth.setRoleIds(m.getRoleIds());
            save(auth);
        }
    }

    /**
     * 为groupIds指定的组授予权限
     * 
     * @param groupIds
     * @param m
     */
    public void addGroupAuth(Long[] groupIds, Auth m) {
        if (ArrayUtils.isEmpty(groupIds)) {
            return;
        }

        for (Long groupId : groupIds) {
            Group group = groupService.findOne(groupId);
            if (group == null) {
                continue;
            }

            Auth auth = authDao.findByGroupId(groupId);
            if (auth != null) {
                auth.addRoleIds(m.getRoleIds());
                continue;
            }
            auth = new Auth();
            auth.setGroupId(groupId);
            auth.setType(m.getType());
            auth.setRoleIds(m.getRoleIds());
            save(auth);
        }
    }

    /**
     * 为组织机构和职务授予权限
     * 
     * @param organizationIds
     * @param jobIds
     * @param m
     */
    public void addOrganizationJobAuth(Long[] organizationIds, Long[][] jobIds, Auth m) {

        if (ArrayUtils.isEmpty(organizationIds)) {
            return;
        }

        for (int i = 0, l = organizationIds.length; i < l; i++) {
            Long organizationId = organizationIds[i];

            /* 职务（Long[][] jobIds）的一行，表示该组织机构的职务，比如第一行职务是第一个组织机构下的职务，
             * 第二行职务是第二个组织机构下的职务
             * 如果当前组织机构下，没有设立职务，则直接为该组织机构授予权限 */
            if (jobIds[i].length == 0) {
                addOrganizationJobAuth(organizationId, null, m);
                continue;
            }

            /* 仅新增/修改一个 spring会自动split（“，”）--->给数组
             * 如果要授予权限的组织机构只有一个，则只遍历这个组织机构下，要授予权限的职务列表
             * 然后分别授予当前职务权限 */

            if (l == 1) {
                for (int j = 0, l2 = jobIds.length; j < l2; j++) {
                    Long jobId = jobIds[i][j];
                    addOrganizationJobAuth(organizationId, jobId, m);
                }
            } else {

                /* jobIds[i]表示第i行（组织机构）有多少列（职务） */
                for (int j = 0, l2 = jobIds[i].length; j < l2; j++) {
                    Long jobId = jobIds[i][j];
                    addOrganizationJobAuth(organizationId, jobId, m);
                }
            }

        }
    }

    /**
     * 为组织机构-职务授予权限 当组织机构下没有职务时，授权给组织机构
     * 
     * @param organizationId
     * @param jobId
     * @param m
     */
    private void addOrganizationJobAuth(Long organizationId, Long jobId, Auth m) {
        if (organizationId == null) {
            organizationId = 0L;
        }
        if (jobId == null) {
            jobId = 0L;
        }

        Auth auth = authDao.findByOrganizationIdAndJobId(organizationId, jobId);
        if (auth != null) {
            auth.addRoleIds(m.getRoleIds());
            return;
        }

        auth = new Auth();
        auth.setOrganizationId(organizationId);
        auth.setJobId(jobId);
        auth.setType(m.getType());
        auth.setRoleIds(m.getRoleIds());
        save(auth);

    }

    /**
     * <p>
     * 查找指定用户，在用户组group, 组织机构organization，职务job,指定组织机构和职务organizationjob 下的角色
     * </p>
     * <p>
     * 1.1、用户 根据用户绝对匹配
     * </p>
     * <p>
     * 1.2、组织机构 根据组织机构绝对匹配 此处需要注意 祖先需要自己获取
     * </p>
     * <p>
     * 1.3、工作职务 根据工作职务绝对匹配 此处需要注意 祖先需要自己获取
     * </p>
     * <p>
     * 1.4、组织机构和工作职务 根据组织机构和工作职务绝对匹配 此处不匹配祖先
     * <p>
     * 1.5、组 根据组绝对匹配
     * </p>
     * 
     * @param userId 必须有
     * @param groupIds 可选
     * @param organizationIds 可选
     * @param jobIds 可选
     * @param organizationJobIds 可选
     * @return
     */
    public List<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds,
        Set<Long[]> organizationJobIds) {
        return authDao.findRoleIds(userId, groupIds, organizationIds, jobIds, organizationJobIds);
    }

    @Override
    public void afterConstruct() {
        super.setBaseDao(authDao);
    }
}
