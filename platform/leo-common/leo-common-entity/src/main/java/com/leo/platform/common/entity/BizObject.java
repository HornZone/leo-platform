package com.leo.platform.common.entity;

/**
 * 用来定义系统中的有意义的实体。
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
 * @date 2016年10月19日 下午5:32:09
 * 
 */
public enum BizObject {

    DRIVER_APP_USER("司机APP账号"), DRIVER_AUDIT_INFO("司机APP账号审核信息"),

    /**
     * 缺省值 用于表示没有指定
     */
    NONE("缺省值");

    private String cn;

    private BizObject(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }

}
