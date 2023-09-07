package com.medical.equipment.constant;

import com.medical.equipment.entity.IntelligentMattressEntity;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java8通过Function函数获取字段名称(获取实体类的字段名称)
 * @see ColumnUtil 使用示例
 */
public class ColumnUtil {

    /**
     * 使Function获取序列化能力
     */
    @FunctionalInterface
    public interface SFunction<T, R> extends Function<T, R>, Serializable {
    }

    /**
     * 字段名注解,声明表字段
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TableField {
        String value() default "";
    }

    //默认配置
    static String defaultSplit = "";
    static Integer defaultToType = 0;

    /**
     * 获取实体类的字段名称(实体声明的字段名称)
     */
    public static <T> String getFieldName(SFunction<T, ?> fn) {
        return getFieldName(fn, defaultSplit);
    }

    /**
     * 获取实体类的字段名称
     *
     * @param split 分隔符，多个字母自定义分隔符
     */
    public static <T> String getFieldName(SFunction<T, ?> fn, String split) {
        return getFieldName(fn, split, defaultToType);
    }

    /**
     * 获取实体类的字段名称
     *
     * @param split  分隔符，多个字母自定义分隔符
     * @param toType 转换方式，多个字母以大小写方式返回 0.不做转换 1.大写 2.小写
     */
    public static <T> String getFieldName(SFunction<T, ?> fn, String split, Integer toType) {
        SerializedLambda serializedLambda = getSerializedLambda(fn);

        // 从lambda信息取出method、field、class等
        String fieldName = serializedLambda.getImplMethodName().substring("get".length());
        fieldName = fieldName.replaceFirst(fieldName.charAt(0) + "", (fieldName.charAt(0) + "").toLowerCase());
        Field field;
        try {
            field = Class.forName(serializedLambda.getImplClass().replace("/", ".")).getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        // 从field取出字段名，可以根据实际情况调整
        TableField tableField = field.getAnnotation(TableField.class);
        if (tableField != null && tableField.value().length() > 0) {
            return tableField.value();
        } else {

            //0.不做转换 1.大写 2.小写
            switch (toType) {
                case 1:
                    return fieldName.replaceAll("[A-Z]", split + "$0").toUpperCase();
                case 2:
                    return fieldName.replaceAll("[A-Z]", split + "$0").toLowerCase();
                default:
                    return fieldName.replaceAll("[A-Z]", split + "$0");
            }

        }

    }

    private static <T> SerializedLambda getSerializedLambda(SFunction<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);
        return serializedLambda;
    }


    /**
     * 测试用户实体类
     */
    public static class TestUserDemo implements Serializable {

        private static final long serialVersionUID = 1L;

        private String loginName;
        private String name;
        private String companySimpleName;

        @ColumnUtil.TableField("nick")
        private String nickName;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public static long getSerialVersionUID() {
            return serialVersionUID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompanySimpleName() {
            return companySimpleName;
        }

        public void setCompanySimpleName(String companySimpleName) {
            this.companySimpleName = companySimpleName;
        }
    }


//     /**
//      * 参考示例
//      */
//     public static void main(String[] args) {
//
//         //实体类原字段名称返回
//         System.out.println();
//         System.out.println("实体类原字段名称返回");
//         System.out.println("字段名：" + ColumnUtil.getFieldName(TestUserDemo::getLoginName));
//         System.out.println("字段名：" + ColumnUtil.getFieldName(TestUserDemo::getNickName));
//         System.out.println("字段名：" + ColumnUtil.getFieldName(TestUserDemo::getCompanySimpleName));
//
//         System.out.println();
//         System.out.println("实体类字段名称增加分隔符");
//         System.out.println("字段名：" + ColumnUtil.getFieldName(TestUserDemo::getCompanySimpleName, "_"));
//
//         System.out.println();
//         System.out.println("实体类字段名称增加分隔符 + 大小写");
//         System.out.println("字段名：" + ColumnUtil.getFieldName(TestUserDemo::getCompanySimpleName, "_", 0));
//         System.out.println("字段名：" + ColumnUtil.getFieldName(TestUserDemo::getCompanySimpleName, "_", 1));
//         System.out.println("字段名：" + ColumnUtil.getFieldName(TestUserDemo::getCompanySimpleName, "_", 2));
//
//
//     }

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static <T> String modelNameToLine(SFunction<T, ?> fn) {
        return humpToLine(getFieldName(fn));
    }


//    public static void main(String[] args) {
//        String s = humpToLine(getFieldName(IntelligentMattressEntity::getRespiratoryRate));
//        System.out.println(s);
//    }


}