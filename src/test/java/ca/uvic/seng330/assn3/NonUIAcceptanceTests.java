package ca.uvic.seng330.assn3;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import ca.uvic.seng330.assn3.*;
import ca.uvic.seng330.assn3.devices.SmartPlugModel;
import ca.uvic.seng330.assn3.devices.SmartPlugView;

import org.slf4j.impl.StaticLoggerBinder;

public class NonUIAcceptanceTests {

  private Organizer organizer;
  @Before
  public void initializeSystem() {
    organizer = new Organizer();


  }


  @Test
  public void testF_SmartPlug() {
    SmartPlugModel SmartPlugModel = new SmartPlugModel(organizer);
    SmartPlugView SmartPlugView = new SmartPlugView(SmartPlugModel, organizer);
   
    assertTrue("m",SmartPlugModel.getStatus().equals(Status.OFF));
    SmartPlugModel.toggle();
    assertTrue("m",SmartPlugModel.getStatus().equals(Status.NORMAL));
    
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
