package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.devices.*;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.HashMap;
import java.util.LinkedList;

public class Hub implements Mediator {

  private HashMap<UUID, Device> registry;
  private LinkedList<Client> clients;
  private final Logger log;

  public Hub() {
    this.registry = new HashMap<>();
    this.clients = new LinkedList<>();
    this.log = LoggerFactory.getLogger(Hub.class);
  }

  public void log(String message) {
    log.info(message);
  }

  public void alert(Device device, String message) {
    JSONObject json = new JSONMessaging(device , message).invoke();

    for (Client client : clients) {
        client.notify(json);
    }
  }

  public void register(Device device) throws HubRegistrationException {
    try {
      registry.put(device.getIdentifier(), device);
    } catch (Exception e) {
      throw new HubRegistrationException((device == null)? "Invalid device" : "Unable to add this device");
    };
  }

  /*
   * Iterates through all devices in the registry, setting their status' to NORMAL
   * and alerting the clients that each device is now operational.
   */
  public void startup() {
    for (Device device : registry.values()) {
      device.setStatus(Status.NORMAL); // use .turnOn()
      alert(device, (device.getClass().toString() + " (" + device.getIdentifier().toString() + ") is now operational"));
    }
  }

  /*
   * Iterates through all devices in the registry, setting their status' to DISABLED
   * and alerting the clients that each device is no longer operational. 
   */
  public void shutdown() {
    for (Device device : registry.values()) {
      device.setStatus(Status.DISABLED); // use .shutDown()
      alert(device, (device.getClass().toString() + " (" + device.getIdentifier().toString() + ") is no longer operational"));
    }
  }

  public int numOfDevices() {
    return registry.size();
  }

  public void unregister(Device device) throws HubRegistrationException {
    String id = device.getIdentifier().toString();

    if (registry.containsKey(id)) {
      registry.remove(id, device);
    } else {
      throw new HubRegistrationException("Specified device is not in the network");
    }
  }

  public void addClient(Client client) {
    clients.add(client);
  }

  public HashMap<UUID, Device> getDevices() {
    return registry;
  }
}
