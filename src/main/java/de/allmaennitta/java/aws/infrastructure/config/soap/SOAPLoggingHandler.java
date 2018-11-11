package de.allmaennitta.java.aws.infrastructure.config.soap;

import de.allmaennitta.java.aws.infrastructure.config.tracking.TrackingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.soap.saaj.support.SaajUtils;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.util.Set;

public class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext> {

  private static Logger LOG = LoggerFactory.getLogger(SOAPLoggingHandler.class.getName());

  private TrackingService trackingService;

  public SOAPLoggingHandler(TrackingService trackingService) {
    this.trackingService = trackingService;
  }

  @Override
  public Set<QName> getHeaders() {
    return null;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext context) {
    trackSoapMessage(context.getMessage(), messageToString(context),
      (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY) ? "SOAP.handleRequest" : "SOAP.handleResponse");
    return true;
  }

  @Override
  public boolean handleFault(SOAPMessageContext context) {
    QName qName = (QName) context.get(MessageContext.WSDL_OPERATION);
    String action = qName.getNamespaceURI() + "/" + qName.getLocalPart();
    trackSoapMessage(context.getMessage(), action, "SOAP.handleFault");
    return true;
  }

  @Override
  public void close(MessageContext context) {
  }

  private void trackSoapMessage(SOAPMessage soapMessage, String action, String title) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      soapMessage.writeTo(out);
      String strMsg = new String(out.toByteArray(), "UTF-8");
      trackingService.trackString(action, title, "xml", strMsg);
    } catch (Exception e) {
      LOG.error("trackSoapMessage", e);
    }
  }

  private String messageToString(SOAPMessageContext context) {
    StringBuilder builder = new StringBuilder();
    String action = "";
    try {
      final SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
      if (envelope != null) {
        SOAPBody body = envelope.getBody();
        if (body != null) {
          SOAPElement bodyElement = SaajUtils.getFirstBodyElement(body);
          if (bodyElement != null) {
            builder.append(bodyElement.getElementQName());
          }
        }
      }
      action = builder.toString();
      if (StringUtils.isEmpty(action)) {
        QName qName = (QName) context.get(MessageContext.WSDL_OPERATION);
        action = qName.getNamespaceURI() + "/" + qName.getLocalPart();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return action;
  }
}
