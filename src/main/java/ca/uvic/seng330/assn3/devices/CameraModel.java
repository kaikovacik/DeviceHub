package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CameraModel extends DeviceModel {

  private BooleanProperty isThisRecordingProperty = new SimpleBooleanProperty();
  private SimpleIntegerProperty diskSizeRemainingProperty = new SimpleIntegerProperty();
  private boolean isObject;
  private static final int maxMem = 3;
  
  public CameraModel(int id) {
    
    super(id);
    this.diskSizeRemainingProperty.set(maxMem);
    this.isThisRecordingProperty.set(false);
    this.aStatus = Status.OFF;
    this.isObject = false;
    this.statusObsStr = new SimpleStringProperty(aStatus.toString());
  }
  
  public IntegerProperty getDiskSize() {
    return diskSizeRemainingProperty;
  }
  
  public void setIsObject(boolean b) {
    isObject = b;
  }

 public final SimpleIntegerProperty diskSizeRemaining() {
   return diskSizeRemainingProperty;
 }
  
   // the recordingLabel is bound to this.
  public final BooleanProperty isThisRecording() {
    return isThisRecordingProperty;
  }

  public final boolean getIsRecording() {
    return isThisRecordingProperty.get();
  }

  public final void setIsRecording(final boolean bool) {
    this.isThisRecordingProperty.set(bool);
  }
  
  public final void record() throws cameraFullException {
    if (aStatus.equals(Status.OFF)) {
      turnOn();
    }
    if (getIsRecording()) {
      setIsRecording(false);
      diskSizeRemainingProperty.set(diskSizeRemainingProperty.intValue() -1);

    } else if (getDiskSize().intValue() > 0) {
      setIsRecording(true);
    } else {
      setStatus(Status.ERROR);
      statusObsStr.set(aStatus.toString());
      throw new cameraFullException("Camera " + getIdentifier() + " is full!");
     
    }
  }
  
  // Where Record() toggles between recording,
  // stopRecording, as it suggests, ONLY stops.
  public final void stopRecording() {
    if (isThisRecordingProperty.get()) {
      isThisRecordingProperty.set(false);
      diskSizeRemainingProperty.set(diskSizeRemainingProperty.intValue() -1);
    }
  }
  
  public void resetMemory() {
    stopRecording();
    diskSizeRemainingProperty.set(maxMem);
    aStatus.equals(Status.NORMAL);
    statusObsStr.set(aStatus.toString());
  }

  public final void turnOff() {
    stopRecording();
    super.turnOff();
  }
    
}