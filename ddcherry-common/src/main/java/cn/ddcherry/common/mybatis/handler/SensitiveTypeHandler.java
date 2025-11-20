package cn.ddcherry.common.mybatis.handler;

import cn.ddcherry.common.mybatis.sensitive.SensitiveFieldRegistry;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 敏感字段 TypeHandler：
 * <ul>
 *     <li>插入、更新时保持原值；</li>
 *     <li>查询时根据注册的脱敏规则输出掩码结果。</li>
 * </ul>
 */
@MappedTypes(String.class)
@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.CHAR})
public class SensitiveTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, parameter);
        } else {
            ps.setObject(i, parameter, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return SensitiveFieldRegistry.desensitize(columnName, rs.getString(columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String column = rs.getMetaData().getColumnLabel(columnIndex);
        return SensitiveFieldRegistry.desensitize(column, rs.getString(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String column = cs.getMetaData().getColumnName(columnIndex);
        return SensitiveFieldRegistry.desensitize(column, cs.getString(columnIndex));
    }
}
