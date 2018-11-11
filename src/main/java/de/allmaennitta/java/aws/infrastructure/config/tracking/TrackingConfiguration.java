package de.allmaennitta.java.aws.infrastructure.config.tracking;

import de.allmaennitta.java.aws.infrastructure.config.main.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Beans necessary for tracking messages in the tracking queue
 * depends on
 * - AwsSqsConfiguration
 */
@Configuration
public class TrackingConfiguration {

  @Autowired
  private QueueMessagingTemplate queueMessagingTemplate;

  @Value("${SERVICE.name:not_yet_set_in_properties}")
  private String serviceName;

  @Bean
  public Producer<TrackingMessage> trackingProducer() {
    return new Producer<TrackingMessage>("tracker-service", queueMessagingTemplate, null);
  }

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public TrackingService trackingService() {
    return new TrackingService(serviceName, trackingProducer());
  }

  @Bean
  public ListenerAspect listenerAspect(TrackingService trackingService) {
    return new ListenerAspect(trackingService);
  }
}
