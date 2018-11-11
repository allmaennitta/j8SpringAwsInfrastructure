package de.allmaennitta.java.aws.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.allmaennitta.dummy.javaws.shared.ModelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
@Import({ModelConfiguration.class})

/**
 * Beans necessary for working with Jackson JSON-Marshalling/Unmarshalling
 * depends on
 * - AwsSqsConfiguration
 * - ModelConfiguration
 */
public class JsonConfiguration {

  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  public MappingJackson2MessageConverter messageConverter() {
    MappingJackson2MessageConverter jacksonMessageConverter = new MappingJackson2MessageConverter();
    jacksonMessageConverter.setObjectMapper(objectMapper);
    jacksonMessageConverter.setSerializedPayloadClass(String.class);

    /*
    TODO: gehen wir implizipt davon aus, dass Messages immer Json enthalten oder haben wir Abweichungen und damit
    TODO: die Notwendigkeit, strikt die Header zu setzen? */
    jacksonMessageConverter.setStrictContentTypeMatch(false);
    return jacksonMessageConverter;
  }

}
