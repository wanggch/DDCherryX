package cn.ddcherry.gen.template;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Placeholder template renderer.
 */
@Component
public class TemplateRenderer {

    public String render(String templateName, Map<String, Object> params) {
        return "// render logic for " + templateName;
    }
}
