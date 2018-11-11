package de.allmaennitta.java.aws.infrastructure.config.aws.metrics;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsMetricsConfiguration {
    @Bean
    public AmazonCloudWatch amazonCloudWatch() {
        return AmazonCloudWatchClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
    }

    @Bean
    public CloudWatchMetrics cloudWatchMetrics(AmazonCloudWatch cloudWatch)  {
        return new CloudWatchMetrics(cloudWatch);
    }

  /**
   * Container to measure response-times from message-receive to message-sent
   */
  @Bean
    public Timespans timespans() {
        return new Timespans();
    }


    @Bean
    /**
     * To execute metrics-start at application startup
     */
    public ApplicationRunner appRunner(CloudWatchMetrics metrics) {
        return args -> {
            metrics.putSTARTS(); //
        };
    }
}
