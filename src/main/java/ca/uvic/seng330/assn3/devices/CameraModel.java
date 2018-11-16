package ca.uvic.seng330.assn3.devices;

import java.util.UUID;

import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CameraModel extends DeviceModel{

  private BooleanProperty isThisRecording = new SimpleBooleanProperty();
  private SimpleIntegerProperty diskSizeRemaining = new SimpleIntegerProperty();
  private boolean isObject;
  private static final int maxMem = 3;

  public CameraModel() {
    this.aID = UUID.randomUUID();
    this.diskSizeRemaining.set(maxMem);
    this.isThisRecording.set(false);
    this.aStatus = Status.OFF;
    this.isObject = false;
  }

  public IntegerProperty getDiskSize() {
    return diskSizeRemaining;
  }

 // public int getMaxMem() {
  //  return maxMem;
  //}
  
  public void setIsObject(boolean b) {
    isObject = b;
  }

  protected void resetDiskSize() {
    diskSizeRemaining.set(maxMem);
  }

  public void decrementDiskSize() {
    diskSizeRemaining.set(diskSizeRemaining.intValue()-1);
  }

  // the recordingLabel is bound to this.
  public final BooleanProperty isThisRecordingProperty() {
    return isThisRecording;
  }

  public final boolean getIsRecording() {
    return isThisRecording.get();
  }

  public final void setIsRecording(final boolean bool) {
    this.isThisRecording.set(bool);
  }

}