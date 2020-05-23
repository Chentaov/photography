package com.cheng.photography.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class MyWebConfig implements WebMvcConfigurer
{
    //设置静态文件的目录
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("/**").addResourceLocations("file:/home/cct/IdeaProjects/photography/src/main/resources/static/");
        registry.addResourceHandler("/head_icon/**").addResourceLocations("file:/opt/uploaded_img/head_icon/");
        registry.addResourceHandler("/art_img/**").addResourceLocations("file:/opt/uploaded_img/art_img/");
        }
}
