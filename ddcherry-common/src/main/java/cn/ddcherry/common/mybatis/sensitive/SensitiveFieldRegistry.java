package cn.ddcherry.common.mybatis.sensitive;

import cn.ddcherry.common.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 敏感字段注册表，缓存列名与脱敏策略。
 */
public final class SensitiveFieldRegistry {

    private static final Map<String, SensitiveMeta> COLUMN_META = new ConcurrentHashMap<>();

    private SensitiveFieldRegistry() {
    }

    public static void register(String columnName, SensitiveMeta meta) {
        if (StringUtils.isBlank(columnName) || meta == null) {
            return;
        }
        COLUMN_META.putIfAbsent(columnName.toLowerCase(), meta);
    }

    public static String desensitize(String column, String origin) {
        if (origin == null || StringUtils.isBlank(column)) {
            return origin;
        }
        SensitiveMeta meta = COLUMN_META.get(column.toLowerCase());
        if (meta == null) {
            return origin;
        }
        return SensitiveMasker.mask(origin, meta);
    }
}
