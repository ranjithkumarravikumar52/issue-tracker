package issuetracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Aspect
@Component
public class IssueTrackerLoggingAspect {
    //setup logger
    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Pointcut declarations for controller, service, DAO
     */


    //controller
    @Pointcut("execution(* issuetracker.controller.*.*(..))")
    private void forControllerPackage() {
    }

    //service
    @Pointcut("execution(* issuetracker.service.*.*(..))")
    private void forServicePackage() {
    }

    //DAO
    //service
    @Pointcut("execution(* issuetracker.dao.*.*(..))")
    private void forDAOPackage() {
    }


    //special combine
    @Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
    private void forAppFlow() {
    }

    //add @Before
    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        //display method we are calling
        String methodSignature = joinPoint.getSignature().toShortString();
        logger.info("====> in @Before: calling method: " + methodSignature);

        //display the arguments to the method
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info("====> arguments: " + arg.toString());
        }

    }

    //add @AfterReturning
    @AfterReturning(pointcut = "forAppFlow()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        //display method we are returning from
        String methodSignature = joinPoint.getSignature().toShortString();
        logger.info("====> in @AfterReturning: from method: " + methodSignature);

        //display data we are returning
        logger.info( "=====> result: " + result);
    }


}
