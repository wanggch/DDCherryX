package cn.ddcherry.framework.handler;

import cn.ddcherry.common.enums.ErrorCode;
import cn.ddcherry.common.exception.ServiceException;
import cn.ddcherry.common.result.Result;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public Result<Void> handleServiceException(ServiceException ex) {
        log.warn("Service exception", ex);
        return Result.failure(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public Result<Void> handleNotLoginException(NotLoginException ex) {
        log.warn("Unauthorized access", ex);
        return Result.failure(ErrorCode.UNAUTHORIZED);
    }

    @ExceptionHandler({NotPermissionException.class, NotRoleException.class})
    public Result<Void> handlePermissionException(Exception ex) {
        log.warn("Forbidden access", ex);
        return Result.failure(ErrorCode.FORBIDDEN);
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Result<Void> handleValidationException(Exception ex) {
        log.warn("Validation exception", ex);
        return Result.failure(ErrorCode.VALIDATION_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception ex) {
        log.error("System exception", ex);
        return Result.failure(ErrorCode.SYSTEM_ERROR);
    }
}
