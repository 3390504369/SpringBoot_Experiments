package com.example.experiment04.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//Documented注解表明这个注释是由 javadoc记录的，在默认情况下也有类似的记录工具。
// 如果一个类型声明被注释了文档化，它的注释成为公共API的一部分。
@Documented
//声明注解的保留期限
@Retention(RUNTIME)
//声明可以使用该注解的目标类型
@Target({ TYPE, METHOD })
//定义权限注解默认类型
public @interface MyInterceptor {
    //声明注解成员，自定义注解
    AuthorityType[] value() default AuthorityType.USER;
    ///某组件未作说明时所有方法具有默认user权限，
    public static enum AuthorityType {
        USER, ADMIN, SUPERADMIN;
    }
    //自定义权限类型，
    //同时支持单独声明方法权限
}

