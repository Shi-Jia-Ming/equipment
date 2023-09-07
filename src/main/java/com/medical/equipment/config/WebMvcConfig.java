package com.medical.equipment.config;

import com.medical.equipment.annotation.AuthCheck1;
import com.medical.equipment.aop.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png","/**/*.jpg","/**/*.jpeg");
    }



    // TODO     经过分析发现由于项目中有配置注解类（@Configuration）继承了WebMvcConfigurationSupport，导致默认的Swagger静态资源被覆盖，而缺失了配置。可在该继承配置类中，显式添加如下swagger静态资源：
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加Swagger静态资源映射
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        super.addResourceHandlers(registry);
    }

    /*
    * @Override
    * protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    *     registry.addResourceHandler("swagger-ui.html")
    *             .addResourceLocations("classpath:/META-INF/resources/");
    *     registry.addResourceHandler("/webjars/**")
    *             .addResourceLocations("classpath:/META-INF/resources/webjars");
    *     super.addResourceHandlers(registry);
    * }
    * 静态资源添加的顺序不可以颠倒。
    * 这样的顺序确保通用的`/webjars/**`映射不会匹配Swagger UI的请求，而较为具体的Swagger UI映射可以正常加载Swagger UI的资源。
    * 如果顺序颠倒，通用的`/webjars/**`映射会匹配所有以`/webjars/`开头的请求，包括Swagger UI的请求。结果是Swagger UI的请求也会被通用映射匹配到，导致Swagger UI无法正确加载。
    */


}
