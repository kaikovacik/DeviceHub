package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.*;
import ca.uvic.seng330.assn3.devices.Temperature.TemperatureOutofBoundsException;
import java.util.UUID;

public class Thermostat implements Device {

  private Status status;
  private UUID id;
  private Mediator network;
  private Temperature temp;

  public Thermostat(Temperature temp, Mediator network) {
    this.id = UUID.randomUUID();
    this.status = Status.NORMAL;
    this.temp = temp;
    this.network = network;
    try {
      this.network.register(this);
      network.alert(this, ("Thermostat (" + this.id.toString() + ") added to network"));
    } catch (HubRegistrationException e) {
      return;
    }
  }

  public Thermostat(Mediator network) {
    this.id = UUID.randomUUID();
    this.status = Status.NORMAL;
    this.temp = new Temperature(0, Temperature.Unit.CELSIUS);
    this.network = network;
    try {
      this.network.register(this);
      network.alert(this, ("Thermostat (" + this.id.toString() + ") added to network"));
    } catch (HubRegistrationException e) {
      return;
    }
  }

  public Thermostat(Temperature temp) {
    this.id = UUID.randomUUID();
    this.status = Status.NORMAL;
    this.temp = temp;
  }

  public Thermostat() {
    this.id = UUID.randomUUID();
    this.status = Status.NORMAL;
    this.temp = new Temperature(0, Temperature.Unit.CELSIUS);
  }

  // Valid temperatures are [0 - 1000] *C
  public void setTemp(Temperature temp) throws TemperatureOutofBoundsException {
    if (temp.getCelsius() > 1000) {
      throw temp.new TemperatureOutofBoundsException("Temperature is too high");
    } else if  (temp.getCelsius() < 0) {
      throw temp.new TemperatureOutofBoundsException("Temperature is too low");
    } else {
      this.temp = temp;
    }
  }

  public Temperature getTemp() {
    return temp;
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