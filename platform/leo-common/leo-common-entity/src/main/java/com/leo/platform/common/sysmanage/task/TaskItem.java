package com.leo.platform.common.sysmanage.task;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.annotation.FieldRemark;
import com.leo.platform.common.annotation.NotNeedCode;
import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.common.entity.BizObject;
import com.leo.platform.common.sysmanage.task.enums.TaskStatusEnum;

/**
 * 任务项
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2016年10月19日 下午5:32:37
 * 
 */
@Entity
@Table(name = "leo_taskitem")
@NotNeedCode
public class TaskItem extends BaseBizEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 所属的任务ID
     */
    @FieldRemark("所属的任务ID")
    private Long taskId;

    /**
     * 当前操作的实体类型
     */
    @FieldRemark("当前操作的实体类型")
    private BizObject entityType;

    /**
     * 当前操作的实体ID
     */
    @FieldRemark("当前操作的实体ID")
    private Long entityId;

    /**
     * 任务执行状态 INIT初始化 RUNING 执行中 COMPLETE 执行完成 TERMINATE 执行终止 EXCEPTION 异常结束
     */
    @FieldRemark("任务执行状态")
    private TaskStatusEnum status;

    /**
     * 执行结果反馈代码
     */
    @FieldRemark("执行结果反馈代码")
    private String appCode;

    /**
     * 执行结果反馈描述
     */
    @FieldRemark("执行结果反馈描述")
    private String remark;

    /**
     * 执行开始时间
     */
    @FieldRemark("执行开始时间")
    private Date startTime;

    /**
     * 执行结束时间
     */
    @FieldRemark("执行结束时间")
    private Date endTime;

    /**
     * 执行任务的业务实体代码 作为展示用,不会在表中实际存储
     */
    private String entityCode;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public BizObject getEntityType() {
        return entityType;
    }

    public void setEntityType(BizObject entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

}
