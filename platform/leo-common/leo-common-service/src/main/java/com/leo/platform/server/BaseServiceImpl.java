package com.leo.platform.server;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leo.platform.common.dao.BaseDao;
import com.leo.platform.common.entity.BaseEntity;
import com.leo.platform.common.model.page.BasePageModel;
import com.leo.platform.common.model.page.PageList;
import com.leo.platform.common.util.StringUtil;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:泛型 ：T 表示实体类型；PK表示主键类型 抽象service层基类 提供一些简便方法
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2016年10月15日 下午10:35:33
 * 
 */
public abstract class BaseServiceImpl<T extends BaseEntity<PK>, PK extends Serializable> implements BaseService<T, PK> {

    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    protected BaseDao<T, PK> baseDao;

    @PostConstruct
    abstract public void afterConstruct();

    public void setBaseDao(BaseDao<T, PK> baseDao) {
        this.baseDao = baseDao;
    }

    // /////////////////////////////////////////////////////
    /**** 调用自建的mapper中的sql方法 ***/
    @Override
    public List<T> getAll() {
        return baseDao.getAll();
    }

    @Override
    public List<T> getAll(BasePageModel model) {
        return baseDao.getAll(model);
    }

    @Override
    public PageList<T> getPageList(BasePageModel model) {
        return baseDao.getPageList(model);
    }

    @Override
    public T get(PK id) {
        return (T)baseDao.get(id);
    }

    @Override
    public T create(T object) {
        return (T)baseDao.create(object);
    }

    @Override
    public List<T> createBatch(List<T> entityList) {
        return baseDao.createBatch(entityList);
    }

    /**
     * 更新单个实体
     * 
     * @param t 实体
     * @return 返回更新的实体
     */
    public T update(T t) {
        return (T)baseDao.update(t);// 调用sql自建的mapper
    }

    @Override
    public void updateBatch(List<T> entityList) {
        baseDao.updateBatch(entityList);
    }

    @Override
    public void remove(PK id) {
        baseDao.removeById(id);
    }

    @Override
    public void removeById(PK id) {
        baseDao.removeById(id);
    }

    @Override
    public void remove(List<PK> ids) {
        baseDao.removeBatchByIds(ids);
    }

    @Override
    public List<T> getByIds(List<PK> ids) {
        return baseDao.getByIds(ids);
    }

    @Override
    public T getBy(String fieldName, Object value) {
        return (T)baseDao.getBy(fieldName, value);
    }

    @Override
    public List<T> getListBy(String propertyName, Object value) {
        return baseDao.getListBy(propertyName, value);
    }

    @Override
    public int getCountByModel(BasePageModel model) {
        return baseDao.getPaginatedListCount(model);
    }

    @Override
    public Long nextCodeSequence() {
        return baseDao.nextCodeSequence();
    }

    /**** 调用自建的mapper中的sql方法 结束 ***/
    // /////////////////////////////////////////////////////

    /**
     * 保存单个实体
     * 
     * @param t 实体
     * @return 返回保存的实体
     */
    public T save(T t) {
        return (T)baseDao.create(t);// 调用自建的mapper
    }

    /**
     * 根据主键删除相应实体
     * 
     * @param id 主键
     */
    public void delete(PK id) {
        baseDao.deleteByPrimaryKey(id);// tk.mapper中的方法
    }

    /**
     * 删除实体
     * 
     * @param m 实体
     */
    public void delete(T t) {
        baseDao.delete(t);// tk.mapper中的方法
    }

    /**
     * 就是public void delete(PK[] ids);
     * <p>
     * Title: delete
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param ids
     */
    /**
     * 根据主键删除相应实体
     * 
     * @param ids 实体
     */
    public void delete(List<PK> ids) {
        baseDao.deleteByIds(StringUtil.join(ids, ","));// tk.mapper中的方法
    }

