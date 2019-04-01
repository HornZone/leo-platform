package com.leo.platform.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
    private static volatile Random randGen = null;
    private static char[] numbersAndLetters = null;
    private static Object initLock = new Object();

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static final String randomString(int length) {

        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    numbersAndLetters =
                        ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                    // numbersAndLetters =
                    // ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                }
            }
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
            // randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);
    }

    /**
     * Determine Whether the Sting is <code>null</code> or <code>""</code> or only containing blank character
     * <p>
     * Examples: <blockquote>
     * 
     * <pre>
     * StringUtil.isBlank(null) == true
     * StringUtil.isBlank("") == true
     * StringUtil.isBlank("  ") == true
     * StringUtil.isBlank("Best") == false
     * StringUtil.isBlank("  Best  ") == false
     * </pre>
     * 
     * </blockquote>
     * 
     * @param string the string for testing
     * 
     * @return If it is blank, return true
     */
    public static final boolean isBlank(String string) {
        if (string == null) {
            return true;
        }
        if (string.length() == 0) {
            return true;
        }
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static final String capitalizeFirstLetter(String sentence) {
        if (sentence == null) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        String[] words = sentence.split(" ");
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase());
            result.append(word.substring(1));
            result.append(" ");
        }
        return result.toString();
    }

    public static final boolean isNotBlank(String string) {
        return !(isBlank(string));
    }

    public static boolean isLong(String num, int radix) {
        boolean result = true;
        try {
            Integer.parseInt(num, radix);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    public static boolean isSame(String str1, String str2) {
        boolean result = true;
        if (StringUtil.isEmpty(str1)) {
            result = StringUtil.isEmpty(str2) ? true : false;
        } else {
            result = StringUtil.isEmpty(str2) ? false : str1.equals(str2);
        }
        return result;
    }

    public static final String lowercaseFirstLetter(String sentence) {
        if (sentence == null) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        String[] words = sentence.split(" ");
        for (String word : words) {
            result.append(word.substring(0, 1).toLowerCase());
            result.append(word.substring(1));
            result.append(" ");
        }
        return result.toString();
    }

    public static final String nullToEmpty(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public static final <T> String join(List<T> strList, String joinStr) {
        StringBuilder sb = new StringBuilder();

        for (T str : strList) {
            if (str != null) {
                sb.append(joinStr);
                sb.append(str);
            }
        }
        String resutStr = sb.toString();
        // 去掉头部的字符
        return resutStr.startsWith(joinStr) ? resutStr.substring(joinStr.length()) : resutStr;
    }

    public static final String firstNotEmppy(String first, String second) {
        return StringUtil.isEmpty(first) ? second : first;
    }

    public static final String trimWithStart(String str, String start) {
        if (!StringUtil.isBlank(str) && !StringUtil.isBlank(start)) {
            String local = str.trim();
            while (local.startsWith(start)) {
                local = local.substring(start.length());
            }
            return local;
        } else {
            return str;
        }

    }

    public static final String trimWithDumpStart(String str, String start) {
        if (!StringUtil.isBlank(str) && !StringUtil.isBlank(start)) {
            String local = str.trim();
            while (local.startsWith(start)) {
                local = local.substring(start.length());
            }
            return start + local;
        } else {
            return str;
        }

    }

    public static final boolean parseCn2Boolean(String str) {
        if (StringUtil.isNotBlank(str) && str.equals("是")) {
            return true;
        }
        return false;
    }

    public static final String toString(Double value) {
        NumberFormat nf = new DecimalFormat("#.#######");
        return value == null ? "" : nf.format(value);
    }

    public static final String toStringOrNull(Double value) {
        NumberFormat nf = new DecimalFormat("#.#######");
        return value == null ? null : nf.format(value);
    }

    /**
     * 从以空格连接的字符床中，获取长度为length的字符串 eg 赣K1112 湘M26231 return 赣K1112
     * 
     * @param str
     * @param length
     * @return
     */
    public static String subString(String str, int length) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        str = str.trim();
        String[] strs = StringUtils.split(str);
        if (strs != null && strs.length > 0) {
            if (strs[0] != null) {
                str = strs[0];
            }
        }
        // 截取长度
        if (str.length() > length) {
            str = str.substring(0, length);
        }
        return str;
    }

    public static <T> List<T> stringToList(String str, String splitStr) {
        String[] splitString = str.split(splitStr);
        List<T> list = Lists.newArrayList();
        for (String splitstr : splitString) {
            list.add((T)splitStr);
        }
        return list;
    }

    public static <T> ArrayList<String> stringToList(List<T> objectList) {
        ArrayList<String> list = Lists.newArrayList();
        for (T t : objectList) {
            list.add(t.toString());
        }
        return list;
    }
}
