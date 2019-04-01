package com.leo.platform.common.dao.impl;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.leo.platform.common.annotation.NotNeedCode;
import com.leo.platform.common.dao.util.CodeUtil;

public class CodeGeneratorFinder {

    public final static Map<String, CodeGenerator> config = Maps.newHashMap();

    private static ConcurrentMap<Class, Boolean> allowCodeCfg = new ConcurrentHashMap<Class, Boolean>();

    static {
        // 任务跟踪code生成器
        config.put("Task", new TaskCodeGenerator());
    }

    @SuppressWarnings("unchecked")
    public static <T> CodeGenerator<T> get(Class<T> clazz) {
        return MoreObjects.firstNonNull(config.get(CodeUtil.getClassName(clazz)), new DefaultCodeGenerator<T>());
    }

    public static boolean allowCode(Class clazz) {
        if (allowCodeCfg.containsKey(clazz)) {
            return allowCodeCfg.get(clazz);
        } else {
            Boolean allowCode = true;
            Annotation a = clazz.getAnnotation(NotNeedCode.class);
            if (a != null) {
                allowCode = false;
            }
            allowCodeCfg.putIfAbsent(clazz, allowCode);
            return allowCode;
        }
    }
}
