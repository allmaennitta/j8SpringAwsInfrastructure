package de.allmaennitta.java.aws.infrastructure;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import de.allmaennitta.java.aws.infrastructure.config.JsonConfiguration;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.AwsMetricsConfiguration;
import de.allmaennitta.java.aws.infrastructure.config.aws.sqs.AwsSqsConfiguration;
import de.allmaennitta.java.aws.infrastructure.config.main.StatusConfiguration;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

/**
 * This Application Class is just a template to show how configuration is
 * implemented. It's not intended for use.
 */
@Deprecated
@Configuration
@ComponentScan
@Import({
    AwsSqsConfiguration.class,
    JsonConfiguration.class,

    TrackingConfiguration.class,
    StatusConfiguration.class,
    AwsMetricsConfiguration.class,

    ListenerProducerHandlerConfig.class
})
@EnableSqs
@EnableAspectJAutoProxy //MUSS IN DER Application-Class dringend gesetzt werden, sonst kein Tracking onReceive!
public class Application {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  @Autowired
  AmazonSQSAsync amazonSQS;

  @Bean
  @Primary
  /**
   * Overwrite deep-within set SimpleMessageListenerContainerFactory to
   * have central values like queueVisibility at an easily visible place
   */
  public SimpleMessageListenerContainerFactory listenerContainerFactory() {
    SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
    factory.setAmazonSqs(amazonSQS);
    factory.setAutoStartup(true);
    factory.setMaxNumberOfMessages(1);
    factory.setWaitTimeOut(20);
    factory.setVisibilityTimeout(60);
    return factory;
  }

  public static void main(String[] args) {
    System.getProperties().setProperty("aws.profile", "tfm-develop");
    ApplicationContext ctx = SpringApplication.run(Application.class, args);
  }
}