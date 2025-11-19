package cn.ddcherry.gen.service.impl;

import cn.ddcherry.gen.service.GenService;
import cn.ddcherry.gen.template.TemplateRenderer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Placeholder implementation for code generation preview.
 */
@Service
public class GenServiceImpl implements GenService {

    private final TemplateRenderer templateRenderer;

    public GenServiceImpl(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @Override
    public Map<String, String> preview(Map<String, Object> config) {
        Map<String, String> previews = new HashMap<>();
        previews.put("entity.java", templateRenderer.render("entity", config));
        previews.put("mapper.xml", templateRenderer.render("mapperXml", config));
        return previews;
    }
}
