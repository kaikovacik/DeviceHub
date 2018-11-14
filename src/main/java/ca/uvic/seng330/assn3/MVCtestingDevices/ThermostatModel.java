// package ca.uvic.seng330.assn3.sethMVCtestingDevices;

// import java.util.UUID;

// import ca.uvic.seng330.assn3.Status;
// import ca.uvic.seng330.assn3.devices.CameraFullException;
// import ca.uvic.seng330.assn3.sethMVCtesting.Organizer;
// import javafx.beans.property.BooleanProperty;
// import javafx.beans.property.IntegerProperty;
// import javafx.beans.property.ReadOnlyIntegerWrapper;
// import javafx.beans.property.SimpleBooleanProperty;
// import javafx.beans.property.SimpleIntegerProperty;

// public class CameraModel extends DeviceModel {

//   private BooleanProperty isThisRecording = new SimpleBooleanProperty();
//   private SimpleIntegerProperty diskSizeRemaining = new SimpleIntegerProperty();

//   private static final int maxMem = 5;

//   public CameraModel() {
//     this.aID = UUID.randomUUID();
//     this.diskSizeRemaining.set(maxMem);
//     this.isThisRecording.set(false);
//     this.aStatus = Status.OFF;
//   }

//   public IntegerProperty getDiskSize() {
//     return diskSizeRemaining;
//   }

//   public int getMaxMem() {
//     return maxMem;
//   }

//   protected void setDiskSize() {
//     diskSizeRemaining.set(maxMem);
//   }

//   public void decrementDiskSize() {
//     diskSizeRemaining.set(diskSizeRemaining.intValue() - 1);
//   }

//   // the recordingLabel is bound to this.
//   public final BooleanProperty isThisRecordingProperty() {
//     return isThisRecording;
//   }

//   public final boolean getIsRecording() {
//     return isThisRecording.get();
//   }

//   public final void setIsRecording(final boolean bool) {
//     this.isThisRecording.set(bool);
//   }

//   @Override
//   public final void turnOff() {
//     super.turnOff();
//     setIsRecording(false);
//   }
// }









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
    } else if (temp.getCelsius() < 0) {
      throw temp.new TemperatureOutofBoundsException("Temperature is too low");
    } else {
      this.temp = temp;
    }
  }

  public Temperature getTemp() {
    return temp;
  }
}


/** Temperature class. */
public class Temperature {

  public class TemperatureOutofBoundsException extends Exception {

    public TemperatureOutofBoundsException(String message) {
      super(message);
    }
  }
  public enum Unit {
    CELSIUS,
    FAHRENHEIT,
    KELVIN
  }
  private double celsius;
  private double fahrenheit;
  private double kelvin;

  public Temperature(double temp, Unit unit) {
    switch (unit) {
      case CELSIUS:
        this.celsius = temp;
        this.fahrenheit = temp * 9 / 5 + 32;
        this.kelvin = temp + 273.15;
        break;
      case FAHRENHEIT:
        this.celsius = (temp - 32) * 5 / 9;
        this.fahrenheit = temp;
        this.kelvin = (temp - 32) * 5 / 9 + 273.15;
        break;
      case KELVIN:
        this.celsius = temp - 273.15;
        this.fahrenheit = (temp - 273.15) * 9 / 5 + 32;
        this.kelvin = temp;
        break;
      default:
        return;
    }
  }

  public double getCelsius() {
    return celsius;
  }

  public double getFahrenheit() {
    return fahrenheit;
  }

  public double getKelvin() {
    return kelvin;
  }
}