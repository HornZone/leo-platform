package com.leo.platform.upms.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.leo.platform.common.dao.impl.BaseDaoImpl;
import com.leo.platform.common.dao.impl.CodeGeneratorFinder;
import com.leo.platform.common.dao.util.CodeUtil;
import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.common.exception.DAOException;
import com.leo.platform.common.exception.OptLockException;
import com.leo.platform.upms.dao.AuthDao;
import com.leo.platform.upms.entity.auth.Auth;
import com.leo.platform.upms.model.auth.AuthModel;

/**
 * xxxDaoImp，实现对应实体的Dao，既继承了BaseDaoImpl实现了通用Dao，也继承了XXDao实现对应实体的Dao层操作 基本上getlistbymodel都能满足所有查询函数的要求
 * 
 * @author Administrator
 * 
 */
@Repository
public class AuthDaoImpl extends BaseDaoImpl<Auth, Long> implements AuthDao {

    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(AuthDaoImpl.class);

    public AuthDaoImpl() {
        super(Auth.class);
    }

    public void clear() {
        this.getSqlSessionTemplate().clearCache();
    }

    @Override
    public Auth findByUserId(Long userId) {
        List<Auth> list;
        try {
            AuthModel authModel = new AuthModel();
            authModel.setUserId(userId);
            authModel.setPageNumber(1);
            authModel.setObjectsPerPage(1);

            RowBounds rowBounds =
                new RowBounds((authModel.getPageNumber() - 1) * authModel.getObjectsPerPage(),
                    authModel.getObjectsPerPage());
            list =
                sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_MODEL, authModel, rowBounds);
            if (list.size() >= 1) {
                return list.get(0);
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Auth findByGroupId(Long groupId) {
        List<Auth> list;
        try {
            AuthModel authModel = new AuthModel();
            authModel.setGroupId(groupId);
            authModel.setPageNumber(1);
            authModel.setObjectsPerPage(1);

            RowBounds rowBounds =
                new RowBounds((authModel.getPageNumber() - 1) * authModel.getObjectsPerPage(),
                    authModel.getObjectsPerPage());
            list =
                sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_MODEL, authModel, rowBounds);
            if (list.size() >= 1) {
                return list.get(0);
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Auth findByOrganizationIdAndJobId(Long organizationId, Long jobId) {
        List<Auth> list;
        try {
            AuthModel authModel = new AuthModel();
            authModel.setOrganizationId(organizationId);
            authModel.setJobId(jobId);
            authModel.setPageNumber(1);
            authModel.setObjectsPerPage(1);

            RowBounds rowBounds =
                new RowBounds((authModel.getPageNumber() - 1) * authModel.getObjectsPerPage(),
                    authModel.getObjectsPerPage());
            list =
                sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_MODEL, authModel, rowBounds);
            if (list.size() >= 1) {
                return list.get(0);
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    /**
     * 该函数只用于查找指定用户，或者指定组织机构，指定职务，或者指定组织机构以及职务下的角色信息 并不是并，而是总
     * 组织机构，职务，组织机构及职务都是user用户通过sys_user_organization_job表中获取到的，所以都是与该user用户相关的权限
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
     * 
     * 注意：roleIds在sql层用了DISTINCT 来去重，相当于是一个set结构
     */
    @Override
    public List<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds,
        Set<Long[]> organizationJobIds) {

        List<Long> roleIds;
        try {

            boolean hasGroupIds = groupIds.size() > 0;
            boolean hasOrganizationIds = organizationIds.size() > 0;
            boolean hasJobIds = jobIds.size() > 0;
            boolean hasOrganizationJobIds = organizationJobIds.size() > 0;

            AuthModel authModel = new AuthModel();
            authModel.setUserId(userId);

            if (hasGroupIds) {
                authModel.setGroupIds(groupIds);
            }

            if (hasOrganizationIds) {
                authModel.setOrganizationIds(organizationIds);
            }

            if (hasJobIds) {
                authModel.setJobIds(jobIds);
            }

            if (hasOrganizationJobIds) {
                authModel.setOrganizationJobIds(organizationJobIds);
            }

            authModel.setPageNumber(1);
            authModel.setObjectsPerPage(1);

            RowBounds rowBounds =
                new RowBounds((authModel.getPageNumber() - 1) * authModel.getObjectsPerPage(),
                    authModel.getObjectsPerPage());
            roleIds = sqlSessionTemplate.selectList(getStatementPrefix() + "findRoleIds", authModel, rowBounds);

            return roleIds;

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    // //////////////////////////////////////////////////
    /** tk.mapper通用方法 **/
    /** 增加方法开始 **/
    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     * 
     * @param entity
     * @return
     */
    @Override
    public int insert(Auth entity) {
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (entity instanceof BaseBizEntity) {
            BaseBizEntity baseInfo = (BaseBizEntity)entity;
            String username = (String)SecurityUtils.getSubject().getPrincipal();

            baseInfo.setCreator(username);
            baseInfo.setLastUpdator(username);
            baseInfo.setCreatorName(username);
            baseInfo.setLastUpdatorName(username);
            /**
             * id是通过sqlmap中的sequence来获得的
             */
            if (CodeGeneratorFinder.allowCode(this.entityClass) && StringUtils.isEmpty(baseInfo.getCode())) {
                String code = CodeGeneratorFinder.get(this.entityClass).generate(entity, nextCodeSequence());
                baseInfo.setCode(code);
            }
            baseInfo.setCreateTime(new Date());
            baseInfo.setLastUpdateTime(baseInfo.getCreateTime());
        }
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.insert(entity);
            // T result = get((PK) entity.getId());
        } catch (Exception e) {
            throw new DAOException("数据创建失败，可能存在数据唯一性冲突", e);
        }
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * 
     * @param entity
     * @return
     */
    public int insertSelective(Auth entity) {
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (entity instanceof BaseBizEntity) {
            BaseBizEntity baseInfo = (BaseBizEntity)entity;

            String username = (String)SecurityUtils.getSubject().getPrincipal();

            baseInfo.setCreator(username);
            baseInfo.setLastUpdator(username);
            baseInfo.setCreatorName(username);
            baseInfo.setLastUpdatorName(username);

            /**
             * id是通过sqlmap中的sequence来获得的
             */
            if (CodeGeneratorFinder.allowCode(this.entityClass) && StringUtils.isEmpty(baseInfo.getCode())) {
                String code = CodeGeneratorFinder.get(this.entityClass).generate(entity, nextCodeSequence());
                baseInfo.setCode(code);
            }
            baseInfo.setCreateTime(new Date());
            baseInfo.setLastUpdateTime(baseInfo.getCreateTime());
        }
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.insertSelective(entity);
            // T result = get((PK) entity.getId());
        } catch (Exception e) {
            throw new DAOException("数据创建失败，可能存在数据唯一性冲突", e);
        }
    }

    /**
     * 特殊数据库支持 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     * 
     * @param entityList
     * @return
     */
    public int insertBatch(List<Auth> entityList) {
        try {
            String username = (String)SecurityUtils.getSubject().getPrincipal();

            Date now = new Date();
            for (Auth entity : entityList) {
                if (entity instanceof BaseBizEntity) {
                    BaseBizEntity baseInfo = (BaseBizEntity)entity;

                    baseInfo.setCreator(username);
                    baseInfo.setLastUpdator(username);

                    baseInfo.setCreatorName(username);
                    baseInfo.setLastUpdatorName(username);

                    if (CodeGeneratorFinder.allowCode(this.entityClass) && StringUtils.isEmpty(baseInfo.getCode())) {
                        String code = CodeUtil.generateCode(this.entityClass, nextCodeSequence());
                        baseInfo.setCode(code);
                    }
                    baseInfo.setCreateTime(now);
                    baseInfo.setLastUpdateTime(now);
                }
            }
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.insertList(entityList);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    // ///////////////////////////
    /** 删除方法开始 **/
    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     * 
     * @param record
     */
    public int delete(Auth record) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.delete(record);
        } catch (Exception e) {
            throw new DAOException("当前数据可能被其他记录引用，无法删除！", e);
        }
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new DAOException("当前数据可能被其他记录引用，无法删除！", e);
        }
    }

    /**
     * 根据Example条件删除数据
     * 
     * @param example
     * @return
     */
    public int deleteByExample(Object example) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.deleteByExample(example);
        } catch (Exception e) {
            throw new DAOException("当前数据可能被其他记录引用，无法删除！", e);
        }
    }

    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String ids) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.deleteByIds(ids);
        } catch (Exception e) {
            throw new DAOException("当前数据可能被其他记录引用，无法删除！", e);
        }
    }

