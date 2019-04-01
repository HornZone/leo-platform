package com.leo.platform.common.dao.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class EntitySequenceMapping {

    /**
     * 默认使用的sequence， 如果实体类不指定sequence，则使用默认的sequence来生成ID
     */
    public static final String DEFAULT_CODE_SEQUENCE = "default_code_sequence";
    public static final String DEFAULT_ID_SEQUENCE = "default_id_sequence";

    // key为Entity class , value为序列的名字
    public static final Map<String, String> MAP = new HashMap<String, String>();

    static {
        // MAP.put( "OrderBaseStatus" , "TI_OB_STATUS");
    }

    /**
     * 根据不同的类，获取Sequence
     */
    public static String getSequence(Class<?> entityClass) {
        String className = getClassName(entityClass);

        String sequence = MAP.get(className);
        if (StringUtils.isBlank(sequence)) {
            // if(entityClass.isAnnotationPresent(SequenceGenerator.class)){
            // sequence = entityClass.getAnnotation(SequenceGenerator.class).name();
            // }

            // if(StringUtils.isBlank(sequence)){
            sequence = DEFAULT_CODE_SEQUENCE;
            // }

        }
        return sequence;
    }

    public static String getClassName(Class<?> entityClass) {
        String className = entityClass.getName();
        if (className.indexOf('$') > 0) {
            className = className.substring(0, className.lastIndexOf('$'));
        }
        if (className.indexOf('.') > 0) {
            className = className.substring(className.lastIndexOf('.') + 1, className.length());
        }
        return className;
    }
}
