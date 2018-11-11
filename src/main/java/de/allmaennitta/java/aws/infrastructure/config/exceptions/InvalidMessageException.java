package de.allmaennitta.java.aws.infrastructure.config.exceptions;


public class InvalidMessageException extends RuntimeException {

  public InvalidMessageException(String s) {
    super(s);
  }
}