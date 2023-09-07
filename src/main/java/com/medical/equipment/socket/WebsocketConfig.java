//package com.medical.equipment.socket;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//
///**
// * WebSocket服务配置
// * @author AnYuan
// */
//
//@Configuration
//public class WebsocketConfig {
//
//    /**
//     * 注入一个ServerEndpointExporter
//     * 该Bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint
//     */
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
//}