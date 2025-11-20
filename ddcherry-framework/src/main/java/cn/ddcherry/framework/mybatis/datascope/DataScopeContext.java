package cn.ddcherry.framework.mybatis.datascope;

/**
 * 保存当前线程的数据权限配置。
 */
public final class DataScopeContext {

    private static final ThreadLocal<DataScopeRule> CONTEXT = new ThreadLocal<>();

    private DataScopeContext() {
    }

    public static void set(DataScopeRule rule) {
        CONTEXT.set(rule);
    }

    public static DataScopeRule get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
