package ca.uvic.seng330.assn3;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import ca.uvic.seng330.assn3.*;
import ca.uvic.seng330.assn3.devices.*;
import ca.uvic.seng330.assn3.devices.ThermostatModel.TemperatureOutofBoundsException;

import org.slf4j.impl.StaticLoggerBinder;

public class NonUIAcceptanceTests {

  private Organizer organizer;
  @Before
  public void initializeSystem() {
    organizer = new Organizer();
  }

  @Test
  public void testD_Thermostat() {
    ThermostatModel model = new ThermostatModel(organizer);
    int temp = 5;
    assertTrue("failure message",organizer.numOfDevices() == 1);
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
    
    try {
      model.setSetting(temp);
    } catch (TemperatureOutofBoundsException e) {
      e.printStackTrace();
      fail();
    }
    
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    assertTrue("failure message",model.getSetting().intValue() == temp);
    
    //assertTrue("failure message",model.getStatus().equals(Status.OFF));
  }
  
  @Test
  public void testE_Lightbulb() {
    LightbulbModel model = new LightbulbModel(organizer);
    
    assertTrue("failure message",organizer.numOfDevices() == 1);
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
    model.toggle();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    model.toggle();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
  }
  
  @Test
  public void testF_SmartPlug() {
    SmartPlugModel model = new SmartPlugModel(organizer);
    
    assertTrue("failure message",organizer.numOfDevices() == 1);
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
    model.toggle();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    model.toggle();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
  }
}
