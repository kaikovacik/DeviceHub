package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.*;
import java.util.UUID;

public class SmartPlug implements Device {

  private Status status;
  private UUID id;
  private Mediator network;
  private boolean isOn;

  public SmartPlug(Mediator network) {
    this.id = UUID.randomUUID();
    this.status = Status.NORMAL;
    this.isOn = false;
    this.network = network;
    try {
      this.network.register(this);
      network.alert(this, ("Smart Plug (" + this.id.toString() + ") added to network"));
    } catch (HubRegistrationException e) {
      return;
    }
  }

  public SmartPlug() {
    this.id = UUID.randomUUID();
    this.status = Status.NORMAL;
    this.isOn = false;
  }

  public void toggle() {
    isOn = !isOn;
  }

  public boolean getCondition() {
    return isOn;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public UUID getIdentifier() {
    return id;
  }

  public Status getStatus() {
    return status;
  }
}