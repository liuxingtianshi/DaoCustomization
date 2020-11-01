package com.zs.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//注解可保留到程序运行时并被加载到 JVM 中
@Target(ElementType.TYPE)//作用于类····
public @interface AnnotationClass {
	String  value();
}