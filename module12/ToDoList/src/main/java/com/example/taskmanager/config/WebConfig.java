package com.example.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // Static Resource Config
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Css resource
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/WEB-INF/resources/css/").setCachePeriod(0);
        //Scripawts resource
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

    // Thymeleaf template resolver serving HTML 5
    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configure) {
        configure.enable();
    }
}