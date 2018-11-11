package de.allmaennitta.java.aws.infrastructure.config.tracking;

import de.allmaennitta.java.aws.infrastructure.config.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrackingMessage {

  private static final Logger LOG = LoggerFactory.getLogger(TrackingMessage.class);

  private long timestamp = System.currentTimeMillis();

  private String serviceName;
  private String action;
  private String sessionId;
  private String requestId;
  private String contentType;
  private String title;
  private String bodyZ;
  private String body;

  /*private*/ TrackingMessage(String sessionId, String requestId, String title, String action, String serviceName) {
    this.sessionId = sessionId;
    this.requestId = requestId;
    this.title = title;
    this.action = action;
    this.serviceName = serviceName;
  }

  public void setZippedBodyOrErrorMessage(String bodyToZip, String contentType) {
    this.setBodyZ(Utils.zipString(bodyToZip));
    this.setContentType(contentType);
  }

  public String getRequestId() {
    return requestId;
  }

  public TrackingMessage setRequestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

  public String getBodyZ() {
    return bodyZ;
  }

  private TrackingMessage setBodyZ(String bodyZ) {
    this.bodyZ = bodyZ;
    return this;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {

    this.body = body;
  }

}
