package de.allmaennitta.java.aws.infrastructure.config.main;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.CloudWatchMetrics;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.Timespans;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;

/**
 * Ongoing best practice implementation of a SQS-Listener which just converts incoming string messages into the desired class
 * type and passes this on to the handler, where the business logic happens.
 *
 * Dependency Injection should wire the parts and leave this class as non-knowing as possible
 *
 * @param <REQ> the desired conversion type of incoming json-payloads
 */
public class Listener<REQ> {

  private static final Logger LOG = LoggerFactory.getLogger(Listener.class);
  private AbstractHandler<REQ> handler;
  private Class<REQ> targetClass;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private Timespans timespans;

  @Autowired
  private CloudWatchMetrics metrics;

  @Autowired
  private TrackingService trackingService;

  @Value("${QUEUES.in}")
  private String inQueue;
  @Value("${aws.sqs.listener.logheaders:false}")
  private boolean logHeaders;

  public Listener() {
  }

  public Listener(String inQueue, AbstractHandler handler, Class<REQ> targetClass) {
    this.inQueue = inQueue;
    this.handler = handler;
    this.targetClass = targetClass;
  }

  @PostConstruct
  private void init() {
    LOG.debug("Starting to listen on: " + this.inQueue);
  }

  @SqsListener(value = "${QUEUES.in}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  /**
   * TODO: We need a ticket to examine changes for request-scope. Right now there is Singleton-Scope for all beans which
   * makes it difficult to pass variables from Consumer to Producer without being sure that it's synchronized
   *
   * In the non-generic way the not-annotated param of the message defines the targetClass in which the message payload is
   * marshalled. This doesn't work as soon as there is only type T.
   * More elegant ways to do the same are highly appreciated.
   */
  protected void consume(String payload, @Headers MessageHeaders headers) {
    REQ req;
    LOG.debug("Consuming from Queue: " + this.inQueue);
    timespans.notifyReceived(System.currentTimeMillis());
    metrics.putRECEIVED_MESSAGES();

    if (logHeaders) {
      for (String h : headers.keySet()) {
        LOG.info("key: " + h + " " + headers.get(h).toString());
      }
    }
    try {
      req = mapper.readValue(payload, this.targetClass);
    } catch (IOException e) {
      String message = String.format(
          "Parser-Error: The following string can't be parsed into %s:\n%s",
          this.targetClass.getSimpleName(),
          payload);
      LOG.error(message);
      metrics.putERRORS();
      trackingService.trackString("ABORT", "ABORT", "plain", message);
      return;
    }
    handler.handle(req);
    metrics.putDELETED_MESSAGES();
  }
}
