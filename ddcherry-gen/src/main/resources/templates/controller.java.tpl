package ${package};

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import cn.ddcherry.common.result.Result;
import ${servicePackage}.I${className}Service;
import ${voPackage}.${className}Vo;
import ${boPackage}.${className}Bo;
import jakarta.validation.Valid;

/**
 * ${functionName} 控制器
 */
@Validated
@RestController
@RequestMapping("/api/${businessName}")
public class ${className}Controller {

    private final I${className}Service ${classNameLower}Service;

    public ${className}Controller(I${className}Service ${classNameLower}Service) {
        this.${classNameLower}Service = ${classNameLower}Service;
    }

    @GetMapping
    public Result<List<${className}Vo>> list() {
        return Result.success(${classNameLower}Service.list());
    }

    @GetMapping("/{${pkField}}")
    public Result<${className}Vo> detail(@PathVariable ${pkType} ${pkField}) {
        return Result.success(${classNameLower}Service.detail(${pkField}));
    }

    @PostMapping
    public Result<Boolean> save(@Valid @RequestBody ${className}Bo bo) {
        return Result.success(${classNameLower}Service.save(bo));
    }

    @PutMapping
    public Result<Boolean> update(@Valid @RequestBody ${className}Bo bo) {
        return Result.success(${classNameLower}Service.update(bo));
    }

    @DeleteMapping("/{${pkField}}")
    public Result<Boolean> remove(@PathVariable ${pkType} ${pkField}) {
        return Result.success(${classNameLower}Service.remove(${pkField}));
    }
}
