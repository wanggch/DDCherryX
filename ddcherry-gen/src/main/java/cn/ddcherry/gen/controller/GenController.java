package cn.ddcherry.gen.controller;

import cn.ddcherry.common.result.Result;
import cn.ddcherry.gen.domain.DatabaseTable;
import cn.ddcherry.gen.domain.GenTable;
import cn.ddcherry.gen.domain.GenTableColumn;
import cn.ddcherry.gen.domain.TableMetadata;
import cn.ddcherry.gen.service.GenService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 代码生成模块对外接口。
 */
@RestController
@RequestMapping("/api/gen")
public class GenController {

    private final GenService genService;

    public GenController(GenService genService) {
        this.genService = genService;
    }

    /**
     * 获取已配置的业务表分页列表。
     *
     * @param pageNum  页码，默认 1
     * @param pageSize 每页条数，默认 10
     * @return 业务表分页数据
     */
    @GetMapping("/tables")
    public Result<Page<GenTable>> pageGenTables(@RequestParam(defaultValue = "1") long pageNum,
                                                @RequestParam(defaultValue = "10") long pageSize) {
        return Result.success(genService.pageGenTables(pageNum, pageSize));
    }

    /**
     * 查询指定表的字段列表。
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

    /**
     * 根据主键获取业务表详情，包含字段信息。
     *
     * @param tableId 表主键
     * @return 表详情
     */
    @GetMapping("/tables/detail/{tableId}")
    public Result<GenTable> getGenTable(@PathVariable Long tableId) {
        return Result.success(genService.getGenTable(tableId));
    }

    /**
     * 查询尚未导入代码生成的物理表清单。
     *
     * @return 数据库中未导入的表列表
     */
    @GetMapping("/tables/unimported")
    public Result<List<DatabaseTable>> listUnimportedTables() {
        return Result.success(genService.listUnimportedTables());
    }

    /**
     * 保存或更新生成配置。
     *
     * @param table 业务表配置
     * @return 操作结果
     */
    @PostMapping("/tables")
    public Result<Void> saveGenTable(@Valid @RequestBody GenTable table) {
        genService.saveGenTable(table);
        return Result.success(null);
    }

    /**
     * 预览指定表的生成结果。
     *
     * @param tableId 业务表主键
     * @return 文件路径与内容映射
     */
    @GetMapping("/tables/{tableId}/preview")
    public Result<Map<String, String>> preview(@PathVariable Long tableId) {
        return Result.success(genService.preview(tableId));
    }

    /**
     * 下载生成好的代码压缩包。
     *
     * @param tableId 业务表主键
     * @return zip 文件流
     */
    @GetMapping("/tables/{tableId}/download")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Long tableId) {
        byte[] data = genService.download(tableId);
        GenTable table = genService.getGenTable(tableId);
        String filename = (table != null ? table.getTableName() : "code") + ".zip";
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + java.net.URLEncoder.encode(filename, StandardCharsets.UTF_8))
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(new ByteArrayResource(data));
    }
}
