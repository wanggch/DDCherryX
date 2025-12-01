package cn.ddcherry.gen.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 简单表元数据模型。
 */
@Data
public class TableMetadata {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述/注释
     */
    private String tableComment;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
