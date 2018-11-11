package de.allmaennitta.java.aws.infrastructure.config.aspectj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import({ExampleAspect.class})
@EnableAspectJAutoProxy
public class TestApplication {

  private static final Logger LOG = LoggerFactory.getLogger(TestApplication.class);

  public static void main(String[] args) throws InterruptedException {
    LOG.info("Starting Application...");
    ApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
    MyServer server = (MyServer) ctx.getBean("server");
    server.serve();
  }

  @Bean
  public MyServer server() {
    LOG.info("Producing Bean myServer...");
    return new MyServer();
  }

}
