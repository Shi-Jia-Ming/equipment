package com.medical.equipment.utils;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @title: PasswordUtil
 * @projectName equipment
 * @description:
 */
public class  PasswordUtil {
    //传一个字符串过来经过md5处理返回一个字符串
    public static String md5(String s) {
        try {
            //MessageDigest是封装md5算法的工具对象还支持SHA算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            //通过digest拿到的任意字符串,得到的bytes都是等长的
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            //这里输出的都是乱码
//	        System.out.println(new String(bytes));
            //返回的toHex通过下面方法再处理
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        //把toHex的字符串把二进制转换成十六进制
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        //循环判断是为了补位操作
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    public static String salt() {
        //使用UUID通用唯一识别码,取第一个-前面的值
        UUID uuid = UUID.randomUUID();
        String[] arr = uuid.toString().split("-");
        return arr[0];
    }


}
