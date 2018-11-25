package ca.uvic.seng330.assn3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ca.uvic.seng330.assn3.devices.DeviceModel;
import ca.uvic.seng330.assn3.devices.DeviceView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Organizer{

  private HashMap<Integer, DeviceView> viewList;
  private HashMap<String, User> userList;
  private final Logger log;
  private SimpleStringProperty lastAllert = new SimpleStringProperty();

  private final DataPersister dP;

  public int deviceCount;
  //  public IntegerProperty deviceCountProperty; 

  public Organizer() {
    this.deviceCount = 0;
    this.viewList = new HashMap<>();
    this.userList = new HashMap<>();
    this.log = LoggerFactory.getLogger(Organizer.class);
    this.dP = new DataPersister();
  }

  public Collection<DeviceView> getViews() {
    return viewList.values();
  }
  
  public HashMap<String, User> getUsers() {
    return userList;
  }

  public void log(String message) {
    log.info(message + "seth");

  }

  //add list to alert
  public void alert(DeviceModel model, String message) {
    JSONMessaging jsonO = new JSONMessaging(model, message);
    jsonO.invoke();
    System.out.println(jsonO.getJSON());
    lastAllert.set(message);
    dP.writeThis(jsonO.getJSON());
  }

  public SimpleStringProperty getLastAllert() {
    return lastAllert;
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

  public void startup() {
    for (/*DeviceView*/Object device : viewList.values()) {
      // device.model.turnOn (or something of the sort)
      // alert(device, (device.getClass().toString() + " (" + device.getIdentifier().toString() + ") is now operational"));
    }
  }

  public void shutdown() {
    for (DeviceView deviceView : viewList.values()) {
      deviceView.getModel().turnOff();
      alert(deviceView.getModel(), (deviceView.getClass().toString() + " (" + deviceView.getModel().getIdentifier() + ") shutdown"));
    }
    lastAllert.set("System Shutdown");
  }

  public void unregister(String entry) throws HubRegistrationException {

    int id = Integer.parseInt(entry);

    if (deviceCount > 0 && viewList.containsKey(id)) {
      alert(viewList.get(id).getModel(), ("Device (" + id + ") removed"));
      viewList.remove(id);
    } else {
      throw new HubRegistrationException("Specified device is not in the network");
    }
  }

  public int numOfDevices() {
    return viewList.size();
  }
}
