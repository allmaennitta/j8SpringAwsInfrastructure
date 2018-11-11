package de.allmaennitta.java.aws.infrastructure.config.main;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * Beans doing some status reporting when the service starts
 */
public class StatusConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(StatusConfiguration.class);

  @Value("${QUEUES.in}")
  private String inQueue;

  @Value("${QUEUES.out}")
  private String outQueue;

  @Autowired
  private Environment env;

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    return args -> {
      LOG.info("Using profiles: "+Arrays.asList(env.getActiveProfiles()).toString());
      LOG.info("Consuming from: " + inQueue);
      LOG.info("Producing to: " + outQueue);

      if (LOG.isDebugEnabled()) {
        debugIncludedBeans(ctx);
      }
    };
  }

  private void debugIncludedBeans(ApplicationContext ctx) {
    LOG.debug("**** Beans included by Spring Boot: ****");
    String[] beanNames = ctx.getBeanDefinitionNames();
    Arrays.sort(beanNames);
    for (String beanName : beanNames) {
      LOG.debug(beanName);
    }
  }
}