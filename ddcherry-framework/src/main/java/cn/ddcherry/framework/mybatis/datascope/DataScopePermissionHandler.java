package cn.ddcherry.framework.mybatis.datascope;

import cn.ddcherry.common.mybatis.datascope.DataScopeUserContextHolder;
import cn.ddcherry.common.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据权限处理器，基于 MyBatis-Plus {@link com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor} 实现。
 */
@Component
public class DataScopePermissionHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        DataScopeRule rule = DataScopeContext.get();
        if (rule == null) {
            return null;
        }
        return buildExpression(rule);
    }

    private Expression buildExpression(DataScopeRule rule) {
        List<Long> deptIds = Optional.ofNullable(DataScopeUserContextHolder.currentDeptIds()).orElse(List.of());
        Long userId = DataScopeUserContextHolder.currentUserId();
        Expression deptExpression = buildDeptExpression(rule.getDeptAlias(), deptIds);
        Expression userExpression = buildUserExpression(rule, userId);
        if (deptExpression != null && userExpression != null) {
            return new OrExpression(deptExpression, userExpression);
        }
        if (deptExpression != null) {
            return deptExpression;
        }
        return userExpression;
    }

    private Expression buildDeptExpression(String columnAlias, List<Long> deptIds) {
        if (StringUtils.isBlank(columnAlias) || deptIds == null || deptIds.isEmpty()) {
            return null;
        }
        List<Expression> idExpressions = deptIds.stream().map(LongValue::new).collect(Collectors.toList());
        ExpressionList expressionList = new ExpressionList(idExpressions);
        return new InExpression(new Column(columnAlias), expressionList);
    }

    private Expression buildUserExpression(DataScopeRule rule, Long userId) {
        if (rule.isIgnoreUser() || StringUtils.isBlank(rule.getUserAlias()) || userId == null) {
            return null;
        }
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new Column(rule.getUserAlias()));
        equalsTo.setRightExpression(new LongValue(userId));
        return equalsTo;
    }
}
