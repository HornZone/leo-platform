package com.leo.platform.common.sysmanage.task.enums;

public enum TaskTypeEnum {

    OB_LOCK(TaskGropEnum.OB_OU_ACC),

    OB_UNLOCK(TaskGropEnum.OB_OU_ACC),

    OB_PREVIOUS_OUTACCOUNT(TaskGropEnum.OB_OU_ACC),

    OB_PREVIOUS_OFF_OUTACCOUNT(TaskGropEnum.OB_OU_ACC),

    SHIPMENT_LOCK(TaskGropEnum.SHIP_OU_ACC),

    SHIPMENT_UNLOCK(TaskGropEnum.SHIP_OU_ACC),

    BATCH_LOCK(TaskGropEnum.BATCH_OU_ACC),

    BATCH_UNLOCK(TaskGropEnum.BATCH_OU_ACC),

    ACTIVITY_BATCH_REPORT(TaskGropEnum.ACTIVITY_TASK_REPORT),

    OB_PREVIOUS_RATE(TaskGropEnum.OB_RATE);

    private TaskGropEnum group;

    private TaskTypeEnum(TaskGropEnum group) {
        this.group = group;
    }

    public TaskGropEnum getGroup() {
        return group;
    }

}
