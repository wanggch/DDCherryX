package cn.ddcherry.common.mybatis.enums;

/**
 * 常见敏感字段类型。
 */
public enum SensitiveType {
    /** 手机号 */
    MOBILE,
    /** 身份证号 */
    ID_CARD,
    /** 邮箱 */
    EMAIL,
    /** 姓名 */
    NAME,
    /** 地址或通用字段，保留少量前后缀 */
    ADDRESS,
    /** 自定义掩码 */
    CUSTOM
}
