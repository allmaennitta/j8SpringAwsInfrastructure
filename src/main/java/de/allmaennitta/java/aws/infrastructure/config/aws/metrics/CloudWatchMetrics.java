package de.allmaennitta.java.aws.infrastructure.config.aws.metrics;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CloudWatchMetrics {

  private static final Logger LOG = LoggerFactory.getLogger(CloudWatchMetrics.class);

  @Value("${METRICS.NAMESPACE:TEST-TASK}")
  private String namespace;
  @Value("${METRICS.DIMENSION.NAME:SERVICE}")
  private String dimensionName;
  @Value("${METRICS.DIMENSION.NAME.THIRDPARTY:THIRDPARTYSERVICE}")
  private String dimensionNameThirdParty;
  @Value("${METRICS.DIMENSION.VALUE:proto-service}")
  private String dimensionValue;

  private AmazonCloudWatch amazonCloudWatch;
  private Dimension serviceDimension;
  private Dimension thpaServiceDimension;

  public CloudWatchMetrics(AmazonCloudWatch cloudWatch){
    this.amazonCloudWatch = cloudWatch;
  }

  @PostConstruct
  protected void init(){
    this.serviceDimension = new Dimension().withName(dimensionName).withValue(dimensionValue);
    this.thpaServiceDimension = new Dimension().withName(dimensionNameThirdParty)
            .withValue(dimensionValue);
  }

  public void putSTARTS() {
    logMetric(this.serviceDimension, METRICS.STARTS, 1L);
  }

  public void putRESPONSE_TIME(Long milliseconds) {
    logMetric(this.serviceDimension, METRICS.RESPONSE_TIME, milliseconds);
  }

  public void putRECEIVED_MESSAGES() {
    logMetric(this.serviceDimension, METRICS.RECEIVED_MESSAGES, 1L);
  }

  public void putDELETED_MESSAGES() {
    logMetric(this.serviceDimension, METRICS.DELETED_MESSAGES, 1L);
  }

  public void putERRORS() {
    logMetric(this.serviceDimension, METRICS.ERRORS, 1L);
  }

  public void putWARNINGS() {
    logMetric(this.serviceDimension, METRICS.WARNINGS, 1L);
  }

  public void putTP_RESPONSE_TIME(Long milliseconds) {
    logMetric(this.thpaServiceDimension, METRICS.TP_RESPONSE_TIME, milliseconds);
  }

  public void putTP_ERRORS() {
    logMetric(this.thpaServiceDimension, METRICS.ERRORS, 1L);
  }

  public void putTP_WARNINGS() {
    logMetric(this.thpaServiceDimension, METRICS.WARNINGS, 1L);
  }

  public void logMetric(Dimension dimension, METRICS metric, Long value) {
    MetricDatum datum = new MetricDatum().withMetricName(metric.name())
        .withUnit(metric.getUnit()).withValue(value.doubleValue()).withDimensions(dimension);
    PutMetricDataRequest request = new PutMetricDataRequest().withNamespace(namespace)
        .withMetricData(datum);
      this.amazonCloudWatch.putMetricData(request);
    LOG.debug(
        String.format("\"Metrics: notifying %s with value %s.", metric.name, value.toString()));
  }


  private enum METRICS {
    STARTS("STARTS", StandardUnit.Count),
    RESPONSE_TIME("RESPONSE_TIME", StandardUnit.Milliseconds),
    RECEIVED_MESSAGES("RECEIVED_MESSAGES", StandardUnit.Count),
    DELETED_MESSAGES("DELETED_MESSAGES", StandardUnit.Count),
    ERRORS("ERRORS", StandardUnit.Count),
    WARNINGS("WARNINGS", StandardUnit.Count),
    TP_RESPONSE_TIME("RESPONSE_TIME", StandardUnit.Milliseconds);

    private final String name;
    private final StandardUnit unit;

    METRICS(String name, StandardUnit unit) {
      this.unit = unit;
      this.name = name;
    }

    public StandardUnit getUnit() {
      return unit;
    }

    public String getName() {
      return name;
    }
  }
}
