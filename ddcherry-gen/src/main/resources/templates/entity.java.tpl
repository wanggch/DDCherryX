package ${package};

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * ${tableComment}
 */
@Data
public class ${className} implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

${fields}}
