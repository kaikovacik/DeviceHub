package ca.uvic.seng330.assn3.sethMVCtesting;

import ca.uvic.seng330.assn3.Status;
import ca.uvic.seng330.assn3.devices.CameraFullException;
import javafx.beans.property.BooleanProperty;
/*
 * Code sample from https://stackoverflow.com/questions/36868391/using-javafx-controller-without-fxml/36873768
 */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CameraModel extends DeviceModel{

  private BooleanProperty isThisRecording = new SimpleBooleanProperty();
  private int diskSizeRemaining;

  public CameraModel() {
    diskSizeRemaining  = 3;
    isThisRecording.set(false);
    aStatus = Status.NORMAL;
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