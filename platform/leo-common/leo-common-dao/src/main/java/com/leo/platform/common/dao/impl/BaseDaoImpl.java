package com.leo.platform.common.dao.impl;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.common.dao.util.CodeUtil;
import com.leo.platform.common.dao.util.EntitySequenceMapping;
import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.common.entity.BaseEntity;
import com.leo.platform.common.exception.DAOException;
import com.leo.platform.common.exception.OptLockException;
import com.leo.platform.common.exception.QueryException;
import com.leo.platform.common.model.page.BasePageModel;
import com.leo.platform.common.model.page.PageList;

public abstract class BaseDaoImpl<T extends BaseEntity<PK>, PK extends Serializable> implements BaseDao<T, PK> {
    static {
        Resources.setCharset(Charset.forName("UTF-8"));
    }
    /**
     * 预定义的Statement
     */
    protected static final String POSTFIX_INSERT = "insert";

    protected static final String POSTFIX_UPDATE = "update";

    protected static final String POSTFIX_GET_BY_ID = "getById";

    protected static final String POSTFIX_GET_ALL = "getAll";

    protected static final String POSTFIX_GET_LIST_BY_MODEL = "getListByModel";

    protected static final String POSTFIX_GET_COUNT_BY_MODEL = "getCountByModel";

    protected static final String POSTFIX_GET_LIST_BY_IDS = "getListByIds";

    protected static final String POSTFIX_DELETE_BY_ID = "deleteById";

    protected static final String POSTFIX_DELETE_BY_IDS = "deleteByIds";
    protected static final String POSTFIX_GET_LIST_BY_FIELD = "getListByField";
    protected static final String POSTFIX_SPLIT = ".";

    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
    protected Class<T> entityClass = null;

    /**
     * Constructor that takes in a class to see which type of entity to persist
     * 
     * @param persistentClass the class type you'd like to persist
     */
    public BaseDaoImpl(final Class<T> persistentClass) {
        this.entityClass = persistentClass;
        // entityClass = (Class) this.getClass().getInterfaces()[0];
    }

    // 获取下一个ID
    @Override
    public Long nextCodeSequence() {
        Assert.notNull(this.entityClass);
        String sequenceName = EntitySequenceMapping.getSequence(this.entityClass);
        Map<String, String> map = new HashMap<String, String>();
        map.put("sequenceName", sequenceName);
        Long res = (Long)sqlSessionTemplate.selectOne("sqlmap.common.getCodeSequence", map);

        return res.longValue();
    }

    protected String getStatementPrefix() {
        return entityClass.getSimpleName() + POSTFIX_SPLIT;
    }

    // /////////////////////////////////////////////////////////////////////////
    private ThreadLocal<SqlSession> tl = new ThreadLocal<SqlSession>();

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

    protected SqlSession getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    protected SqlSession getSqlSession() {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        final SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        return sqlSession;
    }

    /**
     * SqlSessionDaoSupport中有对SqlSessionTemplate的自动注入, 故只能有一个SqlSessionTemplate在XML中配置
     */
    protected SqlSession getBatchSqlSessionTemplate() {
        if (tl.get() == null) {
            tl.set(this.getSqlSession());
            /**
             * 注册至Spring事务,由TransactionSynchronizationUtils#invokeAfterCompletion
             * 回调TransactionSynchronization#afterCompletion方法,去关闭SqlSession,并 从ThreadLocal中清除,用法类似@see
             * SqlSessionUtils#getSqlSession 155行
             */
            TransactionSynchronizationManager.registerSynchronization(new SqlSessionSynchronization());
        }
        return tl.get();
    }

