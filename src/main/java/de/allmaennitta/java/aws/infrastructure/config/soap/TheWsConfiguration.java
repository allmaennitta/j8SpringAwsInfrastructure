package de.allmaennitta.java.aws.infrastructure.config.soap;

import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.tempuri.SOAPDemo;
import org.tempuri.SOAPDemoSoap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.util.List;

@Import(TrackingConfiguration.class)
public class TheWsConfiguration {
//    @Value("${sessionservice.url}")
//    private String sessionServiceUrl;
//    @Value("${ws.basic.user}")
//    private String theBasicUser;
//    @Value("${ws.basic.password}")
//    private String theBasicPassword;


  @Autowired
  TrackingService trackingService;

  @Autowired
  Environment env;

  @Bean
  public SOAPDemoSoap getSoapDemo() {
    SOAPDemoSoap soapDemo = new SOAPDemo().getSOAPDemoSoap();
    BindingProvider bindingProvider = (BindingProvider) soapDemo;
//        if (env.acceptsProfiles("dev", "local")) {
//            bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, theBasicUser);
//            bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, theBasicPassword);
//        }
//        bindingProvider.getRequestContext()
//          .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, sessionServiceUrl);

    List<Handler> handlerChain = bindingProvider.getBinding().getHandlerChain();
    handlerChain.add(soapLoggingHandler(trackingService));
    bindingProvider.getBinding().setHandlerChain(handlerChain);
    return soapDemo;
  }

  @Bean
  public SOAPLoggingHandler soapLoggingHandler(TrackingService trackingService) {
    return new SOAPLoggingHandler(trackingService);
  }
}
