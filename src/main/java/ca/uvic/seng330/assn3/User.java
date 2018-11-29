package ca.uvic.seng330.assn3;

import java.util.HashMap;

import ca.uvic.seng330.assn3.devices.DeviceView;

public class User {
  private String username;
  private String password;
  protected HashMap<Integer, DeviceView> deviceList;
  
  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.deviceList = new HashMap<>();
  }
  
  public void changeName(String username) {
    this.username = username;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void addDevice(DeviceView device) {
    deviceList.put(device.getId(), device);
  }
  
  public void removeDevice(int id) {
    deviceList.remove(id);
  }
  
  public HashMap<Integer, DeviceView> getDevices() {
    return deviceList;
  }
  
  public Boolean checkPassword(String password) {
    return this.password.equals(password);
  }
  
  public String toString() {
    return "[USER: " + username +"]";
  }
}
