package com.medical.equipment.aop;

import com.medical.equipment.annotation.AuthCheck1;
import com.medical.equipment.annotation.AuthCheck2;
import com.medical.equipment.annotation.AuthCheck3;
import com.medical.equipment.entity.SystemUserEntity;
import com.medical.equipment.service.SystemUserService;
import org.apache.catalina.Session;
import org.springframework.beans.MethodInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    SystemUserService systemUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        int auth = 0;   //权限级别
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AuthCheck1 authCheck1 = method.getAnnotation(AuthCheck1.class);
            AuthCheck2 authCheck2 = method.getAnnotation(AuthCheck2.class);
            AuthCheck3 authCheck3 = method.getAnnotation(AuthCheck3.class);

            //查找加了注解的方法
            if(authCheck1 != null){
                //对应操作员权限才可访问的方法
                auth = 1;
            } else if(authCheck2 != null){
                //对应管理员权限可访问的方法
                auth = 2;
            }else if(authCheck3 != null){
                //对应超级管理员权限可访问的方法
                auth = 3;
            }else{
                return true;
            }
        }

        Integer userId;
        //获取cookie数组
        Cookie[] cookies = request.getCookies();
//        System.out.println(auth);
        if (cookies != null && cookies.length > 0) {
            //遍历数组
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    //能取到代表已登录
                    userId = Integer.parseInt(cookie.getValue());
                    //检查cookie是否与session一致,即登录是否一致
                    SystemUserEntity sessionSystemUserEntity = (SystemUserEntity)request.getSession().getAttribute("user");
                    if(userId != sessionSystemUserEntity.getId()){
                        return false;
                    }
                    //通过cookie的userId查询这个用户，查看他的权限
                    SystemUserEntity cookieSystemUserEntity = systemUserService.getById(userId);

                    System.out.println(cookieSystemUserEntity.getAuthority());
                    //检查权限级别是否符合方法(高级管理员也可以访问低级别权限方法)
                    if(cookieSystemUserEntity.getAuthority() < auth){
                        return false;
                    }
                    break;
                }
            }
        }else{
            //未登录，cookie为空
//            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        return true;
    }


}
