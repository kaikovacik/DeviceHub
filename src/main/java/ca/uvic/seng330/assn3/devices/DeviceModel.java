package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

public abstract class DeviceModel {

  protected Status aStatus;
  protected UUID aID;
  public IntegerProperty id;
  protected StringProperty statusObsStr;
  
  public DeviceModel(int id) {
    this.id = new SimpleIntegerProperty();
    this.id.set(id);
  }
  
  public void setStatus(Status pStatus) {
    aStatus = pStatus;
    statusObsStr.set(aStatus.toString());
  }
  
  public Status getStatus() {
    return aStatus;
  }
  
  public StringProperty getStatusAsString() {
    return statusObsStr;
  }

  public int getIdentifier() {
    return id.intValue();
  }
  
  public void turnOn() {
    setStatus(Status.NORMAL);
  }
  
  public void turnOff() {
    setStatus(Status.OFF);
 
  }
}