package cn.ddcherry.gen.mapper;

import cn.ddcherry.gen.domain.DatabaseTable;
import cn.ddcherry.gen.domain.GenTable;
import cn.ddcherry.gen.domain.GenTableColumn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.ddcherry.gen.domain.TableMetadata;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper for generator metadata and database table information.
 */
@Mapper
public interface GenTableMapper extends BaseMapper<GenTable> {

    /**
     * 查询指定表的字段列表。
     *
     * @param tableName 数据表名
     * @return 字段集合
     */
    List<GenTableColumn> selectTableColumns(String tableName);

    /**
     * 查询未导入代码生成的业务表。
     *
     * @return 数据库中尚未存在于 gen_table 表的表清单
     */
    List<DatabaseTable> selectUnimportedTables();
     * 查询当前数据库的所有表及元数据
     *
     * @return 表信息集合
     */
    List<TableMetadata> selectTables();
}
