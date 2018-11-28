package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ThermostatModel extends DeviceModel {
  
  public class TemperatureOutofBoundsException extends Exception {
    private static final long serialVersionUID = 1L;
    public TemperatureOutofBoundsException(String message) {
      super(message);
    }
  }

  private IntegerProperty setting;
  private int savedSetting;

  public ThermostatModel(Organizer organizer) {
    super(organizer);
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
  
  public IntegerProperty getSetting() {
    return setting;
  }
  
  public void turnOn() {
    super.turnOn();
    setting.set(savedSetting);
  }
  
  public void turnOff() {
    super.turnOff();
    setting.set(0);
  }
  
  @Override
  public String toString() {
    return "[Camera: " + id + "]"; 
  }
}