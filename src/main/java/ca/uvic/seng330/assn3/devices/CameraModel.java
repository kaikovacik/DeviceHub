package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CameraModel extends DeviceModel {

  private BooleanProperty isThisRecordingProperty = new SimpleBooleanProperty();
  private SimpleIntegerProperty diskSizeRemainingProperty = new SimpleIntegerProperty();
  private boolean isObject;
  private static final int maxMem = 3;
  
  public CameraModel(Organizer organizer) {
    
    super(organizer);
    this.diskSizeRemainingProperty.set(maxMem);
    this.isThisRecordingProperty.set(false);
    this.isObject = false;
  }
  
  public IntegerProperty getDiskSize() {
    return diskSizeRemainingProperty;
  }
  
  public void setIsObject(boolean b) {
    if ( isObject != b ) {
      isObject = b;
      organizer.alert(this, "Camera (" + getIdentifier() + ") detected an object!");
    }
  }
  
  public boolean getIsObject() {
    return isObject;
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
  
  public final void record() {
    if (aStatus.equals(Status.OFF)) {
      turnOn();
    }
    if (getIsRecording()) {
      setIsRecording(false);
      diskSizeRemainingProperty.set(diskSizeRemainingProperty.intValue()-1);
      organizer.alert(this, "Camera (" + getIdentifier() + ") stopped recording");

    } else if (getDiskSize().intValue() > 0) {
      setIsRecording(true);
      organizer.alert(this, "Camera (" + getIdentifier() + ") started recording");
    } else {
      setStatus(Status.ERROR);
      organizer.alert(this, "Camera (" + getIdentifier() + ") is full!");
      //setIsObject(true);
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
    setStatus(Status.NORMAL);
    statusObsStr.set(aStatus.toString());
  }

  public final void turnOff() {
    stopRecording();
    super.turnOff();
  }
    
  @Override
  public String toString() {
    return "Camera (MODEL)"; 
  }
}