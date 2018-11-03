package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.*;
import java.util.UUID;

public abstract class Device {

  protected Status aStatus;
  protected UUID aID;
  protected Hub aOwner;
  
  public void setStatus(Status pStatus) {
    aStatus = pStatus;
  }
  
  public Status getStatus() {
    return aStatus;
  }

  public UUID getIdentifier() {
    return aID;
  }
}