package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;

public class LightbulbModel extends DeviceModel {

  public LightbulbModel(Organizer organizer) {
    super(organizer);
  }
  
  public void toggle() {
    if (aStatus.equals(Status.OFF)) {
      turnOn();
    }
    else if (aStatus.equals(Status.NORMAL)) {
      turnOff();
    }else {
      System.out.println("Error");
    } 
  }
  
  @Override
  public String toString() {
    return "Lightbulb (MODEL)"; 
  }
}