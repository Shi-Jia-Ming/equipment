//package com.medical.equipment.config;
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//@Slf4j
//public class MyMetaObjectHandler implements MetaObjectHandler {
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        try {
//            log.info("start insert field....");
//            this.setFieldValByName("writeTime", new Date(),metaObject);
//            this.setFieldValByName("modifyTime", new Date(),metaObject);
//            this.setFieldValByName("executor", RolePermissionUtil.getRolePermission().getUsername(),metaObject);
//            this.setFieldValByName("isEnabled", true,metaObject);
//            this.setFieldValByName("executorId", RolePermissionUtil.getRolePermission().getOperatorId(),metaObject);
//        }catch (Exception e){
//            log.error(e.getMessage());
//
//        }
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        try {
//            this.setFieldValByName("modifyTime", new Date(),metaObject);
//            this.setFieldValByName("executor", RolePermissionUtil.getRolePermission().getUsername(),metaObject);
//            this.setFieldValByName("executorId", Long.parseLong(RolePermissionUtil.getRolePermission().getOperatorId()+""),metaObject);
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//    }
//
//    @Override
//    public void selectFill(MetaObject metaObject) {
//
//    }
//
//}