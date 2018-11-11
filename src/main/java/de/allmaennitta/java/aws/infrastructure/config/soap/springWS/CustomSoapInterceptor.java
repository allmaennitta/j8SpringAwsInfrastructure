package de.allmaennitta.java.aws.infrastructure.config.soap.springWS;

import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

public class CustomSoapInterceptor implements ClientInterceptor {

  private TrackingService trackingService;
  private String setCookie;

  public CustomSoapInterceptor(TrackingService trackingService) {
    this.trackingService = trackingService;
  }

  @Override
  public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
    SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
    trackSoapMessage(soapMessage, soapMessage.toString(), "SOAP: handleRequest");
    return true;
  }

  @Override
  public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
    SoapMessage soapMessage = (SoapMessage) messageContext.getResponse();
    trackSoapMessage(soapMessage, soapMessage.toString(), "SOAP: handleResponse");
    return true;
  }

  @Override
  public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
    //TODO get fault
    SoapMessage soapMessage = (SoapMessage) messageContext.getResponse();
    trackSoapMessage(soapMessage, soapMessage.toString(), "SOAP: handleFault");
    return true;
  }

  @Override
  public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

  }

  private void trackSoapMessage(SoapMessage soapMessage, String action, String title) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      soapMessage.writeTo(out);
      String strMsg = new String(out.toByteArray(), "UTF-8");
      trackingService.trackString(action, title, "xml", strMsg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getSetCookie() {
    return setCookie;
  }
}
