package ca.uvic.seng330.assn3.devices;

import java.util.UUID;

import ca.uvic.seng330.assn3.HubRegistrationException;
import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SmartPlugModel extends DeviceModel {

  public SmartPlugModel(int id) {
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