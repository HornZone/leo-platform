package com.leo.platform.common.dao;

import java.io.Serializable;
import java.util.List;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.leo.platform.common.entity.BaseEntity;
import com.leo.platform.common.model.page.BasePageModel;
import com.leo.platform.common.model.page.PageList;

/**
 * BaseDao是在通用mapper上的一层封装， 目的： 1.规避难看的命名：比如selectByIds改成其他可以一看便知的命名方法 2.增加最底层的逻辑过滤，比如查询结果一定是status为1（状态为启用）的字段
 * 3.让方法参数更加明白，比如selectByIds接受String字符串拼接成的类似于1，2，3的id字符串，封装后可以接收list类型的id列表，更加直白。 4.通过对通用mapper的使用，实现一些没有的方法
 * Model设计认为是一个数据传输对象，后台数据流动单位。vo设计为前台转换对象，必须是序列化的，用于前台展示的对象（因为前台就必须展示数据，所以需要一个 这样的序列化对象）；
 * 
 * @author Administrator
 * 
 * @param <M>
 * @param <PK>
 */
public interface BaseDao<T extends BaseEntity<PK>, PK extends Serializable> extends Mapper<T>, MySqlMapper<T>,
    IdsMapper<T> {
    // /////////////////
    /** 增加方法开始 **/
    /**
     * 新建对象
     * 
     * @param object
     * @return
     */
    public T create(T object);

    /**
     * 批量创建对象
     * 
     * @param entityList
     * @return
     */
    public List<T> createBatch(List<T> entityList);

    /** 增加方法结束 **/
    // /////////////////////////////
    /** 删除方法开始 **/
    /**
     * 根据id删除某个对象
     */
    public abstract void removeById(PK id);

    /**
     * 删除一系列对象
     * 
     * @param ids 要删除的对象id列表
     */
    public void removeBatchByIds(List<PK> ids);

    /** 删除方法结束 **/
    // /////////////////////////////
    /** 修改方法开始 **/
    /**
     * 更新对象
     * 
     * @param object
     * @return
     */
    public T update(T object);

    /**
     * 批量更新对象
     * 
     * @param entityList
     */
    public void updateBatch(List<T> entityList);

    /** 修改方法结束 **/
    // ////////////////////////////
    /** 查询方法开始 **/
    /**
     * 获取T的所有对象列表
     */
    public abstract List<T> getAll();

    /**
     * 获取T的所有对象列表, 可以根据Model中的filedOrders进行排序
     * 
     * @param model
     * @return
     */
    public abstract List<T> getAll(BasePageModel model);

    /**
     * 根据model进行搜索T的对象，返回可以翻页的对象列表
     * 
     * @param model
     * @return
     */
    public abstract PageList<T> getPageList(BasePageModel model);

    /**
     * 根据id获取对象
     */
    public abstract T get(PK id);

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
    public List<T> getListBy(String fieldName, Object value);

    /**
     * 根据model查询记录数
     * 
     * @param model
     * @return
     */
    public int getPaginatedListCount(BasePageModel model);

    /**
     * 查看某个对象是否存在
     */
    public abstract boolean exists(PK id);

    /** 查询方法结束 **/
    // ///////////////////////////////////

    /**
     * 获取下一个 code 序列值
     * 
     * @return
     */
    public Long nextCodeSequence();
}
