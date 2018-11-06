package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.*;
import ca.uvic.seng330.assn3.devices.Temperature.TemperatureOutofBoundsException;
import java.util.UUID;

public class Thermostat extends DeviceModel {

  private Mediator network;
  private Temperature temp;

  public Thermostat(Temperature temp, Mediator network) {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.NORMAL;
    this.temp = temp;
    this.network = network;
    try {
      this.network.register(this);
    } catch (HubRegistrationException e) {
      return;
    }
  }

  public Thermostat(Mediator network) {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.NORMAL;
    this.temp = new Temperature(0, Temperature.Unit.CELSIUS);
    this.network = network;
    try {
      this.network.register(this);
    } catch (HubRegistrationException e) {
      return;
    }
  }

  public Thermostat(Temperature temp) {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.NORMAL;
    this.temp = temp;
  }

  public Thermostat() {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.NORMAL;
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
}