package cn.ddcherry.framework.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Sa-Token configuration.
 *
 * <p>Registers login interception and switches token persistence to Redis.</p>
 */
@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {

    private static final List<String> EXCLUDE_PATHS = List.of(
            "/api/auth/login",
            "/doc.html**",
            "/webjars/**",
            "/swagger-resources/**",
            "/v3/api-docs/**"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }

    /**
     * Use Redis for Sa-Token data persistence to align with the project's cache configuration.
     *
     * @param stringRedisTemplate Redis template configured by Spring Boot
     * @return Sa-Token DAO backed by Redis
     */
    @Bean
    public SaTokenDao saTokenDao(StringRedisTemplate stringRedisTemplate) {
        return new SaTokenDaoRedisJackson(stringRedisTemplate);
    }
}
