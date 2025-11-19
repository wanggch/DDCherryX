package cn.ddcherry.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Simple logging aspect for controller methods.
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* cn.ddcherry..controller..*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("Entering {} with args {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("Exiting {} with result {}", joinPoint.getSignature(), result);
    }
}
