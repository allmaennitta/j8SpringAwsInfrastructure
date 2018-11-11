package de.allmaennitta.java.aws.infrastructure.config.aws.sqs;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

/**
 * Beans necessary for working with Amazon SQS
 * depends on
 * - AwsCliConfiguration
 * - JsonConfiguration
 */
@Configuration
public class AwsSqsConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(AwsSqsConfiguration.class);

  @Autowired
  private MappingJackson2MessageConverter messageConverter;


  @Bean
  public AmazonSQSAsync amazonSQS() {
    //LOG.warn("!!!Test des AWS-Loggings!!!");
    AmazonSQSAsync client = AmazonSQSAsyncClientBuilder.standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .build();

    if (LOG.isDebugEnabled()) {
      debugSQS(client);
    }
    return client;
  }

  private void debugSQS(AmazonSQSAsync client) {
    LOG.debug("Checking SQS and Queues...");
    ListQueuesResult result = client.listQueues();
    LOG.debug("Queue URLs");
    for (String url : result.getQueueUrls()) {
      LOG.debug(url);
    }
  }

  @Bean("messageHandler")
  public QueueMessageHandler queueMessageHandler() {
    PayloadArgumentResolver payloadArgumentResolver = new PayloadArgumentResolver(messageConverter);

    QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
    factory.setAmazonSqs(amazonSQS());
    factory.setArgumentResolvers(Collections.<HandlerMethodArgumentResolver>singletonList(payloadArgumentResolver));
    return factory.createQueueMessageHandler();
  }

  @Bean
  public QueueMessagingTemplate queueMessagingTemplate() {
    QueueMessagingTemplate queueMessagingTemplate = new QueueMessagingTemplate(amazonSQS());
    queueMessagingTemplate.setMessageConverter(messageConverter);
    return queueMessagingTemplate;
  }

  //https://github.com/spring-cloud/spring-cloud-aws/blob/5fde56ffaf1ff11a80ef99aa122142f2fc758387/spring-cloud-aws-messaging/src/main/java/org/springframework/cloud/aws/messaging/config/annotation/SqsConfiguration.java
  @Bean
  public SimpleMessageListenerContainerFactory listenerContainerFactory() {
    SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
    factory.setAmazonSqs(amazonSQS());
    factory.setAutoStartup(true);
    factory.setMaxNumberOfMessages(1);
    factory.setWaitTimeOut(20);
    factory.setVisibilityTimeout(30);
    return factory;
  }
}
