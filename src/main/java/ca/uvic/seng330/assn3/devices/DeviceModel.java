package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.UUID;

public abstract class DeviceModel {

  protected Status aStatus;
  protected UUID aID;
  public IntegerProperty id;
  //protected Organizer aOwner;
  
  public DeviceModel(int id) {
    this.id = new SimpleIntegerProperty();
    this.id.set(id);
  }
  
  public void setStatus(Status pStatus) {
    aStatus = pStatus;
  }
  
  public Status getStatus() {
    return aStatus;
  }

  public int getIdentifier() {
    return id.intValue();
  }
  
  public void turnOn() {
    aStatus = Status.NORMAL;
  }
  
  public void turnOff() {
    aStatus = Status.OFF;
  }
}