package cn.ddcherry.framework.config.p6spy;

import cn.ddcherry.common.utils.StringUtils;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * 自定义 p6spy SQL 输出格式。
 */
public class P6spySqlFormatStrategy implements MessageFormattingStrategy {

    private static volatile boolean enabled = true;
    private static volatile boolean formatSql = true;

    public static void configure(boolean enable, boolean pretty) {
        enabled = enable;
        formatSql = pretty;
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (!enabled || StringUtils.isBlank(sql) || Category.ERROR.getName().equals(category)) {
            return "";
        }
        String formattedSql = formatSql ? beautify(sql) : sql.replaceAll("\\s+", " ").trim();
        return String.format("%sms | %s | %s", elapsed, category, formattedSql);
    }

    private String beautify(String sql) {
        return sql.replaceAll("\\s+", " ")
            .replaceAll("(?i) select ", "\nSELECT ")
            .replaceAll("(?i) from ", "\nFROM ")
            .replaceAll("(?i) where ", "\nWHERE ")
            .replaceAll("(?i) order by ", "\nORDER BY ")
            .replaceAll("(?i) group by ", "\nGROUP BY ")
            .trim();
    }
}
