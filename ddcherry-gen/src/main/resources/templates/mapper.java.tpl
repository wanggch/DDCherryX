package ${package};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ${entityPackage}.${className};

/**
 * ${functionName} 数据访问层
 */
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}> {
}
