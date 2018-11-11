package de.allmaennitta.dummy.javaws.shared.model;

import java.math.BigDecimal;

public class TestUtils {
  public static TestModel createDummyModel(){
    TestModel model = new TestModel();
    model.setFoo("foo");
    model.setBar("bar");
    model.setBaz("baz");
    model.setBoz("boz");
    model.setOne(new BigDecimal("1.00"));
    return model;
  }
}
