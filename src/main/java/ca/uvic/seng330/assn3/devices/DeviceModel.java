package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.Status;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class DeviceModel {

  protected Status aStatus;
  public IntegerProperty id;
  protected StringProperty statusObsStr;
  protected Organizer organizer;
  private String name;
  
  public DeviceModel(Organizer organizer) {
    this.id = new SimpleIntegerProperty();
    this.id.set(organizer.deviceCount);
    this.organizer = organizer;
    this.statusObsStr = new SimpleStringProperty();
    setStatus(Status.OFF);
    
    this.name = this.getClass().getSimpleName().toString();
    name = name.substring(0, name.length() - 5);
    
  }
  
  public String getName() {
    return name;
  }
  
  public void setStatus(Status pStatus) {
    aStatus = pStatus;
    statusObsStr.set(aStatus.toString());
  }
  
  public Status getStatus() {
    return aStatus;
  }
  
  public StringProperty getStatusAsString() {
    return statusObsStr;
  }

  public int getIdentifier() {
    return id.intValue();
  }
  
  public void turnOn() {
    if(aStatus.equals(Status.NORMAL)) {
      return;
    }
    setStatus(Status.NORMAL);
    organizer.log(this, name + " (" + id.get() + ") turned on");
    //System.out.println(this.getClass().getName());
  }
  
  public void turnOff() {
    if(aStatus.equals(Status.OFF)) {
      return;
    }
    setStatus(Status.OFF);
    organizer.log(this, name + " (" + id.get() + ") turned off");
  }
}