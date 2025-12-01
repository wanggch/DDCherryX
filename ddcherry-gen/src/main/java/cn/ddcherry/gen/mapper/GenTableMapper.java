package cn.ddcherry.gen.mapper;

import cn.ddcherry.gen.domain.GenTableColumn;
import cn.ddcherry.gen.domain.TableMetadata;
import org.apache.ibatis.annotations.Mapper;

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
    List<GenTableColumn> selectTableColumns(String tableName);

    /**
     * 查询当前数据库的所有表及元数据
     *
     * @return 表信息集合
     */
    List<TableMetadata> selectTables();
}
