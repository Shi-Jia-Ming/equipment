package com.medical.equipment.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Calendar;
import java.util.List;

public class VerifyParamsUtils {

    /**
     * @param br
     * @return com.sins.service_manage.common.utils.ResponseResult
     * @throws
     * @title verifyParams
     * @description 参数检查
     */
    public static R verifyParams(BindingResult br) {
        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<ObjectError> allErrors = br.getAllErrors();
            for (int i = 0; i < allErrors.size(); i++) {
                sb.append(allErrors.get(i).getDefaultMessage());
                if (i != allErrors.size() - 1) {
                    sb.append("|");
                }
            }
            return R.error(sb.toString());
        } else {
            return R.ok();
        }
    }


    /**
     * 检查文件格式是否符合规范
     *
     * @param fileType
     * @return
     */
    public static boolean checkFileType(String fileType) {
        boolean flag = false;
        switch (fileType) {
            case "doc":
            case "xls":
            case "xlsx":
            case "ppt":
            case "pdf":
            case "zip":
            case "txt":
            case "mp3":
            case "jpg":
            case "gif":
            case "png":
            case "apk":
            case "mp4":
                flag = true;
                break;
            default:
                flag = false;
                break;
        }

        return flag;
    }

    /**
     * 构建文件路径
     *
     * @param fileType 文件类型
     * @return
     */
    public static String createFileUrl(String fileType) {
        StringBuffer sbf = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        sbf.append("/" + year + "/" + month + "/" + day);

        switch (fileType) {
            case "doc":
            case "xls":
            case "xlsx":
            case "ppt":
            case "pdf":
            case "txt":
                sbf.append("/text");
                break;
            case "zip":
            case "rar":
            case "7z":
                sbf.append("/zip");
                break;
            case "mp3":
                sbf.append("/video");
                break;
            case "jpg":
            case "gif":
            case "png":
                sbf.append("/img");
                break;
            case "apk":
                sbf.append("/apk");
                break;
            default:
                sbf.append("/other");
                break;
        }

        return sbf.toString();
    }

    /**
     * 构建文件路径2（按照文件来源分类）
     *
     * @param source 文件源（app，平台，查缉布控）
     * @return
     */
    public static String createFileUrl2(String source) {
        StringBuffer sbf = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        sbf.append("/" + source + "/" + year + "/" + month + "/" + day);
        return sbf.toString();
    }

    /**
     * 判断是否是图片格式
     *
     * @param fileType
     * @return
     */
    public static boolean isImg(String fileType) {
        String s = upper2Lower(fileType);
        boolean flag = false;
        switch (s) {
            case "jpg":
            case "gif":
            case "png":
            case "jpeg":
            case "pcx":
            case "psd":
            case "tiff":
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }

    /**
     * 判断是否是文本格式
     *
     * @param fileType
     * @return
     */
    public static boolean isText(String fileType) {
        String s = upper2Lower(fileType);
        boolean flag = false;
        switch (s) {
            case "txt":
            case "doc":
            case "docx":
            case "ppt":
            case "pptx":
            case "xls":
            case "xlsx":
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }

    /**
     * 判断是否是视频文件
     *
     * @param fileType
     * @return
     */
    public static boolean isVideo(String fileType) {
        String s = upper2Lower(fileType);
        boolean flag = false;
        switch (s) {
            case "avi":
            case "mov":
            case "rmvb":
            case "rm":
            case "flv":
            case "mp4":
            case "3gp":
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }


    /**
     * 将字符串中的大写字母转换为小写
     *
     * @param str
     * @return
     */
    public static String upper2Lower(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串中的小写字母转换为大写
     *
     * @param str
     * @return
     */
    public static String lower2Upper(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isLowerCase(c)) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

}
