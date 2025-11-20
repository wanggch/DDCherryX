package cn.ddcherry.framework.mybatis.datascope;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据权限规则参数。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataScopeRule {

    private String deptAlias;

    private String userAlias;

    private boolean ignoreUser;
}
