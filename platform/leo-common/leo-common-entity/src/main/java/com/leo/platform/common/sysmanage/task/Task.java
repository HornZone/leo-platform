package com.leo.platform.common.sysmanage.task;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.leo.platform.common.annotation.FieldRemark;
import com.leo.platform.common.entity.BaseBizEntity;
import com.leo.platform.common.sysmanage.task.enums.TaskGropEnum;
import com.leo.platform.common.sysmanage.task.enums.TaskStatusEnum;
import com.leo.platform.common.sysmanage.task.enums.TaskTypeEnum;

/**
 * 任务信息
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
 * @date 2016年10月19日 下午3:36:18
 * 
 */
@Entity
@Table(name = "leo_task")
public class Task extends BaseBizEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 任务类型 描述当前任务的类型说明
     */

    @FieldRemark("任务类型")
    private TaskTypeEnum type;

    /**
     * 任务分组代码
     */
    @FieldRemark("任务分组代码")
    private TaskGropEnum groupType;

    /**
     * 任务执行状态 INIT初始化 RUNING 执行中 COMPLETE 执行完成 TERMINATE 执行终止 该状态汇总统计子项任务的状态数据
     */
    @FieldRemark("任务执行状态 INIT初始化 RUNING 执行中 COMPLETE 执行完成 TERMINATE 执行终止")
    private TaskStatusEnum status;

    /**
     * 任务子项数量
     */
    @FieldRemark("任务子项数量")
    private Long itemNum;

    public TaskTypeEnum getType() {
        return type;
    }

    public void setType(TaskTypeEnum type) {
        this.type = type;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

    public Long getItemNum() {
        return itemNum;
    }

    public void setItemNum(Long itemNum) {
        this.itemNum = itemNum;
    }

    public TaskGropEnum getGroupType() {
        return groupType;
    }

    public void setGroupType(TaskGropEnum groupType) {
        this.groupType = groupType;
    }

}
