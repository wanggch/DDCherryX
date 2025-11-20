package cn.ddcherry.common.mybatis.datascope;

import java.util.Collections;
import java.util.List;

/**
 * 数据权限上下文工具，封装线程变量的设置与清理。
 */
public final class DataScopeUserContextHolder {

    private static final ThreadLocal<DataScopeUserContext> CONTEXT = new ThreadLocal<>();

    private DataScopeUserContextHolder() {
    }

    public static void set(DataScopeUserContext context) {
        CONTEXT.set(context);
    }

    public static DataScopeUserContext get() {
        return CONTEXT.get();
    }

    public static Long currentUserId() {
        DataScopeUserContext context = get();
        return context == null ? null : context.getUserId();
    }

    public static List<Long> currentDeptIds() {
        DataScopeUserContext context = get();
        if (context == null || context.getDeptIds() == null) {
            return Collections.emptyList();
        }
        return context.getDeptIds();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
