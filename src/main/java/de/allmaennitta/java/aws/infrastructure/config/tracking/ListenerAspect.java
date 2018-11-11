package de.allmaennitta.java.aws.infrastructure.config.tracking;

import static de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService.REQUEST_ID_PATTERN;
import static de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService.SESSION_ID_PATTERN;

import de.allmaennitta.java.aws.infrastructure.config.utils.Utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageHeaders;

@Aspect
public class ListenerAspect {

  private static final Logger LOG = LoggerFactory.getLogger(ListenerAspect.class);
  private final TrackingService tracker;


  public ListenerAspect(TrackingService tracker) {
    this.tracker = tracker;
  }

  @Pointcut("@annotation(org.springframework.cloud.aws.messaging.listener.annotation.SqsListener)")
  public void methodWithListenerAnnotationFullPath() {
  }

  @Before("methodWithListenerAnnotationFullPath() && args(payload, headers)")
  public void setSessionInfos(JoinPoint joinPoint, String payload, MessageHeaders headers) {
    LOG.debug("AOP successful! " + joinPoint.toLongString() + "with Payload " + payload);
    this.tracker.setSessionId(Utils.getId(SESSION_ID_PATTERN, payload));
    this.tracker.setRequestId(Utils.getId(REQUEST_ID_PATTERN, payload));
    this.tracker.trackString("Receive-Response", "Payload", "JSON", payload);
  }

}

