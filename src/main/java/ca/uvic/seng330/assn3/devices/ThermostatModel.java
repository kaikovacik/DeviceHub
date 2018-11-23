package ca.uvic.seng330.assn3.devices;

import java.util.UUID;

import ca.uvic.seng330.assn3.HubRegistrationException;
import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ThermostatModel extends DeviceModel {
  
  public class TemperatureOutofBoundsException extends Exception {

    public TemperatureOutofBoundsException(String message) {
      super(message);
    }
  }

  private IntegerProperty setting;
  // Consider making the following default in DeviceModel
  private StringProperty statusObsStr;
  private int savedSetting;

  public ThermostatModel(int id) {
    super(id);
    this.aStatus = Status.OFF;
    this.statusObsStr = new SimpleStringProperty(aStatus.toString());
    this.setting = new SimpleIntegerProperty(0);
    this.savedSetting = 0;
    
  }

  // Valid temperatures are [0 - 50] (Celsius)
  public void setSetting(int setting) throws TemperatureOutofBoundsException {
    if(aStatus.equals(Status.OFF)) {
      turnOn();
    }
    if (setting > 50) {
      throw new TemperatureOutofBoundsException("Temperature is too high");
    } else if (setting < 0) {
      throw new TemperatureOutofBoundsException("Temperature is too low");
    } else {
      this.setting.set(setting);
      this.savedSetting = setting;
    }
  }
  
  public StringProperty getStatusAsString() {
    return statusObsStr;
  }
  
  public IntegerProperty getSetting() {
    return setting;
  }
  
  public void turnOn() {
    super.turnOn();
    setting.set(savedSetting);
    statusObsStr.set(aStatus.toString());
  }
  
  public void turnOff() {
    super.turnOff();
    setting.set(0);
    statusObsStr.set(aStatus.toString());
  }
}