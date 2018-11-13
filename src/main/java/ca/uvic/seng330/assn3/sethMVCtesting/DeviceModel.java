package ca.uvic.seng330.assn3.sethMVCtesting;

import ca.uvic.seng330.assn3.*;
import java.util.UUID;

public abstract class DeviceModel {

  protected Status aStatus;
  protected UUID aID;
  protected HubController aOwner;
  
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