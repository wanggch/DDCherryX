package cn.ddcherry.framework.config;

import cn.ddcherry.framework.mybatis.datascope.DataScopePermissionHandler;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * MyBatis-Plus configuration, registering pagination、数据权限、逻辑删除等全局能力。
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(DataScopePermissionHandler dataScopePermissionHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new DataPermissionInterceptor(dataScopePermissionHandler));
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> {
            GlobalConfig globalConfig = Optional.ofNullable(properties.getGlobalConfig()).orElseGet(GlobalConfig::new);
            GlobalConfig.DbConfig dbConfig = Optional.ofNullable(globalConfig.getDbConfig()).orElseGet(GlobalConfig.DbConfig::new);
            dbConfig.setLogicDeleteField("delFlag");
            dbConfig.setLogicDeleteValue("1");
            dbConfig.setLogicNotDeleteValue("0");
            globalConfig.setDbConfig(dbConfig);
            properties.setGlobalConfig(globalConfig);
        };
    }
}
