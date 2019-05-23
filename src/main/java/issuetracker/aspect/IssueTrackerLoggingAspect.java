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

	private Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Pointcut declarations for controller, service, DAO
	 */


	/**
	 * for controller
	 */
	@Pointcut("execution(* issuetracker.controller.*.*(..))")
	private void forControllerPackage() {
	}

	/**
	 * for classes in service
	 */
	@Pointcut("execution(* issuetracker.service.*.*(..))")
	private void forServicePackage() {
	}

	/**
	 * for classes in repository
	 */
	@Pointcut("execution(* issuetracker.repository.*.*(..))")
	private void forDAOPackage() {
	}


	/**
	 * for classes in controller, service and repository
	 */
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {
	}

	//add @Before
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		//display method we are calling
		String methodSignature = joinPoint.getSignature().toShortString();
		logger.info("====> Calling method: " + methodSignature);

		//display the arguments to the method
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			logger.info("====> Arguments: " + arg.toString());
		}

	}

	//add @AfterReturning
	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		//display method we are returning from
		String methodSignature = joinPoint.getSignature().toShortString();
		logger.info("<==== Returning from method: " + methodSignature);

		//display data we are returning
		logger.info("<===== Resultant data from returning method: " + result);
	}


}
