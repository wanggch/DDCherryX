package cn.ddcherry.gen.template;

import cn.ddcherry.gen.domain.GenTable;
import cn.ddcherry.gen.domain.GenTableColumn;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 使用类路径下模板文件渲染代码的工具。
 * <p>
 * 通过资源模板拼装控制器、服务、实体等核心文件，便于预览与下载。
 */
@Component
public class TemplateRenderer {

    private static final String NEW_LINE = System.lineSeparator();

    /**
     * 渲染所有代码模板并写入到收集器中。
     *
     * @param collector 生成结果收集器（键为文件路径，值为内容）
     * @param table     业务表配置
     */
    public void renderAll(Map<String, String> collector, GenTable table) {
        List<GenTableColumn> columns = Optional.ofNullable(table.getColumns()).orElseGet(ArrayList::new);
        GenTableColumn pkColumn = Optional.ofNullable(table.getPkColumn())
            .orElseGet(() -> columns.isEmpty() ? null : columns.get(0));

        String basePackage = table.getPackageName() + "." + table.getModuleName();
        String packagePath = basePackage.replace('.', '/');

        collector.put(String.format("java/%s/domain/%s.java", packagePath, table.getClassName()),
            renderFromTemplate("templates/entity.java.tpl", buildBindings(basePackage + ".domain", table,
                pkColumn, columns, this::buildEntityFields)));
        collector.put(String.format("java/%s/domain/bo/%sBo.java", packagePath, table.getClassName()),
            renderFromTemplate("templates/bo.java.tpl", buildBindings(basePackage + ".domain.bo", table,
                pkColumn, columns, this::buildSimpleFields)));
        collector.put(String.format("java/%s/domain/vo/%sVo.java", packagePath, table.getClassName()),
            renderFromTemplate("templates/vo.java.tpl", buildBindings(basePackage + ".domain.vo", table,
                pkColumn, columns, this::buildSimpleFields)));
        collector.put(String.format("java/%s/mapper/%sMapper.java", packagePath, table.getClassName()),
            renderFromTemplate("templates/mapper.java.tpl", buildBindings(basePackage + ".mapper", table,
                pkColumn, columns, null)));
        collector.put(String.format("resources/mapper/%s/%sMapper.xml", table.getModuleName(), table.getClassName()),
            renderFromTemplate("templates/mapper.xml.tpl", buildBindings(basePackage, table, pkColumn, columns,
                this::buildMapperResultMap)));
        collector.put(String.format("java/%s/service/I%sService.java", packagePath, table.getClassName()),
            renderFromTemplate("templates/service.java.tpl", buildBindings(basePackage + ".service", table,
                pkColumn, columns, null)));
        collector.put(String.format("java/%s/service/impl/%sServiceImpl.java", packagePath, table.getClassName()),
            renderFromTemplate("templates/serviceImpl.java.tpl", buildBindings(basePackage + ".service.impl", table,
                pkColumn, columns, null)));
        collector.put(String.format("java/%s/controller/%sController.java", packagePath, table.getClassName()),
            renderFromTemplate("templates/controller.java.tpl", buildBindings(basePackage + ".controller", table,
                pkColumn, columns, null)));
    }

    /**
     * 将生成的文件映射压缩为 zip 字节数组，供下载接口使用。
     *
     * @param files 生成的文件映射
     * @return 压缩后的字节数组
     */
    public byte[] zip(Map<String, String> files) {
        try (var outputStream = new java.io.ByteArrayOutputStream();
             var zipOutputStream = new ZipOutputStream(outputStream)) {
            for (Map.Entry<String, String> entry : files.entrySet()) {
                zipOutputStream.putNextEntry(new ZipEntry(entry.getKey()));
                zipOutputStream.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
                zipOutputStream.closeEntry();
            }
            zipOutputStream.finish();
            return outputStream.toByteArray();
        } catch (IOException e) {
            return new byte[]{};
        }
    }

    private Map<String, String> buildBindings(String pkg, GenTable table, GenTableColumn pkColumn,
                                              List<GenTableColumn> columns,
                                              java.util.function.Function<List<GenTableColumn>, String> fieldBuilder) {
        Map<String, String> bindings = new HashMap<>();
        bindings.put("package", pkg);
        bindings.put("servicePackage", pkg.replace(".controller", ".service")
            .replace(".service.impl", ".service"));
        bindings.put("className", table.getClassName());
        bindings.put("classNameLower", StrUtil.lowerFirst(table.getClassName()));
        bindings.put("tableComment", StrUtil.emptyToDefault(table.getTableComment(), table.getTableName()));
        bindings.put("moduleName", table.getModuleName());
        bindings.put("businessName", table.getBusinessName());
        bindings.put("functionName", table.getFunctionName());
        bindings.put("author", table.getFunctionAuthor());
        bindings.put("pkType", pkColumn != null ? pkColumn.getJavaType() : "Long");
        bindings.put("pkField", pkColumn != null ? pkColumn.getJavaField() : "id");
        bindings.put("entityPackage", pkg.replace(".controller", ".domain")
            .replace(".service.impl", ".domain")
            .replace(".service", ".domain"));
        bindings.put("mapperPackage", pkg.replace(".service.impl", ".mapper")
            .replace(".service", ".mapper"));
        bindings.put("boPackage", pkg.replace(".controller", ".domain.bo")
            .replace(".service.impl", ".domain.bo")
            .replace(".service", ".domain.bo"));
        bindings.put("voPackage", pkg.replace(".controller", ".domain.vo")
            .replace(".service.impl", ".domain.vo")
            .replace(".service", ".domain.vo"));
        if (fieldBuilder != null) {
            bindings.put("fields", fieldBuilder.apply(columns));
        }
        return bindings;
    }

    private String buildEntityFields(List<GenTableColumn> columns) {
        StringBuilder builder = new StringBuilder();
        for (GenTableColumn column : columns) {
            builder.append("    /** ").append(StrUtil.emptyToDefault(column.getColumnComment(), column.getColumnName()))
                .append(" */").append(NEW_LINE);
            builder.append("    private ").append(column.getJavaType()).append(" ")
                .append(column.getJavaField()).append(";").append(NEW_LINE).append(NEW_LINE);
        }
        return builder.toString();
    }

    private String buildSimpleFields(List<GenTableColumn> columns) {
        return buildEntityFields(columns);
    }

    private String buildMapperResultMap(List<GenTableColumn> columns) {
        if (CollUtil.isEmpty(columns)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (GenTableColumn column : columns) {
            builder.append("    <result column=\"")
                .append(column.getColumnName())
                .append("\" property=\"")
                .append(column.getJavaField())
                .append("\"/>")
                .append(NEW_LINE);
        }
        return builder.toString();
    }

    private String renderFromTemplate(String resourcePath, Map<String, String> bindings) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            String raw = FileCopyUtils.copyToString(new InputStreamReader(resource.getStream(), StandardCharsets.UTF_8));
            String rendered = raw;
            for (Map.Entry<String, String> entry : bindings.entrySet()) {
                rendered = StrUtil.replace(rendered, "${" + entry.getKey() + "}", entry.getValue());
            }
            return rendered;
        } catch (IOException e) {
            return "";
        }
    }
}
