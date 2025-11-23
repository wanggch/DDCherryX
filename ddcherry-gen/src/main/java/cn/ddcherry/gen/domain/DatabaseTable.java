package cn.ddcherry.gen.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据库物理表的精简视图，用于代码生成导入与选择。
 */
@Data
public class DatabaseTable {

    /**
     * 数据表名称。
     */
    private String tableName;

    /**
     * 数据表注释/描述。
     */
    private String tableComment;

    /**
     * 数据表创建时间。
     */
    private LocalDateTime createTime;
}
