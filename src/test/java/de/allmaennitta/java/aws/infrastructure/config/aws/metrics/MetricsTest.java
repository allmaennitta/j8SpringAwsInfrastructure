package de.allmaennitta.java.aws.infrastructure.config.aws.metrics;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import({AwsMetricsConfiguration.class})
@TestPropertySource(locations = "classpath:/application.properties")
public class MetricsTest {
    {
        System.getProperties().setProperty("aws.profile", "tfm-develop");
    }

    private static final Logger LOG = LoggerFactory.getLogger(MetricsTest.class);

    @Autowired
    AmazonCloudWatch cloudWatch;

    @Autowired
    CloudWatchMetrics metrics;

    @Autowired
    Timespans timespans;

    @Test @Ignore
    public void EXPECT_MetricsCallToBeTransmitted() {
        System.getProperties().setProperty("aws.profile", "tfm-develop");

        String NAMESPACE = "TASK-TEST_IB";
        String DIMENSION_NAME = "SERVICE";
        String DIMENSION_VALUE = "PROTO";
        String METRIC_NAME = "testmilli";
        int value = 123456;
        Dimension dimension = new Dimension().withName(DIMENSION_NAME).withValue(DIMENSION_VALUE);
        MetricDatum datum = new MetricDatum().withMetricName(METRIC_NAME).withUnit(StandardUnit.Milliseconds)
                .withValue(Long.valueOf(value).doubleValue()).withDimensions(dimension);

        PutMetricDataRequest request = new PutMetricDataRequest().withNamespace(NAMESPACE).withMetricData(datum);
        this.cloudWatch.putMetricData(request);
    }

    @Test
    public void EXPECT_MetricsImplementation() {
        System.getProperties().setProperty("aws.profile", "tfm-develop");
        metrics.putSTARTS();
    }

    @Test
    public void EXPECT_Timespans_ToSendCorrectMetrics() {
        timespans.notifyReceived(1000);
        assertThat(timespans.notifySent(3000)).isEqualTo(2000);
    }
}