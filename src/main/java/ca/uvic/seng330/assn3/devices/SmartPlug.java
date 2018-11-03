package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.*;
import java.util.UUID;

public class SmartPlug extends Device {

  private Mediator network;
  private boolean isOn;

  public SmartPlug(Mediator network) {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.NORMAL;
    this.isOn = false;
    this.network = network;
    try {
      this.network.register(this);
      network.alert(this, ("Smart Plug (" + this.aID.toString() + ") added to network"));
    } catch (HubRegistrationException e) {
      return;
    }
  }

  public SmartPlug() {
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
}