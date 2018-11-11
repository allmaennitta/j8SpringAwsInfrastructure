package de.allmaennitta.java.aws.infrastructure.config.main;

public abstract class AbstractHandler<REQ> {
  public AbstractHandler() {
  }

  public abstract void handle(REQ req);
}
