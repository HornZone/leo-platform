package com.leo.platform.common.exception;

public class OptLockException extends GLException {

    private static final long serialVersionUID = -4487429038874269500L;

    public static final String ERR_OPT_LOCK_CODE = "E010004000000013";
    public static final String ERR_OPT_LOCK_DESC = "数据唯一性冲突";

    public OptLockException(Exception e) {
        super(ERR_OPT_LOCK_CODE + "_" + ERR_OPT_LOCK_DESC, e);
    }

    public OptLockException(String msg) {
        super(msg);
    }

    public OptLockException() {
        super(ERR_OPT_LOCK_CODE + "_" + ERR_OPT_LOCK_DESC);
    }

    public OptLockException(Throwable e) {
        super(ERR_OPT_LOCK_CODE + "_" + ERR_OPT_LOCK_DESC, e);
    }

    public OptLockException(String msg, Throwable e) {
        super(msg, e);
    }

    public String getErrorCode() {
        return ERR_OPT_LOCK_CODE;
    }

    public String getErrorMsg() {
        return ERR_OPT_LOCK_DESC;
    }
}
