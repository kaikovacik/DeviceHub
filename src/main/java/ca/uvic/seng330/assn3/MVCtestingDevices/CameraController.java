package ca.uvic.seng330.assn3.MVCtestingDevices;

import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class CameraController {
  public final SimpleStringProperty aStatus = new SimpleStringProperty();
  private final CameraModel model ;
  

  public CameraController(CameraModel model) {
    this.model = model;
    aStatus.set(model.getStatus().toString());
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
  
  //This and method 'isModelRecordingProperty' is some bad code duplication
  public final BooleanProperty isModelRecordingProperty() {
    return model.isThisRecordingProperty();
  }
  
  // So is this
  public final IntegerProperty diskSpaceLeft() {
    return model.getDiskSize();
  }
  
  public final int diskSpace() {
    return model.getMaxMem();
  }
  
  public final void turnOff() {
    model.turnOff();
    aStatus.set("OFF");
  }
  
  public final void turnOn() {
    model.turnOn();
    aStatus.set("NORMAL");
  }
  
  public void resetMemory() {
    stopRecording();
    model.setDiskSize();
  }

}