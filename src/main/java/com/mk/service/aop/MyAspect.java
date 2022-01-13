package com.mk.service.aop;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
    @Pointcut("execution(* com.mk.service.aop.UserService.*(..)))")
    public void pointCut() {

    }

//    @Before(value = "execution(public * com.mk.service.aop.UserService.*(java.lang.String)))")
    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("Before");
    }
    @After(value = "execution(* com.mk.service.aop.UserService.*(..)))")
    public void after(JoinPoint joinPoint) {
        System.out.println("After");
    }
    @Around("pointCut()  && args (arg1,arg0)")
    public Object around(ProceedingJoinPoint  joinPoint,String arg1,String arg0) throws Throwable {
        Object result = null;

            System.out.println("环绕前置增强");
            result = joinPoint.proceed();

        System.out.println("环绕后置增强");
        return result;
    }
    @AfterReturning(value = "pointCut() && args(a,b)",returning="r")
    public void afterReturning(JoinPoint jp,String a,String b,Object r) {
        System.out.println("returning增强");
    }

//    @AfterThrowing(value = "pointCut()",throwing = "e"))
    @AfterThrowing(value = "execution(* com.mk.service.aop.UserService.*(..))  && args (arg1,arg0)",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,String arg0,String arg1,Exception e){
        System.out.println("Throw增强");
    }
}
