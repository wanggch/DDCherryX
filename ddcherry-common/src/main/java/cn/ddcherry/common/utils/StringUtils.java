package cn.ddcherry.common.utils;

import java.util.Objects;

/**
 * Basic string utility helpers.
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean equals(Object first, Object second) {
        return Objects.equals(first, second);
    }
}