    /**
     * 按照主键查询
     * 
     * @param id 主键
     * @return 返回id对应的实体
     */
    public T findOne(PK id) {
        return (T)baseDao.selectByPrimaryKey(id);// tk.mapper中的方法
    }

    /**
     * 实体是否存在
     * 
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    public boolean exists(PK id) {
        return baseDao.exists(id);// 自建的mapper
    }

    /**
     * 统计实体总数
     * 
     * @return 实体总数
     */
    public long count() {
        BasePageModel bpm = new BasePageModel();
        return baseDao.getPaginatedListCount(bpm);// 自建的mapper
    }

    /**
     * 查询所有实体
     * 
     * @return
     */
    public List<T> findAll() {
        return baseDao.getAll();// 自建的mapper
    }

    /**
     * 合并 按照顺序查询所有实体List<M> findAll(Sort sort) 按条件不分页不排序查询实体List<M> findAllWithNoPageNoSort(Searchable searchable)
     * 按条件排序查询实体(不分页)List<M> findAllWithSort(Searchable searchable) basePageModel有排序字段，只要填写正确，后台调用的时候就能添加排序模块 也有分页模块在里面
     * 
     * @Title: findAll @Description: TODO(这里用一句话描述这个方法的作用) @param @param basePageModel @param @return 设定文件 @return List
     *         <T> 返回类型 @throws
     */
    public List<T> findAll(BasePageModel basePageModel) {
        return baseDao.getAll(basePageModel);// 自建的mapper方法
    }

    /**
     * 合并 分页及排序查询实体 public Page<M> findAll(Pageable pageable) 按条件分页并排序查询实体,带查询字段Page <M> findAll(Searchable searchable)
     * 
     * @Title: findAllPageList @Description: TODO(这里用一句话描述这个方法的作用) @param @param basePageModel @param @return 设定文件 @return
     *         PageList <T> 返回类型 @throws
     */
    public PageList<T> findAllPageList(BasePageModel basePageModel) {
        return baseDao.getPageList(basePageModel);
    }

    /**
     * 按条件分页并排序统计实体数量
     * 
     * @param searchable 条件
     * @return
     */
    public Long count(BasePageModel basePageModel) {
        return (long)baseDao.getPaginatedListCount(basePageModel);
    }

    // /////////////////////////////////////////
    /** rawtypes是说传参时，不该传递基本的类型，导致参数过长，应该传递一个对象把这些参数包装起来 **/
    /*
     * @SuppressWarnings({"rawtypes"}) protected Class entityClass;
     * 
     * @SuppressWarnings({"rawtypes"}) protected Class entityVOClass;
     */

    /**
     * 请在这里给baseDao，modelClass和modelVOClass赋值 eg. UserServiceImpl.java:
     * 
     * @PostConstruct public void afterConstruct(){ super.setBaseDao(userDao); super.setModelClass(user.class);
     *                super.setModelVOClass(userVO.class); }
     */

    /**
     * 关于在spring 容器初始化 bean 和销毁前所做的操作定义方式有三种： 第一种：通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作 第二种是：通过
     * 在xml中定义init-method 和 destory-method方法 第三种是： 通过bean实现InitializingBean和 DisposableBean接口
     */
    /*
     * @PostConstruct abstract public void afterConstruct();
     */

    /*
     * @SuppressWarnings({"rawtypes"}) public Class getEntityClass() { return entityClass; }
     * 
     * @SuppressWarnings({"rawtypes"}) public void setEntityClass(Class entityClass) {
     * this.entityClass = entityClass; }
     * 
     * @SuppressWarnings({"rawtypes"}) public Class getEntityVOClass() { return entityVOClass; }
     * 
     * @SuppressWarnings({"rawtypes"}) public void setEntityVOClass(Class entityVOClass) {
     * this.entityVOClass = entityVOClass; }
     * 
     * @Autowired public void setOrikaBeanMapper(OrikaBeanMapper orikaMapper) { this.orikaBeanMapper =
     * orikaMapper; }
     */

}
