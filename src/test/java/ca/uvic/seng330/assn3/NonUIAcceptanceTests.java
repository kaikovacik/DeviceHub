package ca.uvic.seng330.assn3;

import org.junit.Before;
import org.junit.Test;
import ca.uvic.seng330.assn3.*;

import org.slf4j.impl.StaticLoggerBinder;

public class NonUIAcceptanceTests {

  @Before
  public void initializeSystem() {
    
  }
  
  
  @Test
  public void testShitWorks() {
    System.out.println("hi");
    //return true;
  }

  @Test
  public void testObjectsExist() {

    System.out.println("Test Objects Exist");
/*
    HubController hubC = new HubController();
    Client client = new Client(hubC);

    Camera camera1 = new Camera(hubC);
    System.out.println(hubC.clients.toString());
    
    Thermostat thermostat1 = new Thermostat(hubC);
    Lightbulb lightbulb1 = new Lightbulb(hubC);
    SmartPlug smartplug1 = new SmartPlug(hubC);
    */
    /*
    try {
      hubC.register(camera1);
      hubC.register(thermostat1);
      hubC.register(lightbulb1);
      hubC.register(smartplug1);
      System.out.println("ok");
    } catch (HubRegistrationException e) {
      // TODO Auto-generated catch block
      System.out.println(e.getMessage());
    }
     */

  }

}
