package de.allmaennitta.java.aws.infrastructure.config.aws.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Timespans {
  private static final Logger LOG = LoggerFactory.getLogger(Timespans.class);
  private Long receiveTimeMillis;

  @Autowired
  CloudWatchMetrics metrics;


  public void notifyReceived(long receiveTimeMillis) {
    if (null != this.receiveTimeMillis) {
      LOG.warn("MessageReceived hasn't been reset to null. That should not happen.");
    }
    this.receiveTimeMillis = receiveTimeMillis;
  }

  public long notifySent(long sentTimeMillis) {
    if (null == receiveTimeMillis) {
      LOG.warn("MessageSent notified before MessageReceived notified. That should not happen.");
    }
    long duration = sentTimeMillis - this.receiveTimeMillis;
    metrics.putRESPONSE_TIME(duration);

    this.receiveTimeMillis = null;
    return duration;
  }
}
