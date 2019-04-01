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
import com.leo.platform.common.util.ListUtil;
import com.leo.platform.upms.dao.GroupRelationDao;
import com.leo.platform.upms.entity.grouprelation.GroupRelation;
import com.leo.platform.upms.model.grouprelation.GroupRelationModel;

@Repository
public class GroupRelationDaoImpl extends BaseDaoImpl<GroupRelation, Long> implements GroupRelationDao {
    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(GroupRelationDaoImpl.class);

    public GroupRelationDaoImpl() {
        super(GroupRelation.class);
    }

    @Override
    public GroupRelation findByGroupIdAndUserId(Long groupId, Long userId) {
        List<GroupRelation> list;
        try {
            GroupRelationModel groupRelationModel = new GroupRelationModel();
            groupRelationModel.setGroupId(groupId);
            groupRelationModel.setUserId(userId);
            groupRelationModel.setPageNumber(1);
            groupRelationModel.setObjectsPerPage(1);

            RowBounds rowBounds =
                new RowBounds((groupRelationModel.getPageNumber() - 1) * groupRelationModel.getObjectsPerPage(),
                    groupRelationModel.getObjectsPerPage());
            list =
                sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_MODEL, groupRelationModel,
                    rowBounds);
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
    public GroupRelation findByGroupIdAndStartUserIdLessThanEqualAndEndUserIdGreaterThanEqual(Long groupId,
        Long startUserId, Long endUserId) {
        List<GroupRelation> list;
        try {
            GroupRelationModel groupRelationModel = new GroupRelationModel();
            groupRelationModel.setGroupId(groupId);
            groupRelationModel.setStartUserId(startUserId);
            groupRelationModel.setEndUserId(endUserId);
            groupRelationModel.setPageNumber(1);
            groupRelationModel.setObjectsPerPage(1);

            RowBounds rowBounds =
                new RowBounds((groupRelationModel.getPageNumber() - 1) * groupRelationModel.getObjectsPerPage(),
                    groupRelationModel.getObjectsPerPage());
            list =
                sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_MODEL, groupRelationModel,
                    rowBounds);
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
    public void deleteInRange(Long startUserId, Long endUserId) {
        try {
            GroupRelationModel groupRelationModel = new GroupRelationModel();
            groupRelationModel.setDelStartUserId(startUserId);
            groupRelationModel.setDelEndUserId(endUserId);
            sqlSessionTemplate.delete(getStatementPrefix() + "deleteInRange", groupRelationModel);
        } catch (Exception e) {
            throw new DAOException("当前数据可能被其他记录引用，无法删除！", e);
        }

    }

    @Override
    public GroupRelation findByGroupIdAndOrganizationId(Long groupId, Long organizationId) {
        List<GroupRelation> list;
        try {
            GroupRelationModel groupRelationModel = new GroupRelationModel();
            groupRelationModel.setGroupId(groupId);
            groupRelationModel.setOrganizationId(organizationId);
            groupRelationModel.setPageNumber(1);
            groupRelationModel.setObjectsPerPage(1);

            RowBounds rowBounds =
                new RowBounds((groupRelationModel.getPageNumber() - 1) * groupRelationModel.getObjectsPerPage(),
                    groupRelationModel.getObjectsPerPage());
            list =
                sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_MODEL, groupRelationModel,
                    rowBounds);
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

    /*
     * ("select groupId from GroupRelation where userId=?1 or (startUserId<=?1 and endUserId>=?1))")
     */
    @Override
    public List<Long> findGroupIds(Long userId) {
        List<Long> list;
        try {
            GroupRelationModel groupRelationModel = new GroupRelationModel();
            groupRelationModel.setUserId(userId);

            RowBounds rowBounds =
                new RowBounds((groupRelationModel.getPageNumber() - 1) * groupRelationModel.getObjectsPerPage(),
                    groupRelationModel.getObjectsPerPage());
            list = sqlSessionTemplate.selectList(getStatementPrefix() + "findGroupIds", groupRelationModel, rowBounds);
            if (list.size() >= 1) {
                return list;
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public List<Long> findGroupIds(Long userId, Set<Long> organizationIds) {
        List<Long> list;
        try {
            List<Long> setToList = ListUtil.setToList(organizationIds);
            GroupRelationModel groupRelationModel = new GroupRelationModel();
            groupRelationModel.setUserId(userId);
            groupRelationModel.setOrganizationIds(setToList);

            RowBounds rowBounds =
                new RowBounds((groupRelationModel.getPageNumber() - 1) * groupRelationModel.getObjectsPerPage(),
                    groupRelationModel.getObjectsPerPage());
            list =
                sqlSessionTemplate.selectList(getStatementPrefix() + "findGroupIdsList", groupRelationModel, rowBounds);
            if (list.size() >= 1) {
                return list;
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void clearDeletedGroupRelation() {
        try {
            sqlSessionTemplate.delete(getStatementPrefix() + "clearDeletedGroupRelation");
        } catch (Exception e) {
            throw new DAOException("当前数据可能被其他记录引用，无法删除！", e);
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
    public int insert(GroupRelation entity) {
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
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.insert(entity);
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
    public int insertSelective(GroupRelation entity) {
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
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.insertSelective(entity);
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
    public int insertBatch(List<GroupRelation> entityList) {
        try {
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            Date now = new Date();
            for (GroupRelation entity : entityList) {
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
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.insertList(entityList);
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
    public int delete(GroupRelation record) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.delete(record);
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
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.deleteByPrimaryKey(id);
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
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.deleteByExample(example);
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
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.deleteByIds(ids);
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
    public int updateByPrimaryKey(GroupRelation object) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        GroupRelation oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
        int records = groupRelationDao.updateByPrimaryKey(object);
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
    public int updateByPrimaryKeySelective(GroupRelation object) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        GroupRelation oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
        int records = groupRelationDao.updateByPrimaryKeySelective(object);
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
    public int updateByExample(GroupRelation object, Object example) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        GroupRelation oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
        int records = groupRelationDao.updateByExample(object, example);
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
    public int updateByExampleSelective(GroupRelation object, Object example) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        GroupRelation oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
        int records = groupRelationDao.updateByExampleSelective(object, example);
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
    public List<GroupRelation> select(GroupRelation record) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.select(record);
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
    public GroupRelation selectByPrimaryKey(Long id) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectByPrimaryKey(id);
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
    public List<GroupRelation> selectAll(Long id) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectAll();
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
    public GroupRelation selectOne(GroupRelation record) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectOne(record);
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
    public int selectCount(GroupRelation record) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectCount(record);
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
    public List<GroupRelation> selectByExample(Object example) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectByExample(example);
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
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectCountByExample(example);
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
    public List<GroupRelation> selectByRowBounds(GroupRelation record, RowBounds rowBounds) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectByRowBounds(record, rowBounds);
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
    public List<GroupRelation> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectByExampleAndRowBounds(example, rowBounds);
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
    public List<GroupRelation> selectByIds(String ids) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectByIds(ids);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    // ///////////////////////////
    /** 单表查询方法，使用tk.mapper通用查询方法 **/

    /** 其他的tk.mapper通用方法实现 **/
    @Override
    public GroupRelation selectByPrimaryKey(Object key) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectByPrimaryKey(key);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public List<GroupRelation> selectAll() {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.selectAll();
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int insertList(List<GroupRelation> recordList) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.insertList(recordList);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int insertUseGeneratedKeys(GroupRelation record) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.insertUseGeneratedKeys(record);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        try {
            GroupRelationDao groupRelationDao = getSqlSession().getMapper(GroupRelationDao.class);
            return groupRelationDao.deleteByPrimaryKey(key);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }
}
