package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.devices.*;

public interface Mediator {

  public void log(String message);

  public void alert(DeviceModel device, String message);

  public void register(DeviceModel device) throws HubRegistrationException;

  public void startup();

  public void shutdown();

  public int numOfDevices();

  public void unregister(DeviceModel device) throws HubRegistrationException;

  public void addClient(Client client);
}