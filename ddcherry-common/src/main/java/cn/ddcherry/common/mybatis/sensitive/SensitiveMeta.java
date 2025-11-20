package cn.ddcherry.common.mybatis.sensitive;

import cn.ddcherry.common.mybatis.enums.SensitiveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 敏感字段元数据，用于保存脱敏策略。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveMeta {

    private SensitiveType type;

    private String maskChar;

    private int prefixKeep;

    private int suffixKeep;
}
