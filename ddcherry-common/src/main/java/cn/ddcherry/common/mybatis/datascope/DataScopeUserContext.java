package cn.ddcherry.common.mybatis.datascope;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限上下文，用于保存当前用户可访问的部门和用户标识。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataScopeUserContext {

    /**
     * 当前用户ID。
     */
    private Long userId;

    /**
     * 可访问的部门ID集合。
     */
    @Builder.Default
    private List<Long> deptIds = new ArrayList<>();
}
