package cn.ddcherry.common.mybatis.annotation;

import cn.ddcherry.common.mybatis.enums.SensitiveType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记敏感字段的注解，结合 {@link cn.ddcherry.common.mybatis.handler.SensitiveTypeHandler} 在查询时自动脱敏。
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sensitive {

    /**
     * 预置的脱敏类型。
     */
    SensitiveType type() default SensitiveType.CUSTOM;

    /**
     * 自定义掩码符号。
     */
    String maskChar() default "*";

    /**
     * 保留前缀长度。
     */
    int prefixKeep() default 3;

    /**
     * 保留后缀长度。
     */
    int suffixKeep() default 2;
}
