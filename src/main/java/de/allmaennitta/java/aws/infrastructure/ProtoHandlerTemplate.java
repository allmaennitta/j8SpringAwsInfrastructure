package de.allmaennitta.java.aws.infrastructure;

import de.allmaennitta.java.aws.infrastructure.config.aws.metrics.CloudWatchMetrics;
import de.allmaennitta.java.aws.infrastructure.config.exceptions.InvalidMessageException;
import de.allmaennitta.java.aws.infrastructure.config.exceptions.UnprocessableMessageException;
import de.allmaennitta.java.aws.infrastructure.config.main.AbstractHandler;
import de.allmaennitta.java.aws.infrastructure.config.main.Producer;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import de.allmaennitta.dummy.javaws.shared.model.TestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * This HandlerTemplate is just a template to show how configuration is
 * implemented. It's not intended for use.
 */
@Deprecated
@Component
public class ProtoHandlerTemplate<T>
  extends AbstractHandler<T> {

  private static final Logger LOG = LoggerFactory.getLogger(ProtoHandlerTemplate.class);

  @Autowired
  protected Producer<TestModel> producer;
  @Autowired
  protected TrackingService trackingService;
  @Autowired
  protected CloudWatchMetrics metrics;
  @Autowired
  private Environment environment;

  @Autowired
  String profileSpecificTextFromBean;

  @Value("${profile.testvalue}")
  protected String testvalue;

  @Value("${SERVICE.name}")
  protected String serviceName;

  public void handle(T t) {
    TestModel testModel = null;
    try {
      checkPreconditions(t);
      testModel = (TestModel) t;
      LOG.info("Handling " + testModel.toString());

      // Demo-Code to show the features of spring profiles

      testModel.setFoo(testvalue);
      testModel.setBar(profileSpecificTextFromBean);
      testModel.setBaz(serviceName);
      if (environment.acceptsProfiles("dev")) {
        testModel.setBoz("isDev");
      }
      if (environment.acceptsProfiles("!dev")) {
        testModel.setBoz("isNotDev");
      }
      ////////////

      LOG.info("Producing " + testModel.toString());
      producer.produce(testModel);
    } catch (UnprocessableMessageException e) {
      metrics.putERRORS();
      LOG.error(e.getMessage(), e);
      trackingService.trackString("ABORT", "ABORT", "plain",
        "Abort, because: " + e.getMessage());
      return;
    } catch (InvalidMessageException e) {
      if (null == testModel) testModel = new TestModel();
      String errorMessage = composeErrorMessage(e.getMessage(), testModel);
      LOG.error(errorMessage, e);
      metrics.putERRORS();
      testModel.setErrorMessage(errorMessage);
    } catch (Exception e) {
      metrics.putERRORS();
      LOG.error("Unexpected Exception: " + e.getMessage(), e);
      trackingService.trackString("ABORT", "ABORT", "plain",
        "Unexpected Exception. Abort, because: " + e.getMessage());
      return;
    }
  }

  private boolean checkPreconditions(T t) {
    if (!(t instanceof TestModel)) {
      throw new UnprocessableMessageException(
        "Wrong class! '" + t.getClass().getSimpleName() + "' instead of 'TestModel'!");
    }

    TestModel result = (TestModel) t;
    if (null == result.getFoo()) {
      throw new UnprocessableMessageException(
        "TestModel without Foo. No processing possible: " + result.toString());
    }

    LOG.debug("TestModel valid. Handling " + result.toString());
    return false;
  }

  private String composeErrorMessage(String msg, TestModel result) {
    if (null == result.getErrorMessage() || result.getErrorMessage() == "") {
      return msg;
    } else {
      return result.getErrorMessage().concat("; " + msg);
    }
  }
}