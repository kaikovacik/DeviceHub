
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




package ca.uvic.seng330.assn3.MVCtestingDevices;

import java.util.UUID;

import ca.uvic.seng330.assn3.Status;
import ca.uvic.seng330.assn3.devices.CameraFullException;
import ca.uvic.seng330.assn3.MVCtesting.Organizer;
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

  public ThermostatModel() {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.OFF;
    this.statusObsStr = new SimpleStringProperty(aStatus.toString());
    this.setting = new SimpleIntegerProperty(0);
    this.savedSetting = 0;
  }

  // Valid temperatures are [0 - 50] (Celsius)
  public void setSetting(int setting) throws TemperatureOutofBoundsException {
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