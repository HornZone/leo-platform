package com.leo.platform.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于生成前台行title
 * */
@Retention(RetentionPolicy.RUNTIME)
// 运行时保留
@Target({ElementType.FIELD})
// 注解对象为方
public @interface FieldRemark {
    public String value() default "";
}
