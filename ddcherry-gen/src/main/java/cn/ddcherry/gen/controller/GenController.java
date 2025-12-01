package cn.ddcherry.gen.controller;

import cn.ddcherry.common.result.Result;
import cn.ddcherry.gen.domain.GenTableColumn;
import cn.ddcherry.gen.domain.TableMetadata;
import cn.ddcherry.gen.service.GenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller bridging admin module and generator services.
 */
@RestController
@RequestMapping("/api/gen")
public class GenController {

    private final GenService genService;

    public GenController(GenService genService) {
        this.genService = genService;
    }

    /**
     * 查询当前数据库的表列表
     *
     * @return 表信息列表
     */
    @GetMapping("/tables")
    public Result<List<TableMetadata>> listTables() {
        return Result.success(genService.listTables());
    }

    /**
     * 查询指定表的字段列表
     *
     * @param tableName 数据表名
     * @return 字段列表
     */
    @GetMapping("/tables/{tableName}/columns")
    public Result<List<GenTableColumn>> listColumns(@PathVariable String tableName) {
        return Result.success(genService.listTableColumns(tableName));
    }

}