    private final class SqlSessionSynchronization extends TransactionSynchronizationAdapter {
        @Override
        public void afterCompletion(int status) {
            if (tl.get() != null) {
                logger.debug("Transaction synchronization closing SqlSession [" + tl.get()
                    + "],with ExecutorType.BATCH");
                try {
                    tl.get().close();
                } finally {
                    tl.set(null);
                }
            }
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    /** 增加方法开始 **/

    @Override
    public T create(T entity) {
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (entity instanceof BaseBizEntity) {
            BaseBizEntity baseInfo = (BaseBizEntity)entity;

            String username = (String)SecurityUtils.getSubject().getPrincipal();
            /* String nickname = onlineSession.getNickname(); 别名暂时不填写，因为最重要的是记录认证凭据 */
            baseInfo.setCreator(username);
            baseInfo.setLastUpdator(username);
            baseInfo.setCreatorName(username);// nickname
            baseInfo.setLastUpdatorName(username);// nickname

            if (CodeGeneratorFinder.allowCode(this.entityClass) && StringUtils.isEmpty(baseInfo.getCode())) {
                String code = CodeGeneratorFinder.get(this.entityClass).generate(entity, nextCodeSequence());
                baseInfo.setCode(code);
            }
            baseInfo.setCreateTime(new Date());
            baseInfo.setLastUpdateTime(baseInfo.getCreateTime());
        }
        try {
            sqlSessionTemplate.insert(getStatementPrefix() + POSTFIX_INSERT, entity);
            // T result = get((PK) entity.getId());
            return entity;
        } catch (Exception e) {
            throw new DAOException("数据创建失败，可能存在数据唯一性冲突", e);
        }
    }

    @Override
    public List<T> createBatch(List<T> entityList) {
        try {

            Date now = new Date();
            for (T entity : entityList) {
                if (entity instanceof BaseBizEntity) {
                    BaseBizEntity baseInfo = (BaseBizEntity)entity;

                    String username = (String)SecurityUtils.getSubject().getPrincipal();
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
                sqlSessionTemplate.insert(getStatementPrefix() + POSTFIX_INSERT, entity);
            }
            this.getBatchSqlSessionTemplate().flushStatements();
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return entityList;
    }

    /** 增加方法结束 **/
    // ///////////////////////////////////////////////////////////////////////////
    /** 删除方法开始 **/
    @Override
    public void removeById(PK id) {
        try {
            sqlSessionTemplate.delete(getStatementPrefix() + POSTFIX_DELETE_BY_ID, id);
        } catch (Exception e) {
            throw new DAOException("当前数据可能被其他记录引用，无法删除！", e);
        }
    }

    @Override
    public void removeBatchByIds(List<PK> ids) {
        try {
            for (PK id : ids) {
                T object = get(id);
                this.getBatchSqlSessionTemplate().delete(getStatementPrefix() + POSTFIX_DELETE_BY_ID, id);
            }
            this.getBatchSqlSessionTemplate().flushStatements();
        } catch (Exception e) {
            throw new DAOException("当前数据可能被其他记录引用，无法删除！", e);
        }
    }

    /** 删除方法结束 **/
    // /////////////////////////////////////////////////////////////////////////////
    /** 修改方法开始 **/
    @Override
    public T update(T object) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        // 老数据不用记录了T oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            // 重新去取一遍数据库，获得最新值
            // oldObject = get((PK) baseBizEntity.getId());
        }
        int records = sqlSessionTemplate.update(getStatementPrefix() + POSTFIX_UPDATE, object);
        if (records == 0 || records == -1) {// 如果一条数据都没更新，说明更新没成功
            throw new OptLockException("数据已被修改或删除！请刷新后重试！");
        }
        // 返回更新后的结果；
        T result = get((PK)object.getId());
        return result;
    }

    @Override
    public void updateBatch(List<T> entityList) {
        try {

            for (T entity : entityList) {
                if (entity instanceof BaseBizEntity) {
                    BaseBizEntity baseBizEntity = (BaseBizEntity)entity;
                    String username = (String)SecurityUtils.getSubject().getPrincipal();
                    Date now = new Date();
                    baseBizEntity.setLastUpdatorName(username);
                    baseBizEntity.setLastUpdator(username);
                    baseBizEntity.setLastUpdateTime(now);

                }
                /* int records = */sqlSessionTemplate.update(getStatementPrefix() + POSTFIX_UPDATE, entity);
                /*
                 * if (records == 0 || records == -1) { throw new OptLockException("数据已被修改或删除！请刷新后重试！"); }
                 */
            }
            this.getBatchSqlSessionTemplate().flushStatements();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /** 修改方法结束 **/
    // ////////////////////////////////////////////////////////////////////////////
    /** 查询方法开始 **/
    @Override
    public T get(PK id) {
        try {
            T entity = (T)sqlSessionTemplate.selectOne(getStatementPrefix() + POSTFIX_GET_BY_ID, id);

            return entity;
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public List<T> getAll() {
        Long startTime = System.currentTimeMillis();
        try {
            List<T> retList = sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_ALL);
            return retList;
        } catch (Exception e) {
            throw new QueryException("数据查询失败", e);
        } finally {
            logger.debug("Record loading took " + (System.currentTimeMillis() - startTime));
        }
    }

    @Override
    public List<T> getAll(BasePageModel model) {
        Long startTime = System.currentTimeMillis();
        try {
            List<T> retList = sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_MODEL, model);
            return retList;
        } catch (Exception e) {
            throw new QueryException("数据查询失败", e);
        } finally {
            logger.debug("Record loading took " + (System.currentTimeMillis() - startTime));
        }
    }

    @Override
    public PageList<T> getPageList(BasePageModel model) {
        PageList<T> result = new PageList<T>(model);
        List<T> list;
        Long startTime = System.currentTimeMillis();
        try {
            RowBounds rowBounds =
                new RowBounds((model.getPageNumber() - 1) * model.getObjectsPerPage(), model.getObjectsPerPage());

            list = sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_MODEL, model, rowBounds);
            int count = getPaginatedListCount(model);

            result.setFullListSize(count);
            result.setList(list);

            return result;
        } catch (Exception e) {
            throw new QueryException("数据查询失败", e);
        } finally {
            logger.debug("One page record loading took " + (System.currentTimeMillis() - startTime));
        }
    }

    @Override
    public int getPaginatedListCount(BasePageModel model) {
        Long startTime = System.currentTimeMillis();
        try {
            Long count = (Long)sqlSessionTemplate.selectOne(getStatementPrefix() + POSTFIX_GET_COUNT_BY_MODEL, model);
            return count.intValue();
        } catch (Exception e) {
            throw new QueryException("数据查询失败", e);
        } finally {
            logger.debug("Total record count loading took " + (System.currentTimeMillis() - startTime));
        }

    }

    @Override
    public List<T> getByIds(List<PK> ids) {
        Long startTime = System.currentTimeMillis();
        try {
            List<T> retList = sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_IDS, ids);
            return retList;
        } catch (Exception e) {
            throw new QueryException("数据查询失败", e);
        } finally {
            logger.debug("Record loading took " + (System.currentTimeMillis() - startTime));
        }
    }

    @Override
    public T getBy(String fieldName, Object value) {
        Long startTime = System.currentTimeMillis();
        try {
            List<T> retList = getListBy(fieldName, value);
            T result = null;
            if (retList != null && retList.size() != 0) {
                result = retList.get(0);
            }
            return result;
        } catch (Exception e) {
            throw new QueryException("数据查询失败", e);
        } finally {
            logger.debug("Record loading took " + (System.currentTimeMillis() - startTime));
        }

    }

    @Override
    public List<T> getListBy(String fieldName, Object value) {
        Long startTime = System.currentTimeMillis();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("fieldName", fieldName);
            map.put("colValue", value);
            List<T> retList = sqlSessionTemplate.selectList(getStatementPrefix() + POSTFIX_GET_LIST_BY_FIELD, map);
            return retList;
        } catch (Exception e) {
            throw new QueryException(e);
        } finally {
            logger.debug("Record loading took " + (System.currentTimeMillis() - startTime));
        }
    }

    @Override
    public boolean exists(PK id) {
        T entity = get(id);
        return entity != null;
    }
    /** 查询方法结束 **/
    // //////////////////////////////

}
