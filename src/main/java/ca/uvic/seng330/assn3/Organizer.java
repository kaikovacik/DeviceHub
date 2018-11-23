package ca.uvic.seng330.assn3;

import java.util.Collection;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ca.uvic.seng330.assn3.devices.DeviceModel;
import ca.uvic.seng330.assn3.devices.DeviceView;
import javafx.beans.property.SimpleStringProperty;

public class Organizer{

  private HashMap<Integer, DeviceView> viewList;
  private final Logger log;
  private SimpleStringProperty lastAllert = new SimpleStringProperty();
  
  public int deviceCount;

  public Organizer() {
    this.deviceCount = 0;
    this.viewList = new HashMap<>();
    this.log = LoggerFactory.getLogger(Organizer.class);
  }

  public Collection<DeviceView> getViews() {
    return viewList.values();
  }

  public void log(String message) {
    log.info(message);
  }

  //add list to alert
  public void alert(DeviceModel model, String message) {
    JSONMessaging json = new JSONMessaging(model, message);
    System.out.println(json.invoke());
    lastAllert.set(message);
  }

  public SimpleStringProperty getLastAllert() {
    return lastAllert;
  }

  public void register(DeviceView deviceView) throws HubRegistrationException {
    try {
      viewList.put(++deviceCount, deviceView);
    } catch (Exception e) {
      System.out.println("reg ex");
      throw new HubRegistrationException((deviceView == null) ? "Invalid device" : "Unable to add this device");
    }
  } 

  /*
   * Iterates through all devices in the registry, setting their status' to NORMAL
   * and alerting the clients that each device is now operational.
   */
  public void startup() {
    for (/*DeviceView*/Object device : viewList.values()) {
      // device.model.turnOn (or something of the sort)
      // alert(device, (device.getClass().toString() + " (" + device.getIdentifier().toString() + ") is now operational"));
    }
  }

  /*
   * Iterates through all devices in the registry, setting their status' to DISABLED
   * and alerting the clients that each device is no longer operational. 
   */
  public void shutdown() {
    for (/*DeviceView*/Object device : viewList.values()) {
      // device.model.turnOff (or something of the sort)
      // alert(device, (device.getClass().toString() + " (" + device.getIdentifier().toString() + ") is now operational"));
    }
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
}
