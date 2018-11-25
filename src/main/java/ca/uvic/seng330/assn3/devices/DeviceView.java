package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;

public abstract class DeviceView {
  
  private DeviceModel model;
  private Organizer organizer;
  
  public DeviceView(Organizer organizer ) {
    this.organizer = organizer;
    //organizer.deviceCount++;
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
}