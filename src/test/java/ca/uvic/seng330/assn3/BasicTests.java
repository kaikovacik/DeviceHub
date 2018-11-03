package ca.uvic.seng330.assn3;

import org.junit.Test;
import ca.uvic.seng330.assn3.*;
import ca.uvic.seng330.assn3.devices.*;

public class BasicTests {
  
  @Test
  public void testShitWorks() {
    System.out.println("hi");
    //return true;
  }
  
  @Test
  public void testObjectsExist() {

    System.out.println("Test Objects Exist");

    HubController hubC = new HubController();
    Camera camera1 = new Camera(hubC);
    Thermostat thermostat1 = new Thermostat(hubC);
    Lightbulb lightbulb1 = new Lightbulb(hubC);
    SmartPlug smartplug1 = new SmartPlug(hubC);

    hubC.register(camera1);
    hubC.register(thermostat1);
    hubC.register(lightbulb1);
    hubC.register(smartplug1);

  }
  
}
