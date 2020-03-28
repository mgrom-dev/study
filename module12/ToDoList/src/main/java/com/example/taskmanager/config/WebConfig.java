package com.example.taskmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // Static Resource Config
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Css resource
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/WEB-INF/resources/css/").setCachePeriod(0);
        //Scripts resource
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/resources/js/").setCachePeriod(0);
        //Images resource
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/WEB-INF/resources/images/").setCachePeriod(0);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("WEB-INF/pages/", ".html");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configure) {
        configure.enable();
    }
}