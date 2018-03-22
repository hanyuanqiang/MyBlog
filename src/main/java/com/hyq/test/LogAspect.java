package com.hyq.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.hyq.demo.*.*(..))")
    public void aspect(){};

    @Before("aspect()")
    public void before(JoinPoint jp){
        System.out.println("----------- start ------------");
        System.out.println(jp.getTarget().getClass().getName());    //获得类名
        System.out.println(jp.getSignature().getName());    //获得方法名
        System.out.println("调用方法前处理！");
        System.out.println("----------- end ------------");

    }

    @AfterThrowing(throwing="ex",pointcut = "aspect()")
    public void  afterThrowing(Throwable ex){
        System.out.println("----------start-----------");
        System.out.println("异常信息:"+ex.getMessage());
        System.out.println("----------- end ------------");

    }


}
