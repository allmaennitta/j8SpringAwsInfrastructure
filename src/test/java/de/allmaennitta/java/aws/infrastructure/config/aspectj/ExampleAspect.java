package de.allmaennitta.java.aws.infrastructure.config.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExampleAspect {

  private static final Logger LOG = LoggerFactory.getLogger(ExampleAspect.class);

  @Pointcut("@annotation(LogExecutionTime)")
  public void methodWithListenerAnnotationSimple() {
  }

  @Pointcut("execution(@LogExecutionTime * *(..))")
  public void methodWithListenerAnnotationFullPath() {
  }

  @Pointcut("execution(* MyServer.serve(..))")
  public void methodWithPackageDescription() {
  }

  @Around("methodWithListenerAnnotationFullPath() || methodWithListenerAnnotationSimple() || methodWithPackageDescription()")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();

    Object proceed = joinPoint.proceed();

    long executionTime = System.currentTimeMillis() - start;

    LOG.warn(joinPoint.getSignature() + " executed in " + executionTime + "ms");

    return proceed;
  }

}