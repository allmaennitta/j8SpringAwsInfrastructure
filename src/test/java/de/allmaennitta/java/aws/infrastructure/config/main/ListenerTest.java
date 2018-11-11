package de.allmaennitta.java.aws.infrastructure.config.main;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.allmaennitta.java.aws.infrastructure.config.JsonConfiguration;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.CloudWatchMetrics;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.Timespans;
import de.allmaennitta.java.aws.infrastructure.config.main.ListenerTest.Config;
import de.allmaennitta.java.aws.infrastructure.config.utils.Utils;
import de.allmaennitta.java.aws.infrastructure.config.tracking.ListenerAspect;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.HashMap;

import de.allmaennitta.dummy.javaws.shared.model.TestModel;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import({JsonConfiguration.class, Config.class, ListenerAspect.class})
public class ListenerTest {

  @TestConfiguration
  static class Config {

    @MockBean
    private AbstractHandler handler;

    @Bean
    protected Listener<TestModel> getOut() {
      return new Listener<TestModel>("foo", handler, TestModel.class);
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(ListenerTest.class);

  @MockBean
  private CloudWatchMetrics cloudWatchMetrics;

  @MockBean
  private AmazonCloudWatch amazonCloudWatch;

  @MockBean
  private Timespans timespans;

  @MockBean
  private TrackingService trackingService;

  @Autowired
  private Listener out;

  @Autowired
  private AbstractHandler<TestModel> handler;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  @Spy
  private ListenerAspect aspect;


  @Test
    /*TODO: es scheint schwer möglich zu sein, den kompletten MessageHandler
    genau so zu initialisieren, wie er auch in der Anwendung initialisiert wird,
    um sicher zu sein, dass die korrekten Beans gezogen werden.
    Anbei gehe ich nur über die letzte Stufe, den ObjectMapper.
    Sollte es später möglich sein, die komplette Strecke über die annotierte Methode
    und die nativ im Kontext geladenen Beans zu gehen, dann ist dies zu bevorzugen. */
  public void EXPECT_ResultsAreDeserializedCorrecty() throws IOException {
    //Preconditions
    assertThat(mapper).isNotNull();

    //Preparations
    ArgumentCaptor<TestModel> argument = ArgumentCaptor.forClass(TestModel.class);

    //Execution
    out.consume(stringFromFile("./testmodel.json"),
        new MessageHeaders(new HashMap<>())); //=> Result

    //Assertion
    verify(handler).handle(argument.capture());
    assertThat(argument.getValue()).isNotNull();
    assertThat(argument.getValue().getFoo()).isNotNull();
    assertThat(argument.getValue().getFoo()).isEqualTo("foo");
    assertThat(argument.getValue().getBar()).isEqualTo("bar");
    assertThat(argument.getValue().getOne()).isEqualTo(new BigDecimal("1.00"));
  }

    @Test
    public void GIVEN_aMalformedMessage_EXPECT_THROWS_EXCEPTION() throws MalformedURLException {
//    LOG.error(message);
//    metrics.putERRORS();
//    trackingService.trackString("ABORT", "ABORT", "plain", message);
//
        //Preconditions
        assertThat(mapper).isNotNull();

        //Preparations
        ArgumentCaptor<String> trackingAction = ArgumentCaptor.forClass(String.class);

        out.consume("WRONG MESSAGE FORMAT", new MessageHeaders(new HashMap<>()));
        verify(cloudWatchMetrics).putERRORS();
        verify(trackingService).trackString(trackingAction.capture(), anyString(), anyString(), anyString());
        assertThat(trackingAction.getValue()).isEqualTo("ABORT");

    }

  private String stringFromFile(String filename) throws IOException {
    return IOUtils.toString(this.getClass().getResourceAsStream(filename), "UTF-8");
  }
}

