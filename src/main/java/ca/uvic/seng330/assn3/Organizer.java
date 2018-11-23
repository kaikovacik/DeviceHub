package ca.uvic.seng330.assn3;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uvic.seng330.assn3.devices.CameraModel;
import ca.uvic.seng330.assn3.devices.DeviceModel;
import javafx.beans.property.SimpleStringProperty;

public class Organizer{

  private HashMap<Integer, DeviceModel> modelRegistry;
  private HashMap<Integer, Object> viewList;
  //public LinkedList<Client> clients;
  private final Logger log;
  private SimpleStringProperty lastAllert = new SimpleStringProperty();
  
  public int deviceCount;

  public Organizer() {
    this.deviceCount = 1;
    this.modelRegistry = new HashMap<>();
    this.viewList = new HashMap<>();
    //this.clients = new LinkedList<>();
    this.log = LoggerFactory.getLogger(Organizer.class);
  }

  public void addView(Object view) {
    viewList.put(deviceCount, view);
  }

  // should be deep copy
//  public LinkedList getViews() {
//    return viewList;
//  }
  
  public Collection<Object> getViews() {
    return viewList.values();
  }

  public void log(String message) {
    log.info(message);
  }

  //add list to alert
  public void alert(DeviceModel deviceModel, String message) {
    JSONMessaging json = new JSONMessaging(deviceModel, message);
    System.out.println(json.invoke());
    lastAllert.set(message);

    //this is the camera allerting lightbulb of a person thing
    Object obj = deviceModel;
    if (obj instanceof CameraModel) {
      for (Object model : modelRegistry.values()) {
        //TODO fix this
        //if (model instanceof Lightbulb) ((Lightbulb) model).toggle();
      }
    }  
  }

  public SimpleStringProperty getLastAllert() {
    return lastAllert;
  }

  public void register(DeviceModel device) throws HubRegistrationException {
    try {
      modelRegistry.put(deviceCount, device);
    } catch (Exception e) {
      System.out.println("reg ex");
      throw new HubRegistrationException((device == null) ? "Invalid device" : "Unable to add this device");

    }
  }

  /*
   * Iterates through all devices in the registry, setting their status' to NORMAL
   * and alerting the clients that each device is now operational.
   */
  public void startup() {
    for (DeviceModel device : modelRegistry.values()) {
      device.turnOn();
      alert(device, (device.getClass().toString() + " (" + device.getIdentifier().toString() + ") is now operational"));
    }
  }

  /*
   * Iterates through all devices in the registry, setting their status' to DISABLED
   * and alerting the clients that each device is no longer operational. 
   */
  public void shutdown() {
    for (DeviceModel device : modelRegistry.values()) {
      device.turnOff();
      alert(device, (device.getClass().toString() + " (" + device.getIdentifier().toString() + ") is no longer operational"));
    }
  }

  public int numOfDevices() {
    return modelRegistry.size();
  }

  public void unregister(String entry) throws HubRegistrationException {

//        if (modelRegistry.containsKey(device.getIdentifier())) {
//          modelRegistry.remove(device.getIdentifier(), device);
//        } else {
//      throw new HubRegistrationException("Specified device is not in the network");
//        }

//    UUID uuid = UUID.fromString(id);
    int id = Integer.parseInt(entry);
    System.out.println(modelRegistry.keySet());
    if (deviceCount > 0 && modelRegistry.containsKey(id)) {
      modelRegistry.remove(id);
      viewList.remove(id);
    } else {
      throw new HubRegistrationException("Specified device is not in the network");
    }
  }

  //  public void addClient(Client client) {
  //    clients.add(client);
  //  }

  // TODO: We should make this return a deep copy.
  public HashMap<Integer, DeviceModel> getDevices() {
    return modelRegistry;
  }
}
