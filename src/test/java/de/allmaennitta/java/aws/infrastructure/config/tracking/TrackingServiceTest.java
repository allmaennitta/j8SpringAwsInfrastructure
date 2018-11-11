package de.allmaennitta.java.aws.infrastructure.config.tracking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.allmaennitta.java.aws.infrastructure.config.main.Producer;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingServiceTest.Config;
import de.allmaennitta.dummy.javaws.shared.ModelConfiguration;
import de.allmaennitta.dummy.javaws.shared.model.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import({Config.class, ModelConfiguration.class})
public class TrackingServiceTest {

  private static final Logger LOG = LoggerFactory.getLogger(TrackingServiceTest.class);

  @TestConfiguration
  static class Config {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    Producer<TrackingMessage> trackingMessageProducer;

    @Bean
    public TrackingService getTrackingService() {
      return new TrackingService("foo", trackingMessageProducer);
    }
  }

  @Autowired
  TrackingService out;

  @Autowired
  Producer<TrackingMessage> trackingMessageProducer;

  @Test
  public void EXPECT_CorrectTrackingOf_XML_asString() {
    String body = "<root><div/></root>";
    out.trackString("dance", "title", "XML", body);
    ArgumentCaptor<TrackingMessage> argument = ArgumentCaptor.forClass(TrackingMessage.class);
    verify(trackingMessageProducer).produce(argument.capture());
    assertThat(argument.getValue().getAction()).isEqualTo("dance");
    assertThat(argument.getValue().getBody()).isNull();
    assertThat(argument.getValue().getBodyZ()).isNotEmpty();
  }

  @Test
  public void EXPECT_CorrectTrackingOf_Json() {
    out.trackJson("dance", "title", TestUtils.createDummyModel());
    ArgumentCaptor<TrackingMessage> argument = ArgumentCaptor.forClass(TrackingMessage.class);
    verify(trackingMessageProducer).produce(argument.capture());
    final TrackingMessage msg = argument.getValue();
    LOG.info(msg.getBodyZ());
    assertThat(msg.getAction()).isEqualTo("dance");
    assertThat(msg.getBody()).isNull();
    assertThat(msg.getBodyZ()).isNotEmpty();
  }

}