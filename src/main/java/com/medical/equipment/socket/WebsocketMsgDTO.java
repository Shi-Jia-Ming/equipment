//package com.medical.equipment.socket;
//
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
///**
// * 消息模版
// * @author AnYuan
// */
//
//
//public class WebsocketMsgDTO {
//
//    /**
//     * 发送消息用户
//     */
//    private String uid;
//    /**
//     * 接收消息用户
//     */
//    private String toUId;
//    /**
//     * 消息内容
//     */
//    private String content;
//    /**
//     * 消息时间
//     */
//    private String dateTime;
//    /**
//     * 用户列表
//     */
//    private List<String> onlineUser;
//
//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public String getToUId() {
//        return toUId;
//    }
//
//    public void setToUId(String toUId) {
//        this.toUId = toUId;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(String dateTime) {
//        this.dateTime = dateTime;
//    }
//
//    public List<String> getOnlineUser() {
//        return onlineUser;
//    }
//
//    public void setOnlineUser(List<String> onlineUser) {
//        this.onlineUser = onlineUser;
//    }
//
//    /**
//     * 统一消息模版
//     * @param uid 发送消息用户
//     * @param content 消息内容
//     * @param onlineUser 在线用户列表
//     */
//    public WebsocketMsgDTO(String uid, String content, List<String> onlineUser) {
//        this.uid = uid;
//        this.content = content;
//        this.onlineUser = onlineUser;
//        this.dateTime = localDateTimeToString();
//    }
//
//
//    /**
//     * 获取当前时间
//     * @return String 12:00:00
//     */
//    private String localDateTimeToString() {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        return dateTimeFormatter.format( LocalDateTime.now());
//    }
//}