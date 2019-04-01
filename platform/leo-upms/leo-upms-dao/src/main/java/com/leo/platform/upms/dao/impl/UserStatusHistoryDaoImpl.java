package com.leo.platform.upms.dao.impl;

import java.util.Date;
import java.util.List;

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
import com.leo.platform.common.exception.QueryException;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.upms.dao.UserStatusHistoryDao;
import com.leo.platform.upms.entity.userstatushistory.UserStatusHistory;
import com.leo.platform.upms.model.userstatushistory.UserStatusHistoryModel;

@Repository
public class UserStatusHistoryDaoImpl extends BaseDaoImpl<UserStatusHistory, Long> implements UserStatusHistoryDao {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(UserStatusHistoryDaoImpl.class);

    public UserStatusHistoryDaoImpl() {
        super(UserStatusHistory.class);
    }

    @Override
    public PageList<UserStatusHistory> getPageList(UserStatusHistoryModel userStatusHistoryModel) {
        PageList<UserStatusHistory> result = new PageList<UserStatusHistory>(userStatusHistoryModel);
        List<UserStatusHistory> list;
        int fullListSize;

        try {
            RowBounds rowBounds =
                new RowBounds(
                    (userStatusHistoryModel.getPageNumber() - 1) * userStatusHistoryModel.getObjectsPerPage(),
                    userStatusHistoryModel.getObjectsPerPage());

            list =
                sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_MODEL, userStatusHistoryModel,
                    rowBounds);
            fullListSize = getPaginatedListCount(userStatusHistoryModel);

            result.setFullListSize(fullListSize);
            result.setList(list);

            return result;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new QueryException(e);
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
    public int insert(UserStatusHistory entity) {
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
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.insert(entity);
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
    public int insertSelective(UserStatusHistory entity) {
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
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.insertSelective(entity);
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
    public int insertBatch(List<UserStatusHistory> entityList) {
        try {
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            Date now = new Date();
            for (UserStatusHistory entity : entityList) {
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
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.insertList(entityList);
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
    public int delete(UserStatusHistory record) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.delete(record);
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
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.deleteByPrimaryKey(id);
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
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.deleteByExample(example);
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
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.deleteByIds(ids);
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
    public int updateByPrimaryKey(UserStatusHistory object) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        UserStatusHistory oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
        int records = userStatusHistoryDao.updateByPrimaryKey(object);
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
    public int updateByPrimaryKeySelective(UserStatusHistory object) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        UserStatusHistory oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
        int records = userStatusHistoryDao.updateByPrimaryKeySelective(object);
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
    public int updateByExample(UserStatusHistory object, Object example) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        UserStatusHistory oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
        int records = userStatusHistoryDao.updateByExample(object, example);
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
    public int updateByExampleSelective(UserStatusHistory object, Object example) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        UserStatusHistory oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
        int records = userStatusHistoryDao.updateByExampleSelective(object, example);
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
    public List<UserStatusHistory> select(UserStatusHistory record) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.select(record);
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
    public UserStatusHistory selectByPrimaryKey(Long id) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectByPrimaryKey(id);
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
    public List<UserStatusHistory> selectAll(Long id) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectAll();
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
    public UserStatusHistory selectOne(UserStatusHistory record) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectOne(record);
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
    public int selectCount(UserStatusHistory record) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectCount(record);
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
    public List<UserStatusHistory> selectByExample(Object example) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectByExample(example);
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
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectCountByExample(example);
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
    public List<UserStatusHistory> selectByRowBounds(UserStatusHistory record, RowBounds rowBounds) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectByRowBounds(record, rowBounds);
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
    public List<UserStatusHistory> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectByExampleAndRowBounds(example, rowBounds);
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
    public List<UserStatusHistory> selectByIds(String ids) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectByIds(ids);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    // ///////////////////////////
    /** 单表查询方法，使用tk.mapper通用查询方法 **/

    /** 其他的tk.mapper通用方法实现 **/
    @Override
    public UserStatusHistory selectByPrimaryKey(Object key) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectByPrimaryKey(key);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public List<UserStatusHistory> selectAll() {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.selectAll();
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int insertList(List<UserStatusHistory> recordList) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.insertList(recordList);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int insertUseGeneratedKeys(UserStatusHistory record) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.insertUseGeneratedKeys(record);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        try {
            UserStatusHistoryDao userStatusHistoryDao = getSqlSession().getMapper(UserStatusHistoryDao.class);
            return userStatusHistoryDao.deleteByPrimaryKey(key);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }
}
