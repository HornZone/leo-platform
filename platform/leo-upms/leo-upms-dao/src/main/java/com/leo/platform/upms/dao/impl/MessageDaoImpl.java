package com.leo.platform.upms.dao.impl;

import java.util.ArrayList;
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
import com.leo.platform.common.util.StringUtil;
import com.leo.platform.upms.dao.MessageDao;
import com.leo.platform.upms.entity.message.Message;
import com.leo.platform.upms.enums.MessageState;
import com.leo.platform.upms.model.message.MessageModel;

/**
 * xxxDaoImp，实现对应实体的Dao，既继承了BaseDaoImpl实现了通用Dao，也继承了XXDao实现对应实体的Dao层操作 基本上getlistbymodel都能满足所有查询函数的要求
 * 
 * @author Administrator
 * 
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message, Long> implements MessageDao {

    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(MessageDaoImpl.class);

    public MessageDaoImpl() {
        super(Message.class);
    }

    public void clear() {
        this.getSqlSessionTemplate().clearCache();
    }

    // ////////////////////////////////////// 自定义Dao使用自定义sql语句去实现/////////////////////////
    @Override
    public int changeSenderState(Long senderId, MessageState oldState, MessageState newState, Date changeDate) {
        try {
            MessageModel messageModel = new MessageModel();
            messageModel.setOldSenderState(oldState.toString());
            messageModel.setOldSenderId(senderId);
            messageModel.setSenderState(newState.toString());
            messageModel.setSenderStateChangeDate(changeDate);

            return sqlSessionTemplate.update(getStatementPrefix() + "changeSenderState", messageModel);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public int changeReceiverState(Long receiverId, MessageState oldState, MessageState newState, Date changeDate) {
        try {
            MessageModel messageModel = new MessageModel();
            messageModel.setOldReceiverState(oldState.toString());
            messageModel.setOldReceiverId(receiverId);
            messageModel.setSenderState(newState.toString());
            messageModel.setSenderStateChangeDate(changeDate);

            return sqlSessionTemplate.update(getStatementPrefix() + "changeReceiverState", messageModel);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public int changeSenderState(ArrayList<MessageState> states, MessageState oldStates, Date changeDate,
        Date expireDate) {
        try {
            MessageModel messageModel = new MessageModel();
            messageModel.setSenderStates(StringUtil.stringToList(states));
            messageModel.setSenderExpireDate(expireDate);
            messageModel.setSenderState(oldStates.toString());
            messageModel.setSenderStateChangeDate(changeDate);

            return sqlSessionTemplate.update(getStatementPrefix() + "changeSenderStateList", messageModel);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public int changeReceiverState(ArrayList<MessageState> oldStates, MessageState newState, Date changeDate,
        Date expireDate) {
        try {
            MessageModel messageModel = new MessageModel();
            messageModel.setReceiverStates(StringUtil.stringToList(oldStates));
            messageModel.setReceiverExpireDate(expireDate);
            messageModel.setReceiverState(newState.toString());
            messageModel.setReceiverStateChangeDate(changeDate);

            return sqlSessionTemplate.update(getStatementPrefix() + "changeReceiverStateList", messageModel);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public int clearDeletedMessage(MessageState deletedState) {
        try {
            MessageModel messageModel = new MessageModel();
            messageModel.setSenderState(deletedState.toString());
            messageModel.setReceiverState(deletedState.toString());

            return sqlSessionTemplate.update(getStatementPrefix() + "clearDeletedMessage", messageModel);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Long countUnread(Long userId, MessageState receiverState) {
        try {
            MessageModel messageModel = new MessageModel();
            messageModel.setReceiverId(userId);
            messageModel.setIsRead(false);
            messageModel.setReceiverState(receiverState.toString());

            return (long)sqlSessionTemplate.update(getStatementPrefix() + POSTFIX_GET_COUNT_BY_MODEL, messageModel);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void markRead(Long userId, List<Long> ids) {
        try {
            MessageModel messageModel = new MessageModel();
            messageModel.setReceiverId(userId);
            messageModel.setIds(ids);

            sqlSessionTemplate.update(getStatementPrefix() + "markRead", messageModel);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    // ////////////////////////////////////// 自定义Dao使用自定义sql语句去实现/////////////////////////

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
    public int insert(Message entity) {
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
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.insert(entity);
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
    public int insertSelective(Message entity) {
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
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.insertSelective(entity);
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
    public int insertBatch(List<Message> entityList) {
        try {
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            Date now = new Date();
            for (Message entity : entityList) {
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
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.insertList(entityList);
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
    public int delete(Message record) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.delete(record);
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
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.deleteByPrimaryKey(id);
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
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.deleteByExample(example);
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
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.deleteByIds(ids);
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
    public int updateByPrimaryKey(Message object) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        Message oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
        int records = messageDao.updateByPrimaryKey(object);
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
    public int updateByPrimaryKeySelective(Message object) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        Message oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
        int records = messageDao.updateByPrimaryKeySelective(object);
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
    public int updateByExample(Message object, Object example) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        Message oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
        int records = messageDao.updateByExample(object, example);
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
    public int updateByExampleSelective(Message object, Object example) {
        if (object == null) {
            throw new NullPointerException("需要更新的对象不能为空");
        }
        Message oldObject = null;
        // 父类为BaseBizEntity的entity保存时需要自动记录username、createTime和lastUpdateTime属性
        if (object instanceof BaseBizEntity) {
            BaseBizEntity baseBizEntity = (BaseBizEntity)object;
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            baseBizEntity.setLastUpdatorName(username);
            baseBizEntity.setLastUpdator(username);
            baseBizEntity.setLastUpdateTime(new Date());

            oldObject = get((Long)baseBizEntity.getId());
        }
        MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
        int records = messageDao.updateByExampleSelective(object, example);
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
    public List<Message> select(Message record) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.select(record);
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
    public Message selectByPrimaryKey(Long id) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectByPrimaryKey(id);
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
    public List<Message> selectAll(Long id) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectAll();
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
    public Message selectOne(Message record) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectOne(record);
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
    public int selectCount(Message record) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectCount(record);
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
    public List<Message> selectByExample(Object example) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectByExample(example);
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
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectCountByExample(example);
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
    public List<Message> selectByRowBounds(Message record, RowBounds rowBounds) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectByRowBounds(record, rowBounds);
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
    public List<Message> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectByExampleAndRowBounds(example, rowBounds);
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
    public List<Message> selectByIds(String ids) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectByIds(ids);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    // ///////////////////////////
    /** 单表查询方法，使用tk.mapper通用查询方法 **/

    /** 其他的tk.mapper通用方法实现 **/
    @Override
    public Message selectByPrimaryKey(Object key) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectByPrimaryKey(key);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public List<Message> selectAll() {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.selectAll();
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int insertList(List<Message> recordList) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.insertList(recordList);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int insertUseGeneratedKeys(Message record) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.insertUseGeneratedKeys(record);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        try {
            MessageDao messageDao = getSqlSession().getMapper(MessageDao.class);
            return messageDao.deleteByPrimaryKey(key);
        } catch (Exception e) {
            throw new DAOException("查询数据失败", e);
        }
    }
}
