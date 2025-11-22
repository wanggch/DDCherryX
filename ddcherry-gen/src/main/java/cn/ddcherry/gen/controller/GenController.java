package cn.ddcherry.gen.controller;

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

}
