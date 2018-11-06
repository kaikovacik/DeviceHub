package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.*;
import java.util.UUID;

public class Lightbulb extends DeviceModel{

  private Mediator network;
  private boolean isOn;

  public Lightbulb(Mediator network) {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.NORMAL;
    this.isOn = false;
    this.network = network;
    try {
      this.network.register(this);
    } catch (HubRegistrationException e) {
      return;
    }
  }

  public Lightbulb() {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.NORMAL;
    this.isOn = false;
  }
 
  public void toggle() {
    isOn = !isOn;
  }

  public boolean getCondition() {
    return isOn;
  }
  
  public void turnOff() {
    if (isOn) {
      toggle();
    }
    super.turnOff();
  }
}