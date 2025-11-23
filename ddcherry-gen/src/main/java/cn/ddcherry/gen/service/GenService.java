package cn.ddcherry.gen.service;

import cn.ddcherry.gen.domain.DatabaseTable;
import cn.ddcherry.gen.domain.GenTable;
import cn.ddcherry.gen.domain.GenTableColumn;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 代码生成的应用服务层。
 */
public interface GenService {

    /**
     * 预览指定业务表的代码生成结果。
     *
     * @param tableId 业务表主键
     * @return 文件路径-内容映射
     */
    Map<String, String> preview(Long tableId);

    /**
     * 查询指定数据表的字段列表。
     *
     * @param tableName 数据表名
     * @return 字段列表
     */
    List<GenTableColumn> listTableColumns(String tableName);

    /**
     * 分页查询已配置的业务表。
     *
     * @param pageNumber 页码
     * @param pageSize   每页数量
     * @return 业务表分页结果
     */
    Page<GenTable> pageGenTables(long pageNumber, long pageSize);

    /**
     * 根据主键查询业务表配置以及字段列表。
     *
     * @param tableId 业务表主键
     * @return 表信息与字段信息
     */
    GenTable getGenTable(Long tableId);

    /**
     * 查询数据库中未导入到代码生成的物理表清单。
     *
     * @return 未导入的表列表
     */
    List<DatabaseTable> listUnimportedTables();

    /**
     * 保存或更新业务表配置。
     *
     * @param table 业务表及字段配置
     */
    void saveGenTable(GenTable table);

    /**
     * 生成指定业务表的代码压缩包。
     *
     * @param tableId 业务表主键
     * @return 代码压缩包字节流
     */
    byte[] download(Long tableId);
}
