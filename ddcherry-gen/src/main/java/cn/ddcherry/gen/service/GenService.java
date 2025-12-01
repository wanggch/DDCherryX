package cn.ddcherry.gen.service;

import cn.ddcherry.gen.domain.GenTableColumn;
import cn.ddcherry.gen.domain.TableMetadata;

import java.util.List;
import java.util.Map;

/**
 * Placeholder service for code generation.
 */
public interface GenService {
    Map<String, String> preview(Map<String, Object> config);

    /**
     * 查询指定数据表的字段列表
     *
     * @param tableName 数据表名
     * @return 字段列表
     */
    List<GenTableColumn> listTableColumns(String tableName);

    /**
     * 查询当前数据库的表列表
     *
     * @return 表信息集合
     */
    List<TableMetadata> listTables();
}
