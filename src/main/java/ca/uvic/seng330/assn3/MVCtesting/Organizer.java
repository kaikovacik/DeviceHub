package ca.uvic.seng330.assn3.MVCtesting;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uvic.seng330.assn3.MVCtestingDevices.DeviceModel;

public class Organizer{

  private HashMap<UUID, DeviceModel> registry;
  //public LinkedList<Client> clients;
  private final Logger log;

  public Organizer() {
    this.registry = new HashMap<>();
    //this.clients = new LinkedList<>();
    this.log = LoggerFactory.getLogger(Organizer.class);
  }

  public void log(String message) {
    log.info(message);
  }

  //add list to allert
  public void alert(DeviceModel device, String message) {
    JSONObject json = new JSONMessaging(device, message).invoke();

//    for (Client client : clients) {
//      client.notify(json);
//    }
  }

  public void register(DeviceModel device) throws HubRegistrationException {
    try {
      registry.put(device.getIdentifier(), device);
      alert(device, (device.getClass().getSimpleName() + " (" + device.getIdentifier().toString() + ") added to network"));
    } catch (Exception e) {
      throw new HubRegistrationException((device == null) ? "Invalid device" : "Unable to add this device");
    }
    ;
  }

  /*
   * Iterates through all devices in the registry, setting their status' to NORMAL
   * and alerting the clients that each device is now operational.
   */
  public void startup() {
    for (DeviceModel device : registry.values()) {
      device.turnOn();
      alert(device, (device.getClass().toString() + " (" + device.getIdentifier().toString() + ") is now operational"));
    }
  }

  /*
   * Iterates through all devices in the registry, setting their status' to DISABLED
   * and alerting the clients that each device is no longer operational. 
   */
  public void shutdown() {
    for (DeviceModel device : registry.values()) {
      device.turnOff();
      alert(device, (device.getClass().toString() + " (" + device.getIdentifier().toString() + ") is no longer operational"));
    }
  }

  public int numOfDevices() {
    return registry.size();
  }

  public void unregister(DeviceModel device) throws HubRegistrationException {

    if (registry.containsKey(device.getIdentifier())) {
      registry.remove(device.getIdentifier(), device);
    } else {
      throw new HubRegistrationException("Specified device is not in the network");
    }
  }

//  public void addClient(Client client) {
//    clients.add(client);
//  }

  // TODO: We should make this return a deep copy.
  public HashMap<UUID, DeviceModel> getDevices() {
    return registry;

  }
}
