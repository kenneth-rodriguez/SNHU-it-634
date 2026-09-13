package com.kenRodriguez.flightswebapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// MVC "map" to establish URL endpoints. goof around with HTML templates to add pages.
@Configuration
class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
    }
}
