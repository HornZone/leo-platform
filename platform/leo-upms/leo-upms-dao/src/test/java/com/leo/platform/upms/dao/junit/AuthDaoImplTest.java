package com.leo.platform.upms.dao.junit;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.leo.platform.upms.dao.AuthDao;
import com.leo.platform.upms.entity.auth.Auth;

public class AuthDaoImplTest extends BaseTest {

    @Autowired
    @Qualifier("authDaoImpl")
    private AuthDao authDao;

    private static Long organization_id = 1L;
    private static Long job_id = 2L;
    private static Long group_id = 3L;
    private static Long user_id = 4L;
    private static String role_ids = "1,2,3,4";
    private static String type = "type";

    @Test
    public void testAuthDaoImpl() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindByUserId() {
        // 创建auth
        Auth auth = new Auth(organization_id, job_id, user_id, group_id, role_ids, type);
        /* 由于create函数basedao中会填充登陆用户的用户名，所以必须配置securityManager才行 */
        authDao.create(auth);
        // 使用groupid来查找
        Auth authreturn = authDao.findByUserId(user_id);
        System.out.println("查询出来的auth:organizationid:" + authreturn.getOrganizationId() + ",groupid"
            + authreturn.getGroupId() + ",userid:" + authreturn.getUserId());

    }

    @Test
    public void testFindByGroupId() {
        // 创建auth
        Auth auth = new Auth(organization_id, job_id, user_id, group_id, role_ids, type);
        /* 由于create函数basedao中会填充登陆用户的用户名，所以必须配置securityManager才行 */
        authDao.create(auth);
        // 使用groupid来查找
        Auth authreturn = authDao.findByGroupId(3L);
        System.out.println("查询出来的auth:" + authreturn.getOrganizationId() + "," + authreturn.getGroupId());
    }

    @Test
    public void testFindByOrganizationIdAndJobId() {
        // 创建auth
        Auth auth = new Auth(organization_id, job_id, user_id, group_id, role_ids, type);
        authDao.create(auth);
        // 使用groupid来查找
        Auth authreturn = authDao.findByOrganizationIdAndJobId(organization_id, job_id);
        System.out.println("查询出来的auth:organizationid:" + authreturn.getOrganizationId() + ",groupid"
            + authreturn.getGroupId() + ",userid:" + authreturn.getUserId());
    }

    @Test
    public void testFindRoleIds() {
        fail("Not yet implemented");
    }

    @Test
    public void testInsert() {
        fail("Not yet implemented");
    }

    @Test
    public void testInsertSelective() {
        fail("Not yet implemented");
    }

    @Test
    public void testInsertBatch() {
        fail("Not yet implemented");
    }

    @Test
    public void testDelete() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteById() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteByIds() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByPrimaryKey() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByExampleSelective() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelect() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByPrimaryKeyLong() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectAllLong() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectOne() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectCount() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectCountByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByRowBounds() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByExampleAndRowBounds() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByIds() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByPrimaryKeyObject() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectAll() {
        fail("Not yet implemented");
    }

    @Test
    public void testInsertList() {
        fail("Not yet implemented");
    }

    @Test
    public void testInsertUseGeneratedKeys() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteByPrimaryKey() {
        fail("Not yet implemented");
    }

    @Test
    public void testBaseDaoImpl() {
        fail("Not yet implemented");
    }

    @Test
    public void testNextCodeSequence() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetStatementPrefix() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetSqlSessionTemplate() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetSqlSession() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetBatchSqlSessionTemplate() {
        fail("Not yet implemented");
    }

    @Test
    public void testCreate() {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateBatch() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveById() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveBatchByIds() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateBatch() {
        fail("Not yet implemented");
    }

    @Test
    public void testGet() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAll() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAllBasePageModel() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPageList() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPaginatedListCount() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetByIds() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetBy() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetListBy() {
        fail("Not yet implemented");
    }

    @Test
    public void testExists() {
        fail("Not yet implemented");
    }

}
