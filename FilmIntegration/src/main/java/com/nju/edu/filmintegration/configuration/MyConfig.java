package com.nju.edu.filmintegration.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: peng
 * @Date: 2019/5/16 10:19
 * @Description:
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("forward:/html/index.html");

        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        WebMvcConfigurer.super.addViewControllers(registry);

    }

}