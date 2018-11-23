
// public class CameraModel extends DeviceModel {

//   private BooleanProperty isThisRecording = new SimpleBooleanProperty();
//   private SimpleIntegerProperty diskSizeRemaining = new SimpleIntegerProperty();

//   private static final int maxMem = 5;

//   public CameraModel() {
//     this.aID = UUID.randomUUID();
//     this.diskSizeRemaining.set(maxMem);
//     this.isThisRecording.set(false);
//     this.aStatus = Status.OFF;
//   }

//   public IntegerProperty getDiskSize() {
//     return diskSizeRemaining;
//   }

//   public int getMaxMem() {
//     return maxMem;
//   }

//   protected void setDiskSize() {
//     diskSizeRemaining.set(maxMem);
//   }

//   public void decrementDiskSize() {
//     diskSizeRemaining.set(diskSizeRemaining.intValue() - 1);
//   }

//   // the recordingLabel is bound to this.
//   public final BooleanProperty isThisRecordingProperty() {
//     return isThisRecording;
//   }

//   public final boolean getIsRecording() {
//     return isThisRecording.get();
//   }

//   public final void setIsRecording(final boolean bool) {
//     this.isThisRecording.set(bool);
//   }

//   @Override
//   public final void turnOff() {
//     super.turnOff();
//     setIsRecording(false);
//   }
// }

package ca.uvic.seng330.assn3.devices;

import java.util.UUID;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LightbulbModel extends DeviceModel {

  public LightbulbModel(int id) {
    super(id);
    this.aID = UUID.randomUUID();
    this.aStatus = Status.OFF;
    this.statusObsStr = new SimpleStringProperty(aStatus.toString());
  }
  
  public void toggle() {
    if (aStatus == Status.OFF) {
      aStatus = Status.NORMAL;
      statusObsStr.set(aStatus.toString());
    }
    else if (aStatus == Status.NORMAL) {
      aStatus = Status.OFF;
      statusObsStr.set(aStatus.toString());
    }
    
  }
}