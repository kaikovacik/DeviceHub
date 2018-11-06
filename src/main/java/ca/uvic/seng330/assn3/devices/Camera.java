package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.*;
import java.util.UUID;

public class Camera extends DeviceModel {

  private Mediator network;
  private boolean isRecording;
  private int diskSize;

  public Camera(Mediator network) {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.NORMAL;
    this.isRecording = false;
    this.diskSize = 100;
    this.network = network;
    try {
      this.network.register(this);
    } catch (HubRegistrationException e) {
      return;
    }
  }

  public Camera() {
    this.aID = UUID.randomUUID();
    this.aStatus = Status.NORMAL;
    this.isRecording = false;
    this.diskSize = 100;
  }

  public void record() throws CameraFullException {
    if (diskSize > 0) {
      isRecording = !isRecording; 
      if (!isRecording) diskSize--;   // if camera just turned off, decrement size
    } else {
      aStatus = Status.ERROR;
      throw new CameraFullException("Camera is full!");
    }
  }
  
  public void turnOff() {
    if(isRecording) {
      isRecording = false;
      diskSize--;
    }
    super.turnOff();
  }
}