package de.allmaennitta.java.aws.infrastructure.config.main;


import static de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService.REQUEST_ID_PATTERN;
import static de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService.SESSION_ID_PATTERN;

import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.Timespans;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import de.allmaennitta.java.aws.infrastructure.config.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.core.MessagePostProcessor;

/**
 * Ongoing best practice implementation of a SQS-Producer which just converts processed instances into appropriate json.
 * Dependency Injection should wire the parts and leave this class as non-knowing as possible
 *
 * @param <RES> the desired conversion type of incoming json-payloads
 */
public class Producer<RES> {
  @Autowired
  Timespans timespans;

  private static final Logger LOG = LoggerFactory.getLogger(Producer.class);
  private final QueueMessagingTemplate template;
  private final String outQueue;
  private TrackingService tracker;
  protected MessagePostProcessor messagePostProcessor = message -> {
    if (null != this.tracker) {
      String payload = message.getPayload().toString();
      this.tracker.setSessionId(Utils.getId(SESSION_ID_PATTERN, payload));
      this.tracker.setRequestId(Utils.getId(REQUEST_ID_PATTERN, payload));
      this.tracker.trackString("Send-Message", "Payload", "JSON", payload);
    }
    return message;
  };

  /**
   * Producer to serialize objects as String into SQS queue
   *
   * @param tracker can be NULL, e.g. for the tracking producer to avoid an infinite regress //TODO add appropriate @Nullable
   * annotation to TrackingService
   */
  public Producer(String outQueue, QueueMessagingTemplate template, TrackingService tracker) {
    this.outQueue = outQueue;
    this.template = template;
    this.tracker = tracker;
  }


  protected void setPostProcessor(MessagePostProcessor postProcessor) {
    this.messagePostProcessor = postProcessor;
  }

  /**
   * Marshalls the instance of the given type as JSON into the given queue The DebugMessagePostProcessor enables the
   * examination of the de facto sent message
   */
  public void produce(RES res) {
    LOG.debug("Producing in Queue: " + outQueue);
    template.convertAndSend(outQueue, res, this.messagePostProcessor);
    if (this.tracker != null) { // => Message, statt Tracking-Producer
      timespans.notifySent(System.currentTimeMillis());
    }
  }

}

