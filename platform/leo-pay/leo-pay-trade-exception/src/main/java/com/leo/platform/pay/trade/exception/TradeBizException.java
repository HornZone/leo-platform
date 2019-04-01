package com.leo.platform.pay.trade.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leo.platform.pay.common.exception.BizException;

/**
 * <b>功能说明:交易模块异常类</b>
 */
public class TradeBizException extends BizException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3536909333010163563L;

    /** 错误的支付方式 **/
    public static final int TRADE_PAY_WAY_ERROR = 102;

    /** 微信异常 **/
    public static final int TRADE_WEIXIN_ERROR = 103;
    /** 订单异常 **/
    public static final int TRADE_ORDER_ERROR = 104;

    /** 交易记录状态不为成功 **/
    public static final int TRADE_ORDER_STATUS_NOT_SUCCESS = 105;

    /** 支付宝异常 **/
    public static final int TRADE_ALIPAY_ERROR = 106;

    /** 参数异常 **/
    public static final int TRADE_PARAM_ERROR = 107;

    private static final Log LOG = LogFactory.getLog(TradeBizException.class);

    public TradeBizException() {}

    public TradeBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public TradeBizException(int code, String msg) {
        super(code, msg);
    }

    public TradeBizException print() {
        LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }
}
