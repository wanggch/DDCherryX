package cn.ddcherry.gen.service.impl;

import cn.ddcherry.gen.constant.GenConstants;
import cn.ddcherry.gen.domain.DatabaseTable;
import cn.ddcherry.gen.domain.GenTable;
import cn.ddcherry.gen.domain.GenTableColumn;
import cn.ddcherry.gen.mapper.GenTableColumnMapper;
import cn.ddcherry.gen.mapper.GenTableMapper;
import cn.ddcherry.gen.service.GenService;
import cn.ddcherry.gen.template.TemplateRenderer;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * 代码生成核心服务的默认实现。
 */
@Service
public class GenServiceImpl implements GenService {

    private final TemplateRenderer templateRenderer;
    private final GenTableMapper genTableMapper;
    private final GenTableColumnMapper genTableColumnMapper;

    public GenServiceImpl(TemplateRenderer templateRenderer, GenTableMapper genTableMapper,
                          GenTableColumnMapper genTableColumnMapper) {
        this.templateRenderer = templateRenderer;
        this.genTableMapper = genTableMapper;
        this.genTableColumnMapper = genTableColumnMapper;
    }

    @Override
    public Map<String, String> preview(Long tableId) {
        GenTable table = getGenTable(tableId);
        Map<String, String> previews = new HashMap<>();
        if (table == null) {
            return previews;
        }
        templateRenderer.renderAll(previews, table);
        return previews;
    }

    @Override
    public List<GenTableColumn> listTableColumns(String tableName) {
        List<GenTableColumn> columns = genTableMapper.selectTableColumns(tableName);
        IntStream.range(0, columns.size()).forEach(index -> columns.get(index).setSort(index + 1));
        columns.forEach(this::prepareColumnDefaults);
        return columns;
    }

    @Override
    public Page<GenTable> pageGenTables(long pageNumber, long pageSize) {
        return genTableMapper.selectPage(new Page<>(pageNumber, pageSize), new LambdaQueryWrapper<>());
    }

    @Override
    public GenTable getGenTable(Long tableId) {
        if (tableId == null) {
            return null;
        }
        GenTable table = genTableMapper.selectById(tableId);
        if (table == null) {
            return null;
        }
        List<GenTableColumn> columns = genTableColumnMapper.selectList(new LambdaQueryWrapper<GenTableColumn>()
            .eq(GenTableColumn::getTableId, tableId)
            .orderByAsc(GenTableColumn::getSort));
        table.setColumns(columns);
        table.setPkColumn(determinePkColumn(columns));
        return table;
    }

    @Override
    public List<DatabaseTable> listUnimportedTables() {
        return genTableMapper.selectUnimportedTables();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGenTable(GenTable table) {
        if (table == null) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        if (table.getTableId() == null) {
            table.setCreateTime(now);
            genTableMapper.insert(table);
        } else {
            table.setUpdateTime(now);
            genTableMapper.updateById(table);
            genTableColumnMapper.delete(new LambdaQueryWrapper<GenTableColumn>().eq(GenTableColumn::getTableId, table.getTableId()));
        }

        List<GenTableColumn> columns = Optional.ofNullable(table.getColumns()).orElseGet(ArrayList::new);
        for (int i = 0; i < columns.size(); i++) {
            GenTableColumn column = columns.get(i);
            column.setTableId(table.getTableId());
            if (column.getSort() == null) {
                column.setSort(i + 1);
            }
            genTableColumnMapper.insert(column);
        }
    }

    @Override
    public byte[] download(Long tableId) {
        Map<String, String> generatedFiles = preview(tableId);
        return templateRenderer.zip(generatedFiles);
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

    private GenTableColumn determinePkColumn(List<GenTableColumn> columns) {
        if (CollUtil.isEmpty(columns)) {
            return null;
        }
        return columns.stream().filter(GenTableColumn::isPk).findFirst().orElse(columns.get(0));
    }

    private String resolveJavaType(String dataType) {
        if (contains(GenConstants.COLUMNTYPE_STR, dataType) || contains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
            return GenConstants.TYPE_STRING;
        }
        if (contains(GenConstants.COLUMNTYPE_TIME, dataType)) {
            return GenConstants.TYPE_DATE;
        }
        if (contains(GenConstants.COLUMNTYPE_INTEGER, dataType)) {
            return GenConstants.TYPE_INTEGER;
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
