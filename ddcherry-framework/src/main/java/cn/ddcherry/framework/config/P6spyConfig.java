package cn.ddcherry.framework.config;

import cn.ddcherry.framework.config.p6spy.P6spySqlFormatStrategy;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.Slf4JLogger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * p6spy 输出配置。
 */
@Configuration
@EnableConfigurationProperties(P6spyProperties.class)
public class P6spyConfig {

    private final P6spyProperties properties;

    public P6spyConfig(P6spyProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        P6spySqlFormatStrategy.configure(properties.isEnabled(), properties.isFormatSql());
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatStrategy.class.getName());
        P6SpyOptions.getActiveInstance().setAppender(Slf4JLogger.class.getName());
    }
}
