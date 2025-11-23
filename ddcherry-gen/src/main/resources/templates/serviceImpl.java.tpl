package ${package};

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import ${entityPackage}.${className};
import ${mapperPackage}.${className}Mapper;
import ${boPackage}.${className}Bo;
import ${voPackage}.${className}Vo;
import ${servicePackage}.I${className}Service;

/**
 * ${functionName} 业务实现
 */
@Service
public class ${className}ServiceImpl implements I${className}Service {

    private final ${className}Mapper mapper;

    public ${className}ServiceImpl(${className}Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<${className}Vo> list() {
        return mapper.selectList(null).stream().map(this::toVo).collect(Collectors.toList());
    }

    @Override
    public ${className}Vo detail(${pkType} id) {
        ${className} entity = mapper.selectById(id);
        return entity == null ? null : toVo(entity);
    }

    @Override
    public boolean save(${className}Bo bo) {
        ${className} entity = new ${className}();
        BeanUtils.copyProperties(bo, entity);
        return mapper.insert(entity) > 0;
    }

    @Override
    public boolean update(${className}Bo bo) {
        ${className} entity = new ${className}();
        BeanUtils.copyProperties(bo, entity);
        return mapper.updateById(entity) > 0;
    }

    @Override
    public boolean remove(${pkType} id) {
        return mapper.deleteById(id) > 0;
    }

    private ${className}Vo toVo(${className} entity) {
        ${className}Vo vo = new ${className}Vo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
