package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.*;
import java.util.UUID;

/* Device was initally a superclass
 * That implemented these three
 * methods for its subclasses.
 * Chanege was made to adhere to 
 * the tests. */
public interface Device {

  public void setStatus(Status status);

  public UUID getIdentifier();

  public Status getStatus();
}