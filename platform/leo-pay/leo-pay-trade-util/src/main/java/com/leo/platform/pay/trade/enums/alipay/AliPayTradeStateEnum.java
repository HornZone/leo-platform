package com.leo.platform.pay.trade.enums.alipay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能说明:支付宝订单状态枚举类.</b>
 * 
 */
public enum AliPayTradeStateEnum {
    TRADE_FINISHED("支付完成"), TRADE_SUCCESS("支付成功"), FAIL("支付失败");

    /** 描述 */
    private String desc;

    private AliPayTradeStateEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
        AliPayTradeStateEnum[] ary = AliPayTradeStateEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = ary[num].name();
            map.put("desc", ary[num].getDesc());
            enumMap.put(key, map);
        }
        return enumMap;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List toList() {
        AliPayTradeStateEnum[] ary = AliPayTradeStateEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            list.add(map);
        }
        return list;
    }

    public static AliPayTradeStateEnum getEnum(String name) {
        AliPayTradeStateEnum[] arry = AliPayTradeStateEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].name().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }

    /**
     * 取枚举的json字符串
     * 
     * @return
     */
    public static String getJsonStr() {
        AliPayTradeStateEnum[] enums = AliPayTradeStateEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (AliPayTradeStateEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
