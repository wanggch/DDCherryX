package cn.ddcherry.gen.mapper;

import cn.ddcherry.gen.domain.GenTableColumn;
import cn.ddcherry.gen.domain.TableMetadata;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper for generator metadata.
 */
@Mapper
public interface GenTableMapper {

    /**
     * 查询指定表的字段列表
     *
     * @param tableName 数据表名
     * @return 字段集合
     */
    @Select({
        "SELECT",
        "    column_name   AS columnName,",
        "    column_comment AS columnComment,",
        "    column_type   AS columnType,",
        "    CASE WHEN column_key = 'PRI' THEN '1' ELSE '0' END AS isPk,",
        "    CASE WHEN is_nullable = 'NO' THEN '1' ELSE '0' END AS isRequired,",
        "    CASE WHEN extra = 'auto_increment' THEN '1' ELSE '0' END AS isIncrement",
        "FROM information_schema.columns",
        "WHERE table_schema = (SELECT DATABASE())",
        "  AND table_name = #{tableName}",
        "ORDER BY ordinal_position"
    })
    List<GenTableColumn> selectTableColumns(String tableName);

    /**
     * 查询当前数据库的所有表及元数据
     *
     * @return 表信息集合
     */
    @Select({
        "SELECT",
        "    table_name    AS tableName,",
        "    table_comment AS tableComment,",
        "    create_time   AS createTime,",
        "    update_time   AS updateTime",
        "FROM information_schema.tables",
        "WHERE table_schema = (SELECT DATABASE())",
        "ORDER BY table_name"
    })
    List<TableMetadata> selectTables();
}
