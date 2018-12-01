package ca.uvic.seng330.assn3;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import ca.uvic.seng330.assn3.devices.DeviceModel;
import ca.uvic.seng330.assn3.devices.DeviceView;
import ca.uvic.seng330.assn3.devices.LightbulbView;
import javafx.beans.property.SimpleStringProperty;

public class Organizer{

  private ConcurrentHashMap<Integer, DeviceView> viewList;

  private HashMap<String, User> userList;
  private SimpleStringProperty lastAllert = new SimpleStringProperty();
  private SimpleStringProperty lastLog = new SimpleStringProperty();
  private final DataPersister dP;
  public int deviceCount;

  public Organizer() {
    this.deviceCount = 0;
    this.viewList = new ConcurrentHashMap<>();
    this.userList = new HashMap<>();
    this.dP = new DataPersister();
  }

  public ConcurrentHashMap<Integer, DeviceView> getViews() {
    return viewList;
  }

  public HashMap<String, User> getUsers() {
    return userList;
  }

  // logs data to file.
  public void log(DeviceModel model, String message) {
    JSONMessaging jsonO = new JSONMessaging(model, message);   
    dP.writeThis(jsonO.getJSON());            // to log file
    lastLog.set(jsonO.getJSON().toString());  // writes to Activity
    System.out.println(jsonO.getJSON());      
  }

  public void logString(String message) {
    String toLog = "{\"payload\":\"" + message + "\",\"created_at\":\"" + new Date() + "\"}";
    dP.writeThis(toLog);  // writes to log file
    lastLog.set(toLog);   // writes to Activity
    //lastAllert.set(message);  // writes to alerts
    System.out.println(toLog);
  }

  //add list to alert
  public void alert(DeviceModel model, String message) {
    log(model, message);
    lastAllert.set(message);    // writes to alerts
    
    if (message.contains("object")) {
      for( DeviceView d : viewList.values()) {
        if (d instanceof LightbulbView) {
          d.getModel().turnOn();
        }
      }
    }
  }

  public SimpleStringProperty getLastAllert() {
    return lastAllert;
  }

  public SimpleStringProperty getLastLog() {
    return lastLog;
  }

  public void addUser(User user) throws HubRegistrationException {
    try {
      userList.put(user.getUsername(), user);
    } catch (Exception e) {
      System.out.println("reg ex");
      throw new HubRegistrationException((user == null)? "Invalid user" : "Unable to add this user");
    }
  }

  public void register(DeviceView deviceView) throws HubRegistrationException {
    try {
      viewList.put(++deviceCount, deviceView);
    } catch (Exception e) {
      System.out.println("reg ex");
      throw new HubRegistrationException((deviceView == null) ? "Invalid device" : "Unable to add this device");
    }
  } 

  public void unregister(int id) throws HubRegistrationException {
    if (deviceCount > 0 && viewList.containsKey(id)) {
      DeviceModel m = viewList.get(id).getModel();
      alert(m, m.getName() + " (" + m.getIdentifier() + ") removed");
      viewList.remove(id);
      for (User u : userList.values()) {
        u.removeDevice(id);
      }
    } else {
      throw new HubRegistrationException("Specified device is not in the network");
    }
  }

  public void startup() {
    for (DeviceView device : viewList.values()) {
      device.getModel().turnOn();
      //alert(device, (device.getClass().toString() + " (" + device.getModel.getIdentifier().toString() + ") is now operational"));
    }
    logString("All devices turned on.");
  }

  public void shutdown() {
    for (DeviceView deviceView : viewList.values()) {
      deviceView.getModel().turnOff();
    }
    logString("All devices shutdown.");
  }

  public void statusCheck() {
    //System.out.format("%s: %s%n", Thread.currentThread().getName(), "stat check");
    String statusString = "Status Check:";
    DeviceModel m;
    if (viewList.size() == 0 ) {
      statusString = statusString.concat(" There are no devices in the system.");
    }else {
      for( DeviceView v : viewList.values()) {
        m = v.getModel();
        statusString = statusString.concat("  -  " + m.getName() + " (" + m.getIdentifier() +"): " + m.getStatus());
      }
    }
    logString(statusString);
  }

  public int numOfDevices() {
    return viewList.size();
  }
}