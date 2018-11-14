package ca.uvic.seng330.assn3.MVCtestingDevices;

import java.util.UUID;

import ca.uvic.seng330.assn3.Status;
import ca.uvic.seng330.assn3.MVCtesting.HubRegistrationException;
import ca.uvic.seng330.assn3.MVCtesting.Organizer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class CameraController {
  public final SimpleStringProperty aStatus = new SimpleStringProperty();
  private final CameraModel model ;
  private Organizer organizer;

  public CameraController(CameraModel model, Organizer pOrganizer) {
    this.model = model;
    this.organizer = pOrganizer;
    aStatus.set(model.getStatus().toString());

    try { // registration is here because CamController needs to know about organizer to alert it anyways.
      organizer.register(model);
    } catch (HubRegistrationException e) {
      System.out.println("Error Line " + new Exception().getStackTrace()[0].getLineNumber());
      e.printStackTrace();
    }
  }

  public  final void record() {
    if( model.getIsRecording() ) {
      model.setIsRecording(false);
      model.decrementDiskSize();

    }else if(model.getDiskSize().intValue() > 0 ){
      model.setIsRecording(true);
    }else {
      model.setStatus(Status.ERROR);
      aStatus.set(model.getStatus().toString());
      organizer.alert(model, "Camera is full!");
     
    }
  }
  
  // Where Record() toggles between recording,
  // stopRecording, as it suggests, ONLY stops.
  public final void stopRecording() {
    if( model.getIsRecording() ) {
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
  }
}