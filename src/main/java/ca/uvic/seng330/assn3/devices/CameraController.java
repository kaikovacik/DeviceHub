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
   
  public DeviceModel getModel() {
    return model;
  }
  
  public UUID getID() {
    return model.getID();
  }
}