package de.allmaennitta.dummy.javaws.shared.utils;

public class IataCode {

  private String IATACode;
  private String ICAOCode;
  private String ManufacturerAircraftTypeModel;
  private String WakeCategory;

  public IataCode(String IATACode, String ICAOCode, String manufacturerAircraftTypeModel,
      String wakeCategory) {
    this.IATACode = IATACode;
    this.ICAOCode = ICAOCode;
    ManufacturerAircraftTypeModel = manufacturerAircraftTypeModel;
    WakeCategory = wakeCategory;
  }

  public String getIATACode() {

    return IATACode;
  }

  public void setIATACode(String IATACode) {
    this.IATACode = IATACode;
  }

  public String getICAOCode() {
    return ICAOCode;
  }

  public void setICAOCode(String ICAOCode) {
    this.ICAOCode = ICAOCode;
  }

  public String getManufacturerAircraftTypeModel() {
    return ManufacturerAircraftTypeModel;
  }

  public void setManufacturerAircraftTypeModel(String manufacturerAircraftTypeModel) {
    ManufacturerAircraftTypeModel = manufacturerAircraftTypeModel;
  }

  public String getWakeCategory() {
    return WakeCategory;
  }

  public void setWakeCategory(String wakeCategory) {
    WakeCategory = wakeCategory;
  }
}
