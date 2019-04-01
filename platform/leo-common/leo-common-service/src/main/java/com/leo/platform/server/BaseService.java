package com.leo.platform.server;

import java.io.Serializable;
import java.util.List;

import com.leo.platform.common.entity.BaseEntity;
import com.leo.platform.common.model.page.BasePageModel;
import com.leo.platform.common.model.page.PageList;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 泛型 ： T 表示实体类型；PK表示主键类型 抽象service层基类 提供一些简便方法
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2016年10月15日 下午10:33:44
 * 
 */
public interface BaseService<T extends BaseEntity<PK>, PK extends Serializable> {

    // //////////////////////////////////
    /** 基础服务 **/
    /**
     * 获取T的所有对象列表
     */
    List<T> getAll();

    /**
     * 获取T的所有对象列表, 可以根据so中的orderFields进行排序
     * 
     * @param so
     * @return
     */
    List<T> getAll(BasePageModel model);

    /**
     * 根据so进行搜索T的对象，返回可以翻页的对象列表
     * 
     * @param so
     * @return
     */
    PageList<T> getPageList(BasePageModel model);

    /**
     * 根据id获取对象
     */
    T get(PK id);

    /**
     * 新建对象
     * 
     * @param object
     * @return
     */
    public T create(T object);

    public List<T> createBatch(List<T> entityList);

    /**
     * 更新对象
     * 
     * @param object
     * @return
     */
    public T update(T object);

    public void updateBatch(List<T> entityList);

    /**
     * 删除某个对象
     */
    public abstract void remove(PK id);

    /**
     * 删除某个对象
     */
    public abstract void removeById(PK id);

    /**
     * 删除一系列对象
     * 
     * @param ids 要删除的对象id列表
     */
    public void remove(List<PK> ids);

    /**
     * 根据id列表获取对象列表
     * 
     * @param ids
     * @return
     */
    public abstract List<T> getByIds(List<PK> ids);

    /**
     * 根据某个字段的值获取对象
     * 
     * @param fieldName 字段名
     * @param value 字段的值
     * @return 成功返回该对象，否则返回null
     */
    public abstract T getBy(String fieldName, Object value);

    /**
     * 根据某个字段的值获取对象列表
     * 
     * @param fieldName 字段名
     * @param value 字段的值
     * @return 成功返回该对象列表，否则返回空的列表
     */
    public List<T> getListBy(String propertyName, Object value);

    /**
     * 根据so获取记录数
     * 
     * @param so
     * @return
     */
    public int getCountByModel(BasePageModel model);

    /**
     * 获取下一个 code 序列值
     * 
     * @return
     */
    public Long nextCodeSequence();

    // //////////////////////////////////
    /**
     * 保存单个实体
     * 
     * @param m 实体
     * @return 返回保存的实体
     */
    public T save(T m);

    /**
     * 根据主键删除相应实体
     * 
     * @param id 主键
     */
    public void delete(PK id);

    /**
     * 删除实体
     * 
     * @param m 实体
     */
    public void delete(T m);

    /**
     * 根据主键删除相应实体
     * 
     * @param ids 实体
     */
    public void delete(List<PK> ids);

    /**
     * 按照主键查询
     * 
     * @param id 主键
     * @return 返回id对应的实体
     */
    public T findOne(PK id);

    /**
     * 实体是否存在
     * 
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    public boolean exists(PK id);

    /**
     * 统计实体总数
     * 
     * @return 实体总数
     */
    public long count();

    /**
     * 查询所有实体
     * 
     * @return
     */
    public List<T> findAll();
}
