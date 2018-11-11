package de.allmaennitta.java.aws.infrastructure.config.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SOAPCookieHandler implements SOAPHandler<SOAPMessageContext> {

  private static Logger LOG = LoggerFactory.getLogger(SOAPCookieHandler.class.getName());
  private final String SET_COOKIE = "SET-COOKIE";
  private final String COOKIE = "COOKIE";

  private String cookie = null;

  @Override
  public Set<QName> getHeaders() {
    return null;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext context) {
    if ((Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
      setCookieHeader(context);
    } else {
      cookie = getSetCookieHeader(context);
    }
    return true;
  }

  @Override
  public boolean handleFault(SOAPMessageContext context) {
    return true;
  }

  @Override
  public void close(MessageContext context) {

  }

  public String getCookie() {
    return cookie;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  private String getSetCookieHeader(SOAPMessageContext context) {
    Map<String, List<String>> headers = (Map<String, List<String>>)
        context.get(MessageContext.HTTP_RESPONSE_HEADERS);
    List<String> list = headers.get(SET_COOKIE);
    if (list != null && !list.isEmpty()) {
      return list.get(0);
    } else {
      return cookie;
    }
  }

  private void setCookieHeader(SOAPMessageContext context) {
    if (cookie != null) {
      Map<String, List<String>> headers = (Map<String, List<String>>)
          context.get(MessageContext.HTTP_REQUEST_HEADERS);
      if (null == headers) {
        headers = new HashMap<>();
        context.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
      }
      List<String> list = new ArrayList<>();
      list.add(cookie);
      if (headers.containsKey(COOKIE)) {
        headers.replace(COOKIE, list);
      } else {
        headers.put(COOKIE, list);
      }
    }
  }
}
