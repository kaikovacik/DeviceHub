package ca.uvic.seng330.assn3.sethMVCtestingDevices;

import java.util.UUID;

import ca.uvic.seng330.assn3.Status;
import ca.uvic.seng330.assn3.devices.CameraFullException;
import ca.uvic.seng330.assn3.sethMVCtesting.Organizer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CameraModel extends DeviceModel{

  private BooleanProperty isThisRecording = new SimpleBooleanProperty();
  private int diskSizeRemaining;
  private Organizer organizer;

  public CameraModel(Organizer pOrganizer) {
    this.aID = UUID.randomUUID();
    this.diskSizeRemaining = 2;
    this.isThisRecording.set(false);
    this.aStatus = Status.NORMAL;
    this.organizer = pOrganizer; 
  }

  public int getDiskSize() {
    return diskSizeRemaining;
  }

  public void decrementDiskSize() {
    diskSizeRemaining--;
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