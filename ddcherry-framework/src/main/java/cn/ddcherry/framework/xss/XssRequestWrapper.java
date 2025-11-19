package cn.ddcherry.framework.xss;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.web.util.HtmlUtils;

/**
 * Simple wrapper that escapes parameters.
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return value == null ? null : HtmlUtils.htmlEscape(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        String[] escaped = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            escaped[i] = HtmlUtils.htmlEscape(values[i]);
        }
        return escaped;
    }
}
