package com.leo.platform.common.dao.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.leo.platform.common.dao.util.EntityCodePrefixMapping.CodeConfig;

/**
 * 根据ID，生成各Entity的code
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
 * @date 2016年10月16日 下午9:29:58
 * 
 */
public class CodeUtil {

    public static String generateCode(Class<?> entityClass, Long id) {
        String className = getClassName(entityClass);

        CodeConfig config = EntityCodePrefixMapping.MAP.get(className);

        if (config == null) {
            config = EntityCodePrefixMapping.DEFAULT_CONFIG;
        }
        return generateCode(config.getPrefix(), config.getDateFormat(), config.getNumberLength(), id);
    }

    public static String generateCode(String className, Long id) {

        CodeConfig config = EntityCodePrefixMapping.MAP.get(className);

        if (config == null) {
            config = EntityCodePrefixMapping.DEFAULT_CONFIG;
        }
        return generateCode(config.getPrefix(), config.getDateFormat(), config.getNumberLength(), id);
    }

    public static String generateCode(String prefix, String dataFormat, int numberLength, Long id) {
        String seq = StringUtils.right(String.format("%0" + numberLength + "d", id), 6);
        if (StringUtils.isBlank(dataFormat)) {
            return prefix + seq;
        } else {
            DateFormat df = null;
            try {
                df = new SimpleDateFormat(dataFormat);
            } catch (Exception e) {
                df = new SimpleDateFormat(EntityCodePrefixMapping.DEFAULT_CONFIG.getDateFormat());
            }
            return prefix + df.format(new Date()) + seq;
        }
    }

    public static String generateCode(CodeConfig config, Long id) {
        return generateCode(config.getPrefix(), config.getDateFormat(), config.getNumberLength(), id);
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

    public static void main(String[] args) {
        System.out.println(generateCode("SKU", "yyMMdd", 6, 1234567L));
        System.out.println(generateCode("PG", "yyMMdd", 6, 1234L));
    }
}
