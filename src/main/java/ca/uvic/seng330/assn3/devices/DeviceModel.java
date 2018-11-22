package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;

import java.util.UUID;

public abstract class DeviceModel {

  protected Status aStatus;
  protected UUID aID;
  protected int id;
  //protected Organizer aOwner;
  
  public DeviceModel() {
    this.id = 0;
  }
  
  public void setStatus(Status pStatus) {
    aStatus = pStatus;
  }
  
  public Status getStatus() {
    return aStatus;
  }

  public UUID getIdentifier() {
    return aID;
  }
  
  public void turnOn() {
    aStatus = Status.NORMAL;
  }
  
  public void turnOff() {
    aStatus = Status.OFF;
  }
}