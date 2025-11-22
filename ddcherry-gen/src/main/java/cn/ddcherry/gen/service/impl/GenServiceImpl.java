package cn.ddcherry.gen.service.impl;

import cn.ddcherry.gen.constant.GenConstants;
import cn.ddcherry.gen.domain.GenTableColumn;
import cn.ddcherry.gen.mapper.GenTableMapper;
import cn.ddcherry.gen.service.GenService;
import cn.ddcherry.gen.template.TemplateRenderer;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Placeholder implementation for code generation preview.
 */
@Service
public class GenServiceImpl implements GenService {

    private final TemplateRenderer templateRenderer;
    private final GenTableMapper genTableMapper;

    public GenServiceImpl(TemplateRenderer templateRenderer, GenTableMapper genTableMapper) {
        this.templateRenderer = templateRenderer;
        this.genTableMapper = genTableMapper;
    }

    @Override
    public Map<String, String> preview(Map<String, Object> config) {
        Map<String, String> previews = new HashMap<>();
        previews.put("entity.java", templateRenderer.render("entity", config));
        previews.put("mapper.xml", templateRenderer.render("mapperXml", config));
        return previews;
    }

    @Override
    public List<GenTableColumn> listTableColumns(String tableName) {
        List<GenTableColumn> columns = genTableMapper.selectTableColumns(tableName);
        columns.forEach(this::prepareColumnDefaults);
        return columns;
    }

    private void prepareColumnDefaults(GenTableColumn column) {
        String dataType = StrUtil.nullToEmpty(StrUtil.subBefore(column.getColumnType(), "(", false)).toLowerCase();
        column.setJavaField(StrUtil.toCamelCase(column.getColumnName()));
        column.setJavaType(resolveJavaType(dataType));
        column.setHtmlType(resolveHtmlType(dataType));
        column.setIsInsert(GenConstants.REQUIRE);
        column.setIsEdit(GenConstants.REQUIRE);
        column.setIsList(GenConstants.REQUIRE);
        column.setIsQuery(column.isPk() ? "0" : GenConstants.REQUIRE);
        column.setQueryType(resolveQueryType(dataType));
    }

    private String resolveJavaType(String dataType) {
        if (contains(GenConstants.COLUMNTYPE_STR, dataType) || contains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
            return GenConstants.TYPE_STRING;
        }
        if (contains(GenConstants.COLUMNTYPE_TIME, dataType)) {
            return GenConstants.TYPE_DATE;
        }
        if (contains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
            return GenConstants.TYPE_BIGDECIMAL;
        }
        return GenConstants.TYPE_STRING;
    }

    private String resolveHtmlType(String dataType) {
        if (contains(GenConstants.COLUMNTYPE_TIME, dataType)) {
            return GenConstants.HTML_DATETIME;
        }
        if (contains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
            return GenConstants.HTML_TEXTAREA;
        }
        return GenConstants.HTML_INPUT;
    }

    private String resolveQueryType(String dataType) {
        return contains(GenConstants.COLUMNTYPE_STR, dataType) ? GenConstants.QUERY_LIKE : GenConstants.QUERY_EQ;
    }

    private boolean contains(String[] targets, String dataType) {
        for (String target : targets) {
            if (StrUtil.equalsIgnoreCase(target, dataType)) {
                return true;
            }
        }
        return false;
    }
}