    // ///////////////////////////
    /** 修改方法开始 **/
    /**
     * 根据主键更新实体全部字段，null值会被更新
     * 
     * @param object
     * @return
     */
    public int updateByPrimaryKey(Auth object) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        Auth oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();

            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
        int records = authDao.updateByPrimaryKey(object);
        if (records == 0 || records == -1) {
            throw new OptLockException("数据已被修改或删除！请刷新后重试！");
        }
        return records;
    }

    /**
     * 根据主键更新属性不为null的值
     * 
     * @param object
     * @return
     */
    public int updateByPrimaryKeySelective(Auth object) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        Auth oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
        int records = authDao.updateByPrimaryKeySelective(object);
        if (records == 0 || records == -1) {
            throw new OptLockException("数据已被修改或删除！请刷新后重试！");
        }
        return records;
    }

    /**
     * 根据Example条件更新实体record包含的全部属性，null值会被更新
     * 
     * @param object
     * @param example Example条件查询对象
     * @return
     */
    public int updateByExample(Auth object, Object example) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        Auth oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
        int records = authDao.updateByExample(object, example);
        if (records == 0 || records == -1) {
            throw new OptLockException("数据已被修改或删除！请刷新后重试！");
        }
        return records;
    }

    /**
     * 根据Example条件更新实体record包含的不是null的属性值
     * 
     * @param object
     * @param example
     * @return
     */
    public int updateByExampleSelective(Auth object, Object example) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        Auth oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
        int records = authDao.updateByExampleSelective(object, example);
        if (records == 0 || records == -1) {
            throw new OptLockException("数据已被修改或删除！请刷新后重试！");
        }
        return records;
    }

    // ///////////////////////////
    /** 查询方法开始 **/
    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     * 
     * @param record
     * @return
     */
    public List<Auth> select(Auth record) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.select(record);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     * 
     * @param id
     * @return
     */
    public Auth selectByPrimaryKey(Long id) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectByPrimaryKey(id);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    /**
     * 查询全部结果，select(null)方法能达到同样的效果
     * 
     * @param id
     * @return
     */
    public List<Auth> selectAll(Long id) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectAll();
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     * 
     * @param id
     * @return
     */
    public Auth selectOne(Auth record) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectOne(record);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     * 
     * @param record
     * @return
     */
    public int selectCount(Auth record) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectCount(record);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    /**
     * 根据Example条件进行查询 重点：这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列
     * 
     * @param example
     * @return
     */
    public List<Auth> selectByExample(Object example) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectByExample(example);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    /**
     * 根据Example条件进行查询总数
     * 
     * @param example
     * @return
     */
    public int selectCountByExample(Object example) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectCountByExample(example);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    /**
     * 根据实体属性和RowBounds进行分页查询
     * 
     * @param record
     * @param rowBounds
     * @return
     */
    public List<Auth> selectByRowBounds(Auth record, RowBounds rowBounds) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectByRowBounds(record, rowBounds);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    /**
     * 根据example条件和RowBounds进行分页查询
     * 
     * @param example
     * @param rowBounds
     * @return
     */
    public List<Auth> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectByExampleAndRowBounds(example, rowBounds);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     * 
     * @param ids
     * @return
     */
    public List<Auth> selectByIds(String ids) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectByIds(ids);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    // ///////////////////////////
    /** 单表查询方法，使用tk.mapper通用查询方法 **/

    /** 其他的tk.mapper通用方法实现 **/
    @Override
    public Auth selectByPrimaryKey(Object key) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectByPrimaryKey(key);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public List<Auth> selectAll() {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.selectAll();
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int insertList(List<Auth> recordList) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.insertList(recordList);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int insertUseGeneratedKeys(Auth record) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.insertUseGeneratedKeys(record);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        try {
            AuthDao authDao = getSqlSession().getMapper(AuthDao.class);
            return authDao.deleteByPrimaryKey(key);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }
}
