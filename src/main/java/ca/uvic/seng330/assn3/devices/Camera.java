package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.*;
import java.util.UUID;

public class Camera extends Device {

  private Status status;
  private UUID id;
  private Mediator network;
  private boolean isRecording;
  private int diskSize;

  public Camera(Mediator network) {
    this.id = UUID.randomUUID();
    this.status = Status.NORMAL;
    this.isRecording = false;
    this.diskSize = 100;
    this.network = network;
    try {
      this.network.register(this);
      network.alert(this, ("Camera (" + this.id.toString() + ") added to network"));
    } catch (HubRegistrationException e) {
      return;
    }
  }

  public Camera() {
    this.id = UUID.randomUUID();
    this.status = Status.NORMAL;
    this.isRecording = false;
    this.diskSize = 100;
  }

  public void record() throws CameraFullException {
    if (diskSize > 0) {
      isRecording = !isRecording; 
      if (!isRecording) diskSize--;
    } else {
      status = Status.CRITICAL;
      throw new CameraFullException("Camera is full!");
    }
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public UUID getIdentifier() {
    return id;
  }

  public Status getStatus() {
    return status;
  }
}