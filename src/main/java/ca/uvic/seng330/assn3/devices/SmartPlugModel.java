package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.SimpleStringProperty;

public class SmartPlugModel extends DeviceModel {

  public SmartPlugModel(Organizer organizer) {
    super(organizer);
    this.aStatus = Status.OFF;
    this.statusObsStr = new SimpleStringProperty(aStatus.toString());
  }
  
  public void toggle() {
    if (aStatus == Status.OFF) {
      turnOn();
    }
    else if (aStatus == Status.NORMAL) {
      turnOff();
    }
    else{
      System.out.println("Error");
    } 
  }
  
  @Override
  public String toString() {
    return "[Smart Plug: MODEL]"; 
  }
}