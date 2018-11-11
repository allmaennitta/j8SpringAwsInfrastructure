package de.allmaennitta.java.aws.infrastructure.config.aspectj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyServer {

  private static final Logger LOG = LoggerFactory.getLogger(MyServer.class);

  @LogExecutionTime
  public void serve() throws InterruptedException {
    LOG.info("falling asleep...");
    Thread.sleep(2000);
  }
}