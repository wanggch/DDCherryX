package cn.ddcherry.framework.mybatis.datascope;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限注解，标记需要进行数据范围过滤的方法或类。
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 部门字段别名（包含别名的列名，如 t.dept_id）。
     */
    String deptAlias() default "";

    /**
     * 用户字段别名（包含别名的列名，如 t.create_by）。
     */
    String userAlias() default "";

    /**
     * 是否跳过当前用户过滤。
     */
    boolean ignoreUser() default false;
}
