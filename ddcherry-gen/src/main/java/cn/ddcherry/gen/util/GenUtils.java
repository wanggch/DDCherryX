package cn.ddcherry.gen.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilities for generator module.
 */
public final class GenUtils {

    private GenUtils() {
    }

    public static Map<String, Object> defaultConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("author", "ddcherry");
        config.put("package", "cn.ddcherry");
        return config;
    }
}
