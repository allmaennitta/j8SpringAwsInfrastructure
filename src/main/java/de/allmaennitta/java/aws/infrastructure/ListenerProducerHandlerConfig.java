package de.allmaennitta.java.aws.infrastructure;

import de.allmaennitta.java.aws.infrastructure.config.main.AbstractHandler;
import de.allmaennitta.java.aws.infrastructure.config.main.Listener;
import de.allmaennitta.java.aws.infrastructure.config.main.Producer;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import de.allmaennitta.dummy.javaws.shared.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;

/**
 * This ListenerProducerHandlerConfig is just a template to show how configuration is
 * implemented. It's not intended for direct use.
 */
@Deprecated

public class ListenerProducerHandlerConfig {

  @Autowired
  private QueueMessagingTemplate messagingTemplate;

  @Autowired
  private SimpleMessageListenerContainerFactory listenerContainerFactory;

  @Autowired
  private QueueMessageHandler queueMessageHandler;

  @Autowired
  private AbstractHandler protoHandler;

  @Autowired
  private TrackingService trackingService;

  @Value("${QUEUES.in}")
  private String inQueue;

  @Value("${QUEUES.out}")
  private String outQueue;

  @Bean
  public Listener<TestModel> getListener() {
    return new Listener<TestModel>(inQueue, protoHandler, TestModel.class);
  }

  @Bean
  private Producer<TestModel> getProducer() {
    return new Producer<>(outQueue, messagingTemplate, trackingService);
  }

  /*
   * Must be defined here to be executed after ThreadExecutor is initalized.
   */
  @Bean
  public SimpleMessageListenerContainer simpleMessageListenerContainer() {
    SimpleMessageListenerContainer listenerContainer =
        listenerContainerFactory.createSimpleMessageListenerContainer();
    listenerContainer.setMessageHandler(queueMessageHandler);
    return listenerContainer;
  }
}
