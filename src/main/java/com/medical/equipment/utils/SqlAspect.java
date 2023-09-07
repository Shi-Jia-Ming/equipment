package com.medical.equipment.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.*;

@Aspect
@Component
public class SqlAspect {
    @Resource(name = "sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Pointcut("@annotation(com.medical.equipment.utils.Sql)")
    public void switchStockDataSource() {
    }

    @Around("switchStockDataSource()")
    public Object switchOrderDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        // 参数值
        Object[] args = joinPoint.getArgs();
        updateTableName(joinPoint, sqlSessionFactory);

        return joinPoint.proceed();
    }

    public void updateTableName(ProceedingJoinPoint joinPoint, SqlSessionFactory sessionFactory) throws NoSuchFieldException, IllegalAccessException {
        //1.获取namespace+methdoName
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String namespace = method.getDeclaringClass().getName();
        String methodName = method.getName();
        //2.根据namespace+methdoName获取相对应的MappedStatement
        Configuration configuration = sessionFactory.getConfiguration();
        MappedStatement mappedStatement = configuration.getMappedStatement(namespace+"."+methodName);


        Object[] objects = joinPoint.getArgs(); //获取实参

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Map<String,Object> map = new HashMap<>();

        for (int i = 0;i<parameterAnnotations.length;i++){
            Object object = objects[i];
            if (parameterAnnotations[i].length == 0){ //说明该参数没有注解，此时该参数可能是实体类，也可能是Map，也可能只是单参数
                if (object.getClass().getClassLoader() == null && object instanceof Map){
                    map.putAll((Map<? extends String, ?>) object);
                    System.out.println("该对象为Map");
                }else{//形参为自定义实体类
                    map.putAll(objectToMap(object));
                    System.out.println("该对象为用户自定义的对象");
                }
            }else{//说明该参数有注解，且必须为@Param
                for (Annotation annotation : parameterAnnotations[i]){
                    if (annotation instanceof Param){
                        map.put(((Param) annotation).value(),object);
                    }
                }
            }
        }



//        // 获得 private final SqlSource sqlSource; 对象
//        Field sqlSource = FieldUtils.getField(mappedStatement.getClass(), "sqlSource", true);
//        sqlSource.setAccessible(true);
//        // 动态数据源
//        DynamicSqlSource staticSqlSource = (DynamicSqlSource) sqlSource.get(mappedStatement);
//        // 包含sql
//        BoundSql boundSql = staticSqlSource.getBoundSql(null);
//        // 修改sql
//        Field sqlSourceField = mappedStatement.getClass().getDeclaredField("sqlSource");//反射
//        sqlSourceField.setAccessible(true);
//        // boundSql.getSql() 获取执行的sql
//        System.out.println( "sql:"+ boundSql.getSql());

        BoundSql boundSql = mappedStatement.getBoundSql(map);
        String s = showSql(configuration, boundSql);

        System.out.println(s);
        if (s.contains("GROUP")) {
            String group = StringUtils.substringAfter(s, "GROUP");
            System.out.println(group);

        }
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

//        sqlSourceField.set(mappedStatement, new StaticSqlSource(mappedStatement.getConfiguration(),
//                // 修改sql中的表名
//                updateTableName(boundSql.getSql(), "新表名"),
//                boundSql.getParameterMappings()));
//        Object[] args = joinPoint.getArgs();
//        FieldUtils.writeDeclaredField(args[0], "sub_num", "增加值", true);
    }
    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    private static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 解析BoundSql，生成不含占位符的SQL语句
     * @param configuration
     * @param boundSql
     * @return
     */
    private  static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    String[] s =  metaObject.getObjectWrapper().getGetterNames();
                    s.toString();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 若为字符串或者日期类型，则在参数两边添加''
     * @param obj
     * @return
     */
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }
}
