package com.leo.platform.common.util;

import org.apache.commons.codec.binary.Hex;
import org.springframework.util.SerializationUtils;

/**
 * 序列化和反序列化类,序列化将对象使用hex序列化成16进制的序列，反序列化则相反
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
 * @date 2016年10月16日 上午9:21:47
 * 
 */
public class SerializableUtils {
    public static String writeObject(Object o) throws Exception {
        return Hex.encodeHexString(SerializationUtils.serialize(o));
    }

    // 反序列化String字符串为对象

    public static Object readObject(String hexStr) throws Exception {
        return SerializationUtils.deserialize(Hex.decodeHex(hexStr.toCharArray()));
    }
}
