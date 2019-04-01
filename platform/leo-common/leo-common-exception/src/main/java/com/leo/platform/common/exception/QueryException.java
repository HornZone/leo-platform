package com.leo.platform.common.exception;

public class QueryException extends GLException {

    private static final long serialVersionUID = 1L;

    public static final String ERR_QUERY_CODE = "E030002000030000";

    public static final String ERR_QUERY_DESC = "数据查询异常";

    public QueryException(Exception e) {
        super(ERR_QUERY_CODE + "_" + ERR_QUERY_DESC, e);
    }

    public QueryException(String msg) {
        super(msg);
    }

    public QueryException(String msg, Throwable e) {
        super(msg, e);
    }

    public QueryException() {}

    public String getErrorCode() {
        return ERR_QUERY_CODE;
    }

    public String getErrorMsg() {
        return ERR_QUERY_DESC;
    }
}
