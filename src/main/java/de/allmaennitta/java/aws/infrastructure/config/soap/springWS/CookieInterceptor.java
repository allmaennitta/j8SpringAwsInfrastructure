package de.allmaennitta.java.aws.infrastructure.config.soap.springWS;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

public class CookieInterceptor implements ClientInterceptor {

  private String setCookie = null;

  @Override
  public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
    return true;
  }

  @Override
  public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
    String[] headers = ((SaajSoapMessage) messageContext.getResponse()).getSaajMessage().getMimeHeaders()
        .getHeader("Set-Cookie");
    //to reread set setCookie-field to null before sending request
    if (getSetCookie() == null && headers != null && headers.length > 0) {
      setSetCookie(headers[0]);
    }
    return true;
  }

  @Override
  public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
    return true;
  }

  @Override
  public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

  }

  public String getSetCookie() {
    return setCookie;
  }

  public void setSetCookie(String setCookie) {
    this.setCookie = setCookie;
  }
}
