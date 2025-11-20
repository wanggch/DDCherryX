package cn.ddcherry.common.mybatis.core;

/**
 * 审计信息上下文工具类，使用 ThreadLocal 保存与清理当前线程中的登录用户信息。
 */
public final class AuditContextHolder {

    private static final ThreadLocal<AuditContext> CONTEXT = new ThreadLocal<>();

    private AuditContextHolder() {
    }

    public static void set(AuditContext auditContext) {
        CONTEXT.set(auditContext);
    }

    public static AuditContext get() {
        return CONTEXT.get();
    }

    public static Long getUserId() {
        AuditContext context = get();
        return context == null ? null : context.getUserId();
    }

    public static Long getDeptId() {
        AuditContext context = get();
        return context == null ? null : context.getDeptId();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
