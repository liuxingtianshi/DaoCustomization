package com.zs.framework.util;

import com.zs.framework.annotation.AnnotationClass;
import com.zs.framework.annotation.AnnotationField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    /**
     * 根据类名获取表名
     *
     * @param clazz 类名
     */

    public static String getTableName(Class<?> clazz) {
        // 获取类名上的注解,如果有注解就是注解的值，否则就是表名就是类名
        AnnotationClass annotationClass = clazz.getAnnotation(AnnotationClass.class);
        if (annotationClass != null) {
            return annotationClass.value();
        } else {
            // 获取类名，不是类带路径的全名
            return clazz.getSimpleName();
        }
    }

    /**
     * 根据对象获取表名
     */
    public static <T> String getTableNameByObject(T t) {
        Class<?> clazz = t.getClass();
        return getTableName(clazz);
    }

    /**
     * 根据成员变量名拼接方法
     *
     * @param FieldName  成员变量名
     * @param purposeStr 要给成员名前加的方法前缀
     */
    public static String getMethodName(String FieldName, String purposeStr) {
        return purposeStr + upperFirstLetter(FieldName);
    }

    /**
     * 将一个字符串的首字母改成大写
     *
     * @param string
     * @return
     */
    public static String upperFirstLetter(String string) {
        Character oldChar = string.charAt(0);
        Character newChar = String.valueOf(oldChar).toUpperCase().charAt(0);
        String resultStr = string.replace(oldChar, newChar);
        return resultStr;
    }

    /**
     * 通过Field获取列名，有注解的成员变量名和列名不一致，无注解的成员变量名就是列名
     */
    public static String getColumnNameByField(Field field) {
        AnnotationField annotationField = field.getAnnotation(AnnotationField.class);
        if (annotationField != null) {
            return annotationField.value();
        } else {
            return field.getName();
        }
    }

    /**
     * 根据对象获取所有列名(除了id)
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<String> getAllColumnNamesByObject(T t) {
        List<String> columnNameList = new ArrayList<>();
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                columnNameList.add(getColumnNameByField(field));
            }
        }
        return columnNameList;
    }

    /**
     * 去掉StringBuffer中最后一个逗号
     *
     * @param buffer
     * @return 去掉逗号的stringBuffer
     */
    public static StringBuffer removeLastComma(StringBuffer buffer) {
        String str = buffer.toString();
        return new StringBuffer(str.substring(0, str.length() - 1));
    }

    /**
     * 根据对象的成员变量名获取对象成员变量的值集合（除了id）
     */
    public static <T> List<Object> getFieldValues(T t) {
        List<Object> objects = new ArrayList<>();
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String getterMethodStr = getMethodName(fieldName, "get");
            try {
                if (!fieldName.equals("id")) {
                    Method getterMethod = clazz.getDeclaredMethod(getterMethodStr);
                    Object object = getterMethod.invoke(t);
                    objects.add(object);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }
}