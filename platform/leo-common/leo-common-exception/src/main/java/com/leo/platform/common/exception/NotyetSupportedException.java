package com.leo.platform.common.exception;

public class NotyetSupportedException extends GLException {

    private static final String ERR_NOTYET_SUPPORTED_DESC = "NotyetSupportedException";

    private static final long serialVersionUID = -2015846534435514785L;

    public static final String ERR_NOTYET_SUPPORTED_CODE = "E030002000040000";

    public NotyetSupportedException(Exception e) {
        super(ERR_NOTYET_SUPPORTED_CODE + "_" + ERR_NOTYET_SUPPORTED_DESC, e);
    }

    public NotyetSupportedException(String msg) {
        super(msg);
    }

    public NotyetSupportedException() {
        super(ERR_NOTYET_SUPPORTED_CODE + "_" + ERR_NOTYET_SUPPORTED_DESC);
    }

    public NotyetSupportedException(Throwable e) {
        super(ERR_NOTYET_SUPPORTED_CODE + "_" + ERR_NOTYET_SUPPORTED_DESC, e);
    }

    public NotyetSupportedException(Throwable e, String msg) {
        super(msg, e);
    }

    public String getErrorCode() {
        return ERR_NOTYET_SUPPORTED_CODE;
    }

    public String getErrorMsg() {
        return ERR_NOTYET_SUPPORTED_DESC;
    }
}
