package de.allmaennitta.dummy.javaws.shared.model;

import java.math.BigDecimal;

public class TestModel {
  private String foo;
  private String bar;
  private String baz;
  private String boz;
  private BigDecimal one;
  private String errorMessage;

  @Override
  public String toString() {
    return "TestModel{" +
      "foo='" + foo + '\'' +
      ", bar='" + bar + '\'' +
      ", one=" + one +
      '}';
  }

  public String getFoo() {
    return foo;
  }

  public void setFoo(String foo) {
    this.foo = foo;
  }

  public String getBar() {
    return bar;
  }

  public void setBar(String bar) {
    this.bar = bar;
  }

  public BigDecimal getOne() {
    return one;
  }

  public void setOne(BigDecimal one) {
    this.one = one;
  }

  public void setBaz(String baz) {
    this.baz = baz;
  }

  public String getBaz() {
    return baz;
  }

  public String getBoz() {
    return boz;
  }

  public void setBoz(String boz) {
    this.boz = boz;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
