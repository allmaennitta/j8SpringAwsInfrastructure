package de.allmaennitta.java.aws.infrastructure.config.tracking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.allmaennitta.java.aws.infrastructure.config.main.Producer;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TrackingService {

  private static final Logger LOG = LoggerFactory.getLogger(TrackingService.class);
  private static final String CONTENT_TYPE = "JSON";

  //Regexp for extraction of session- and request-ID from message-payload
  public static final Pattern SESSION_ID_PATTERN = Pattern.compile(".*\"sessionId\"\\s?:\\s?\"(.*?)\",.*");
  public static final Pattern REQUEST_ID_PATTERN = Pattern.compile(".*\"requestId\"\\s?:\\s?\"(.*?)\",.*");

  private final String serviceName;
  private final Producer<TrackingMessage> trackingProducer;

  private String sessionId;
  private String requestId;

  @Autowired
  private ObjectMapper mapper;

  public TrackingService(String serviceName, Producer<TrackingMessage> producer) {
    this.serviceName = serviceName;
    this.trackingProducer = producer;
  }

  public <T> void trackJson(String action, String title, T bodyObject) {
    TrackingMessage message = new TrackingMessage(this.sessionId, this.requestId, title, action, this.serviceName);
    String body;

    try {
      body = mapper.writeValueAsString(bodyObject);
    } catch (JsonProcessingException e) {
      message.setBody("Objekt " + bodyObject.getClass().getSimpleName() + "lässt sich nicht zu Json konvertieren.");
      trackingProducer.produce(message);
      return;
    }
    message.setZippedBodyOrErrorMessage(body, "JSON");
    trackingProducer.produce(message);
  }

  public void trackString(String action, String title, String contentType, String body) {
    TrackingMessage message = new TrackingMessage(this.sessionId, this.requestId, title, action, this.serviceName);
    message.setZippedBodyOrErrorMessage(body, contentType);
    trackingProducer.produce(message);
  }

  public void trackString(String action, String title, String body) {
    TrackingMessage message = new TrackingMessage(this.sessionId, this.requestId, title, action, this.serviceName);
    message.setBody(body);
    //TODO
    message.setContentType("xml");
    trackingProducer.produce(message);
  }

  public String getSessionId() {
    return sessionId;
  }

  public TrackingService setSessionId(String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

  public String getRequestId() {
    return requestId;
  }

  public TrackingService setRequestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * Tests if message-body is bigger than the maximum allowed sqs-queue-size Not needed right now, but conserved, because
   * calculation might still prove useful
   *
   * @param body of message
   * @return true if message-body is bigger than queue-limit
   */
  private boolean isBiggerThanMaxQueueSize(String body) {
    final long MAX_QUEUE_SIZE = 262144;
    final int BYTES_PER_UTF_8_CHAR = 16;
    final double CONVERSION_FACTOR_TO_BITS = 0.125;

    return (body.toCharArray().length * BYTES_PER_UTF_8_CHAR * CONVERSION_FACTOR_TO_BITS > MAX_QUEUE_SIZE);
  }
}
