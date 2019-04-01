package com.leo.platform.common.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务exception，支持将多个参数传递到前层，构造成用户方便追踪原因的message.
 * 
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * 构造错误信息的参数.
     */
    private List<String> parms;
    /**
     * 错误信息代码，用做与i18n关联.
     */
    private String errorcode;
    /**
     * 错误消息.
     */
    private String message;

    /**
     * 字段错误信息列表.
     */
    private Map<String, String> fieldErrors;
    /**
     * 错误信息列表.
     */
    private List<String> errors;

    public BusinessException() {
        super();
    }

    public BusinessException(String errorcode) {
        super();
        this.errorcode = errorcode;
    }

    /**
     * .
     * 
     * @param errorcode 错误编码
     * @param message 异常信息
     */
    public BusinessException(String errorcode, String message) {
        super(message);
        this.errorcode = errorcode;
        this.message = message;
    }

    /**
     * .
     * 
     * @param errorcode 错误编码
     * @param message 异常信息
     * @param cause 原因excpeiton
     */
    public BusinessException(String errorcode, String message, Throwable cause) {
        super(message, cause);
        this.errorcode = errorcode;
        this.message = message;
    }

    public BusinessException(String errorcode, Throwable cause) {
        super(cause);
        this.errorcode = errorcode;
    }

    /**
     * .
     * 
     * @param errorcode 错误编码
     * @param message 异常信息
     * @param parameter 参数
     */
    public BusinessException(String errorcode, String message, String parameter) {
        super(message);
        this.errorcode = errorcode;
        this.message = message;
        addParameter(parameter);
    }

    /**
     * 添加参数.
     * 
     * @param parameter 参数
     */
    public void addParameter(String parameter) {
        if (parms == null) {
            parms = new ArrayList<String>();
        }
        parms.add(parameter);
    }

    public List<String> getParameters() {
        return parms;
    }

    public List<String> getParms() {
        return parms;
    }

    public void setParms(List<String> parms) {
        this.parms = parms;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 是否有字段校验不通过的错误.
     * 
     * @return true or false
     */
    public boolean hasFieldError() {
        if (fieldErrors != null && fieldErrors.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加字段错误信息.
     * 
     * @param fieldName 字段名
     * @param errorMsg 错误信息
     */
    public void addFieldError(String fieldName, String errorMsg) {
        if (fieldErrors == null) {
            fieldErrors = new LinkedHashMap<String, String>();
        }
        fieldErrors.put(fieldName, errorMsg);
    }

    /**
     * 删除字段错误信息.
     * 
     * @param fieldName 字段名
     */
    public boolean removeFieldError(String fieldName, String errorMsg) {
        if (fieldErrors == null) {
            return false;
        }
        fieldErrors.remove(fieldName);
        return true;
    }

    /**
     * 添加错误信息.
     * 
     * @param errorMsg 错误信息
     */
    public void addError(String errorMsg) {
        if (errors == null) {
            errors = new ArrayList<String>();
        }
        errors.add(errorMsg);
    }

    /**
     * 删除所有错误信息.
     */
    public boolean clearErrors() {
        if (errors == null) {
            return false;
        }
        errors.clear();
        return true;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
