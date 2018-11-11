package de.allmaennitta.java.aws.infrastructure.config.soap.springWS;

import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

/**
 * Beans necessary for working with Spring-WS and for tracking the result in aws
 * depends on
 * - TrackingConfiguration
 */
@Configuration
public class SpringWSConfig {

  @Value("${marshaller.context.path}")
  private String marshallerContextPath;
  @Value("${unmarshaller.context.path}")
  private String unMarshallerContextPath;
  @Value("${ws.default.uri}")
  private String defaultUri;
  @Value("${ws.http.read.setcookie:false}")
  private boolean readSetCookie;
  @Autowired
  private TrackingService trackingService;

  @Bean
  public WebServiceTemplate webServiceTemplate() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath(marshallerContextPath);
    Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
    unmarshaller.setContextPath(unMarshallerContextPath);

    WebServiceTemplate template = new WebServiceTemplate();
    //template.setMessageFactory(messageFactory());
    template.setDefaultUri(defaultUri);
    template.setMarshaller(marshaller);
    template.setUnmarshaller(unmarshaller);

    ClientInterceptor[] interceptors;
    if (readSetCookie) { //authorization via Cookie
      interceptors = new ClientInterceptor[]{new CustomSoapInterceptor(trackingService), new CookieInterceptor()};
    } else {
      interceptors = new ClientInterceptor[]{new CustomSoapInterceptor(trackingService)};
    }
    template.setInterceptors(interceptors);

    return template;
  }

}
