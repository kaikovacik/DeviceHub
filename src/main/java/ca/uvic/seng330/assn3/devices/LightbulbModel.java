package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.SimpleStringProperty;

public class LightbulbModel extends DeviceModel {

  public LightbulbModel(Organizer organizer) {
    super(organizer);
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