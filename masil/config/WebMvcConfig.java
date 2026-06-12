package com.example.masil.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor  // ← 추가
public class WebMvcConfig implements WebMvcConfigurer {

    private final StringToCategoryConverter stringToCategoryConverter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/video_files/**")
                .addResourceLocations("file:///C:/meta12/masil/videos/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/meta12/masil/category_images/");
        registry.addResourceHandler("/thumbnails/**")
                .addResourceLocations("file:///C:/meta12/masil/thumbnails/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToCategoryConverter);
    }
}