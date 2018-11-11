package de.allmaennitta.java.aws.infrastructure.config.soap;

import de.allmaennitta.java.aws.infrastructure.config.JsonConfiguration;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.Timespans;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.tempuri.SOAPDemoSoap;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@Import({TheWsConfiguration.class, JsonConfiguration.class})
public class TheWsConfigurationTest {
  @MockBean
  QueueMessagingTemplate template;

  @MockBean
  Timespans timespans;

  @Autowired
  SOAPDemoSoap soapDemo;

  @Test
  public void EXPECT_SOAPImplementation() {
    assertThat(soapDemo.addInteger(1L, 1L)).isEqualTo(2L);
  }

}