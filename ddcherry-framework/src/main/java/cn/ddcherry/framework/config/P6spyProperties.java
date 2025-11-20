package cn.ddcherry.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * p6spy 配置开关。
 */
@ConfigurationProperties(prefix = "ddcherry.mybatis.p6spy")
public class P6spyProperties {

    /**
     * 是否启用 SQL 日志。
     */
    private boolean enabled = true;

    /**
     * 是否进行格式化输出。
     */
    private boolean formatSql = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFormatSql() {
        return formatSql;
    }

    public void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }
}
