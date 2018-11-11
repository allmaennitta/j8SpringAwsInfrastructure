package de.allmaennitta.java.aws.infrastructure.config.main;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.allmaennitta.java.aws.infrastructure.config.JsonConfiguration;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.CloudWatchMetrics;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.Timespans;
import de.allmaennitta.java.aws.infrastructure.config.tracking.ListenerAspect;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingConfiguration;
import java.util.HashMap;

import de.allmaennitta.dummy.javaws.shared.model.TestModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableAspectJAutoProxy
@Import({JsonConfiguration.class, TrackingConfiguration.class})
public class AspectJListenerTest {

  @TestConfiguration
  static class Config {

    @MockBean
    private AbstractHandler handler;
    @MockBean
    private QueueMessagingTemplate template;

    @Bean
    @Primary
    protected Listener<TestModel> getOut() {
      return new Listener<TestModel>("foo", handler, TestModel.class);
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(AspectJListenerTest.class);
  @Autowired
  private Listener out;

  @Autowired
  private AbstractHandler<TestModel> handler;

  @Autowired
  private ObjectMapper mapper;

  @SpyBean
  private ListenerAspect aspect;

  @MockBean
  private Timespans timespans;

  @MockBean
  private CloudWatchMetrics metrics;

  @Test
  public void EXPECT_PointCut_atListener_isBeingExecuted() throws Throwable {
    ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
    out.consume(
      "{\n" +
        "  \"foo\":\"foo\",\n" +
        "  \"bar\":\"bar\",\n" +
        "  \"baz\":\"baz\",\n" +
        "  \"boz\":\"boz\",\n" +
        "  \"one\":\"1.00\",\n" +
        "  \"errorMessage\":null\n" +
        "}"
        ,
        new MessageHeaders(new HashMap<>()));
    verify(aspect).setSessionInfos(any(), argument.capture(), any());
    assertThat(argument.getValue()).isNotEmpty();
    LOG.info("Payload: " + argument.getValue());
  }
}

