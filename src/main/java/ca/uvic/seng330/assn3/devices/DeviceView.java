package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;

public abstract class DeviceView {
  
  private DeviceModel model;
  private Organizer organizer;
  private int id;
  
  public DeviceView(Organizer organizer ) {
    this.organizer = organizer;
    this.id = organizer.deviceCount+1;
  }
  
  public Organizer getOrganizer() {
    return organizer;
  }
  
  public DeviceModel getModel() {
    return model;
  }
  
  public void setModel(DeviceModel pModel) {
    model = pModel;
  }
  
  public int getId() {
    return id;
  }
  
  public String toString() {
    return "[Device: " + getId() + "]"; 
  }
}
