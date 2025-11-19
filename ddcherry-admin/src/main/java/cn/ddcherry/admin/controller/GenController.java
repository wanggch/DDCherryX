package cn.ddcherry.admin.controller;

import cn.ddcherry.common.result.Result;
import cn.ddcherry.gen.service.GenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @PostMapping("/preview")
    public Result<Map<String, String>> preview(@RequestBody Map<String, Object> config) {
        return Result.success(genService.preview(config));
    }
}
