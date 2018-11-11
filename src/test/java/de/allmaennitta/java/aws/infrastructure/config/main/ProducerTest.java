package de.allmaennitta.java.aws.infrastructure.config.main;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.allmaennitta.java.aws.infrastructure.config.JsonConfiguration;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.CloudWatchMetrics;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.Timespans;
import de.allmaennitta.java.aws.infrastructure.config.main.ListenerTest.Config;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map.Entry;

import de.allmaennitta.dummy.javaws.shared.model.TestModel;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import({JsonConfiguration.class, Config.class})
public class ProducerTest {

  private static final Logger LOG = LoggerFactory.getLogger(ProducerTest.class);

  @TestConfiguration
  static class Config {

    @Autowired
    protected MappingJackson2MessageConverter messageConverter;

    @MockBean
    protected AmazonSQSAsync sqsClient;

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
      QueueMessagingTemplate queueMessagingTemplate = new QueueMessagingTemplate(sqsClient);
      queueMessagingTemplate.setMessageConverter(messageConverter);
      return queueMessagingTemplate;
    }

    @Bean
    public MessagePostProcessor messageAccessor() {
      return new MessagePostProcessor() {
        @Override
        public Message<?> postProcessMessage(Message<?> message) {
          return message;
        }
      };
    }

    @Bean
    protected Producer out() {
      Producer out = new Producer("foo", queueMessagingTemplate(), null);
      out.setPostProcessor(messageAccessor());
      return out;
    }

  }

  @Autowired
  protected AmazonSQSAsync sqsClient;

  @Autowired
  private Producer out;

  @Autowired
  ObjectMapper mapper;

  @SpyBean
  QueueMessagingTemplate template;

  @MockBean
  private
  CloudWatchMetrics metrics;

  @MockBean
  private Timespans timespans;

  @MockBean
  private TrackingService trackingService;

  @SpyBean
  MessagePostProcessor messageAccessor;

  @Test
  public void EXPECT_ResultsAreSerializedCorrectly() throws IOException {
    //Preconditions
    assertThat(mapper).isNotNull();
    GetQueueUrlResult urlResult = new GetQueueUrlResult();
    urlResult.setQueueUrl("http://foo");
    when(sqsClient.getQueueUrl((GetQueueUrlRequest) any())).thenReturn(urlResult);
    // Mockito.doNothing().when(template).doSend((Message) any());

    //Preparations
    TestModel result = mapper.readValue(stringFromFile("./testmodel.json"), TestModel.class);

    //Execution
    out.produce(result);

    //Preparation for assertion
    ArgumentCaptor argument = ArgumentCaptor.forClass(Message.class);
    verify(messageAccessor).postProcessMessage((Message<?>) argument.capture());
    Message messageResult = (Message) argument.getValue();

    //Assertion
    logMessage(messageResult);

    String str = messageResult.getPayload().toString();

    BasicJsonTester j = new BasicJsonTester(getClass());

    assertThat(j.from(str)).extractingJsonPathStringValue("@.foo").isEqualTo("foo");
    assertThat(j.from(str)).extractingJsonPathStringValue("@.bar").isEqualTo("bar");
    assertThat(j.from(str)).extractingJsonPathStringValue("@.one").isEqualTo("1.00");
  }

  private void logMessage(Message messageResult) {
    for (Entry e : messageResult.getHeaders().entrySet()) {
      LOG.info("header: " + e.getKey() + " -> " + e.getValue());
    }
    LOG.info("payload: " + messageResult.getPayload().toString());
  }

  private String stringFromFile(String filename) throws IOException {
    return IOUtils.toString(this.getClass().getResourceAsStream(filename), "UTF-8");
  }
}

