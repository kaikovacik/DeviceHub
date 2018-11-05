package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.devices.DeviceModel;
import java.util.LinkedList;

public class BasicUser extends AbstractUser{
  
  private LinkedList<DeviceModel> followedDevices;
  
  // Adds a device for the user to follow/receive notifications from
  public void addFollowedDevice(DeviceModel pDeviceModel) {
    followedDevices.add(pDeviceModel);
  }
  
  public void removeFollowedDevice(DeviceModel pDeviceModel) {
    followedDevices.remove(pDeviceModel);
  }
  
}
