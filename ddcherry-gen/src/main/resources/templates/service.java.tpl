package ${package};

import java.util.List;
import ${voPackage}.${className}Vo;
import ${boPackage}.${className}Bo;

/**
 * ${functionName} 业务接口
 */
public interface I${className}Service {

    List<${className}Vo> list();

    ${className}Vo detail(${pkType} id);

    boolean save(${className}Bo bo);

    boolean update(${className}Bo bo);

    boolean remove(${pkType} id);
}
