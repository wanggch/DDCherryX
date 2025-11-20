package cn.ddcherry.framework.mybatis.sensitive;

import cn.ddcherry.common.mybatis.annotation.Sensitive;
import cn.ddcherry.common.mybatis.sensitive.SensitiveFieldRegistry;
import cn.ddcherry.common.mybatis.sensitive.SensitiveMeta;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * 扫描实体模型，注册 {@link Sensitive} 标记的字段到 {@link SensitiveFieldRegistry}。
 */
@Component
public class SensitiveFieldRegistrar {

    private static final Logger log = LoggerFactory.getLogger(SensitiveFieldRegistrar.class);

    @Value("${ddcherry.mybatis.scan-base-package:cn.ddcherry}")
    private String basePackage;

    @PostConstruct
    public void registerSensitiveFields() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(TableName.class));
        scanner.findCandidateComponents(basePackage).forEach(beanDefinition -> {
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                for (Field field : clazz.getDeclaredFields()) {
                    Sensitive sensitive = field.getAnnotation(Sensitive.class);
                    if (sensitive == null) {
                        continue;
                    }
                    String column = resolveColumnName(field);
                    SensitiveMeta meta = SensitiveMeta.builder()
                        .type(sensitive.type())
                        .maskChar(sensitive.maskChar())
                        .prefixKeep(sensitive.prefixKeep())
                        .suffixKeep(sensitive.suffixKeep())
                        .build();
                    SensitiveFieldRegistry.register(column, meta);
                }
            } catch (ClassNotFoundException e) {
                log.warn("Unable to load entity class {} for sensitive scanning", beanDefinition.getBeanClassName(), e);
            }
        });
    }

    private String resolveColumnName(Field field) {
        TableField tableField = field.getAnnotation(TableField.class);
        if (tableField != null && cn.ddcherry.common.utils.StringUtils.isNotBlank(tableField.value())) {
            return tableField.value();
        }
        return camelToUnderline(field.getName());
    }

    private String camelToUnderline(String name) {
        StringBuilder builder = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                builder.append('_').append(Character.toLowerCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString().toLowerCase(Locale.ROOT);
    }
}
