package cn.ddcherry.system.model;

import lombok.Data;

/**
 * Login response carrying token information.
 */
@Data
public class LoginResponse {
    /**
     * Token 名称
     */
    private String tokenName;

    /**
     * Token 值
     */
    private String tokenValue;
}
