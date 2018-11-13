package ca.uvic.seng330.assn3.sethMVCtestingDevices;

import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class CameraController {
  public final SimpleStringProperty aStatus = new SimpleStringProperty();
  private final CameraModel model ;
  

  public CameraController(CameraModel model) {
    this.model = model ;
    aStatus.set(model.getStatus().toString());
  }

  public  final void record() {
    if( model.getIsRecording() ) {
      model.setIsRecording(false);
      model.decrementDiskSize();

    }else if( model.getDiskSize() > 0 ){
      model.setIsRecording(true);
    }else {
      model.setStatus(Status.ERROR);
      aStatus.set(model.getStatus().toString());
    }
  }
  
  //This and method 'isModelRecordingProperty' is some bad code duplication
  public final BooleanProperty isModelRecordingProperty() {
    return model.isThisRecordingProperty();
  }

}