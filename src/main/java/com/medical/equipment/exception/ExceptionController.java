package com.medical.equipment.exception;

import com.medical.equipment.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(ErrorException.class)
    public R throwException(RuntimeException e) {
        e.printStackTrace();
        return R.error(e.getMessage());
    }




    @ExceptionHandler(BindException.class)
    public R handleValidException(BindException e) {

        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> errorMap = new HashMap<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        log.error("数据校验错误");
        return R.error("数据校验错误！").put("data", errorMap);
    }

    @ExceptionHandler(Exception.class)
    public R throwException(Exception e) {
        e.printStackTrace();
        return R.error("系统正在维护中！！！ 请稍后再试");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R MethodArgumentNotValidException(HttpRequestMethodNotSupportedException e){
        e.printStackTrace();
        log.error("请求方式错误");
        return R.error("请求方式错误").put("data",e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        log.error("没有入参");
        return R.error("没有入参").put("data",e.getMessage());
    }
}