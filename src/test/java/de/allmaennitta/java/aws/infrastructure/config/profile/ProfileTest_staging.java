package de.allmaennitta.java.aws.infrastructure.config.profile;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import de.allmaennitta.java.aws.infrastructure.ProtoHandlerTemplate;
import de.allmaennitta.java.aws.infrastructure.config.JsonConfiguration;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.CloudWatchMetrics;
import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.Timespans;
import de.allmaennitta.java.aws.infrastructure.config.main.AbstractHandler;
import de.allmaennitta.java.aws.infrastructure.config.main.Producer;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import de.allmaennitta.dummy.javaws.shared.model.TestModel;
import de.allmaennitta.dummy.javaws.shared.model.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {JsonConfiguration.class, ProfileTest_staging.Config.class})
@ActiveProfiles("staging")
@DirtiesContext
public class ProfileTest_staging {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileTest_staging.class);

    @MockBean
    private Producer producer;

    @MockBean
    private Timespans timespans;

    @MockBean
    private CloudWatchMetrics metrics;

    @MockBean
    private AmazonCloudWatch amazonCloudWatch;

    @MockBean
    private TrackingService trackingService;

    @TestConfiguration
    static class Config {

        @MockBean
        private QueueMessagingTemplate template;

        @Bean
        protected AbstractHandler<TestModel> handler() {
            return new ProtoHandlerTemplate<>();
        }

        @Bean
        @Profile("dev")
        String stringReturnerDev() {
            return "DEV-ENV!";
        }

        @Bean
        @Profile("staging")
        String stringReturnerStaging() {
            return "STAGING-ENV!";
        }

        @Bean
        @Profile("prod")
        String stringReturnerProd() {
            return "PROD-ENV!";
        }
    }


    @Autowired
    private AbstractHandler handler;

    @Test
    public void EXPECT_ResultsAreDeserializedCorrecty() {
        TestModel resultToCheck = runHandler();
        //See dev-test for specific documentation about asserts
        assertThat(resultToCheck.getBaz()).isEqualTo("proto");
        assertThat(resultToCheck.getFoo()).isEqualTo("staging-environment");
        assertThat(resultToCheck.getBar()).isEqualTo("STAGING-ENV!");
        assertThat(resultToCheck.getBoz()).isEqualTo("isNotDev");
        LOG.info("SHOULD BE LOGGED");
        LOG.debug("SHOULD NOT BE LOGGED");
    }

    private TestModel runHandler() {
        ArgumentCaptor<TestModel> resultToCheck = ArgumentCaptor.forClass(TestModel.class);
        handler.handle(TestUtils.createDummyModel());
        verify(producer).produce(resultToCheck.capture());
        return resultToCheck.getValue();
    }

}

