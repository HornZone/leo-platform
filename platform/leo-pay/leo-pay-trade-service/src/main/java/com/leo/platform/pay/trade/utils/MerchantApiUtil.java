package com.leo.platform.pay.trade.utils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * <b>功能说明:商户API工具类
 */
public class MerchantApiUtil {

    /**
     * 获取参数签名
     * 
     * @param paramMap 签名参数
     * @param paySecret 签名密钥
     * @return
     */
    public static String getSign(Map<String, Object> paramMap, String paySecret) {
        SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            Object value = m.getValue();
            if (value != null && StringUtils.isNotBlank(String.valueOf(value))) {
                stringBuffer.append(m.getKey()).append("=").append(value).append("&");
            }
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());

        String argPreSign = stringBuffer.append("&paySecret=").append(paySecret).toString();
        String signStr = MD5Util.encode(argPreSign).toUpperCase();

        return signStr;
    }

    /**
     * 获取参数拼接串
     * 
     * @param paramMap
     * @return
     */
    public static String getParamStr(Map<String, Object> paramMap) {
        SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            Object value = m.getValue();
            if (value != null && StringUtils.isNotBlank(String.valueOf(value))) {
                stringBuffer.append(m.getKey()).append("=").append(value).append("&");
            }
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());

        return stringBuffer.toString();
    }

    /**
     * 验证商户签名
     * 
     * @param paramMap 签名参数
     * @param paySecret 签名私钥
     * @param signStr 原始签名密文
     * @return
     */
    public static boolean isRightSign(Map<String, Object> paramMap, String paySecret, String signStr) {

        if (StringUtils.isBlank(signStr)) {
            return false;
        }

        String sign = getSign(paramMap, paySecret);
        if (signStr.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

}
