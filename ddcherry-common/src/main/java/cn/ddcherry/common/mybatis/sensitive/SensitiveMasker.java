package cn.ddcherry.common.mybatis.sensitive;

import cn.ddcherry.common.mybatis.enums.SensitiveType;
import cn.ddcherry.common.utils.StringUtils;

/**
 * 脱敏工具方法。
 */
public final class SensitiveMasker {

    private SensitiveMasker() {
    }

    public static String mask(String value, SensitiveMeta meta) {
        if (StringUtils.isBlank(value) || meta == null) {
            return value;
        }
        SensitiveType type = meta.getType();
        return switch (type) {
            case MOBILE -> maskMiddle(value, 3, 4, meta.getMaskChar());
            case ID_CARD -> maskMiddle(value, 4, 4, meta.getMaskChar());
            case EMAIL -> maskEmail(value, meta.getMaskChar());
            case NAME -> maskMiddle(value, 1, 0, meta.getMaskChar());
            case ADDRESS -> maskMiddle(value, meta.getPrefixKeep(), meta.getSuffixKeep(), meta.getMaskChar());
            case CUSTOM -> maskMiddle(value, meta.getPrefixKeep(), meta.getSuffixKeep(), meta.getMaskChar());
        };
    }

    private static String maskEmail(String email, String maskChar) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return maskMiddle(email, 1, 0, maskChar);
        }
        String prefix = email.substring(0, atIndex);
        String suffix = email.substring(atIndex);
        return maskMiddle(prefix, 1, 0, maskChar) + suffix;
    }

    private static String maskMiddle(String value, int prefix, int suffix, String maskChar) {
        if (value.length() <= prefix + suffix) {
            return value;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(value, 0, prefix);
        builder.append(maskChar.repeat(Math.max(1, value.length() - prefix - suffix)));
        if (suffix > 0) {
            builder.append(value, value.length() - suffix, value.length());
        }
        return builder.toString();
    }
}
