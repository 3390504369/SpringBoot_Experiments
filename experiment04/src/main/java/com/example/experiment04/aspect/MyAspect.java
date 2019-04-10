package com.example.experiment04.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
//声明切面类为容器组件，由容器扫描创建
/*1、@controller 控制器（注入服务）
        2、@service 服务（注入dao）
        3、@repository dao（实现dao访问）
        4、@component （把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
        @Component,@Service,@Controller,@Repository注解的类，并把这些类纳入进spring容器中管理*/
@Aspect
//声明为切面类
//声明切面类为组件，由容器扫描创建
public class MyAspect {

    @Around("execution(* com.example..*.buy*(..))")
    //                   路径          类型  方法
    //首要指示器，可声明切入点：路径，类型，方法，方法参数类型
    //，参数个数等的匹配表达式
    public Object calculateExecutionTime(ProceedingJoinPoint joinpoint) throws Throwable {
        //                               可作为方法参数直接注入
        long start = System.nanoTime();
        Object result = joinpoint.proceed();
        long end = System.nanoTime();
        log.debug("方法：{}()，的执行时间：{}", joinpoint.getSignature().getName(), end - start);
        return result;
    }

    //@Around环绕通知，在切入点方法执行前，或执行后执行，
    //可阻止目标方法的执行，替换参数等
    @Around("@within(myInterceptor) || @annotation(myInterceptor)")
    //@within(注解类型)”匹配所有持有指定注解类型内 ||   标注了某个注解的所有方法
    //@within仅支持声明切入点路径及切入点类型。。连接点类型包含指定注解
    // @annotation连接点方法包含指定注解
    public Object interecptorTarget(ProceedingJoinPoint joinpoint, MyInterceptor myInterceptor) throws Throwable {
        //  ProceedingJoinPoint接口，继承JoinPoint, 仅可注入around通知
        Optional.ofNullable(myInterceptor)
                .or(() -> {
                    MyInterceptor m =
                            joinpoint.getTarget().getClass().getAnnotation(MyInterceptor.class);
                    //获取连接点对象，获取连接点对象的类,获取注解对象
                    return Optional.of(m);
                })
                .ifPresent(m -> {
                    for (MyInterceptor.AuthorityType t : m.value()) {
                        //比较用户实际权限
                        log.debug("当前执行方法的权限：{}", t);
                    }
                });
        return joinpoint.proceed();
        //执行连接点方法并返回结果
    }
}
