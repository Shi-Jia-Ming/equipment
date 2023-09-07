package com.medical.equipment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan("com.medical.equipment.mapper")
@EnableTransactionManagement
@EnableScheduling
public class EquipmentApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EquipmentApplication.class, args);
//        run.getBean(SocketServer.class).start();//在spring容器启动后，取到已经初始化的SocketServer，启动Socket服务

    }

}
