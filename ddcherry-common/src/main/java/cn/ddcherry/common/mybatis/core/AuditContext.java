package cn.ddcherry.common.mybatis.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 审计上下文，保存当前登录用户的标识信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditContext {

    /**
     * 当前用户ID。
     */
    private Long userId;

    /**
     * 当前用户所在部门ID。
     */
    private Long deptId;

    /**
     * 当前用户名称。
     */
    private String username;
}
