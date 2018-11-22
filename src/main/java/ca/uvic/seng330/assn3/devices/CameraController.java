package ca.uvic.seng330.assn3.devices;

import java.util.UUID;

import ca.uvic.seng330.assn3.HubRegistrationException;
import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CameraController {
  public final SimpleStringProperty aStatus = new SimpleStringProperty();
  private final CameraModel model;
  private Organizer organizer;

  public CameraController(CameraModel model, Organizer pOrganizer) {
    this.model = model;
    this.organizer = pOrganizer;
    aStatus.set(model.getStatus().toString());

  }

  public final void record() {
    if (model.getStatus().equals(Status.OFF)) {
      model.turnOn();
    }
    if (model.getIsRecording()) {
      model.setIsRecording(false);
      model.decrementDiskSize();

    } else if (model.getDiskSize().intValue() > 0) {
      model.setIsRecording(true);
    } else {
      model.setStatus(Status.ERROR);
      aStatus.set(model.getStatus().toString());
      organizer.alert(model, "Camera is full!");
     
    }
  }
  
  // Where Record() toggles between recording,
  // stopRecording, as it suggests, ONLY stops.
  public final void stopRecording() {
    if (model.getIsRecording()) {
      model.setIsRecording(false);
      model.decrementDiskSize();
    }
  }
  
  //Next two methods are bad code duplication
  public final BooleanProperty isModelRecordingProperty() {
    return model.isThisRecordingProperty();
  }
  
  public final IntegerProperty diskSpaceLeft() {
    return model.getDiskSize();
  }
  
  // A listener still needs to be added to the isObject boolean. The listener will throw the alert
  public final void setIsObject(boolean b) {
    model.setIsObject(b);
    if (b) organizer.alert(model, ("Camera (" + model.getIdentifier().toString() + ") detected an object!"));
  }
  
  public final void turnOff() {
    stopRecording();
    model.turnOff();
    aStatus.set("OFF");
  }
  
  public final void turnOn() {
    model.turnOn();
    aStatus.set("NORMAL");
  }
  
  public void resetMemory() {
    stopRecording();
    model.resetDiskSize();
    model.setStatus(Status.NORMAL);
    aStatus.set("NORMAL");
    //System.out.println(model.getStatus());
  }
  
  public DeviceModel getModel() {
    return model;
  }
  
  public UUID getID() {
    return model.getID();
  }
}