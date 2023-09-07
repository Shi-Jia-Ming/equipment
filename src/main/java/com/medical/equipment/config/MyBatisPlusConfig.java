package com.medical.equipment.config;


import com.github.pagehelper.PageInterceptor;
//import com.medical.equipment.aop.MybatisSqlInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {

    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }

//    @Bean
//    public MybatisSqlInterceptor mybatisSqlInterceptor(){
//        return  new MybatisSqlInterceptor();
//    }
}
