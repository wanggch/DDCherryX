package cn.ddcherry.framework.mybatis.datascope;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * 基于 {@link DataScope} 的切面，在方法执行前写入数据权限规则。
 */
@Aspect
@Component
public class DataScopeAspect {

    @Around("@annotation(dataScope)")
    public Object around(ProceedingJoinPoint joinPoint, DataScope dataScope) throws Throwable {
        DataScopeRule rule = DataScopeRule.builder()
            .deptAlias(dataScope.deptAlias())
            .userAlias(dataScope.userAlias())
            .ignoreUser(dataScope.ignoreUser())
            .build();
        DataScopeContext.set(rule);
        try {
            return joinPoint.proceed();
        } finally {
            DataScopeContext.clear();
        }
    }

    @Around("@within(dataScope)")
    public Object aroundType(ProceedingJoinPoint joinPoint) throws Throwable {
        DataScope dataScope = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), DataScope.class);
        if (dataScope == null) {
            return joinPoint.proceed();
        }
        DataScopeRule rule = DataScopeRule.builder()
            .deptAlias(dataScope.deptAlias())
            .userAlias(dataScope.userAlias())
            .ignoreUser(dataScope.ignoreUser())
            .build();
        DataScopeContext.set(rule);
        try {
            return joinPoint.proceed();
        } finally {
            DataScopeContext.clear();
        }
    }
}
