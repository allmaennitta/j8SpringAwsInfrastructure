package de.allmaennitta.java.aws.infrastructure.config.exceptions;

public class UnprocessableMessageException extends RuntimeException {
  public UnprocessableMessageException(String s) {
    super(s);
  }
}