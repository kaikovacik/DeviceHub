package ca.uvic.seng330.assn3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import ca.uvic.seng330.assn3.*;
import ca.uvic.seng330.assn3.devices.*;
import ca.uvic.seng330.assn3.devices.ThermostatModel.TemperatureOutofBoundsException;


public class NonUIAcceptanceTests {

  private Organizer organizer;
  
  @Before
  public void initializeSystem() {
    organizer = new Organizer();
  }

  @Test
  public void testA_Users() {
    // Place holder, to be added
  }
  
  @Test
  public void testB_Hub() {
    CameraView cView = new CameraView(organizer);
    ThermostatView tView = new ThermostatView(organizer);
    LightbulbView lView = new LightbulbView(organizer);
    SmartPlugView sPView1 = new SmartPlugView(organizer);
    SmartPlugView sPView2 = new SmartPlugView(organizer);
    SmartPlugView sPView3 = new SmartPlugView(organizer);
    
    assertTrue("failure message", organizer.getViews().contains(sPView2));
    assertTrue("failure message", sPView2.getModel().id.equals(5));
    
    try {
      organizer.register(cView);
      
      //
    } catch (HubRegistrationException e) {
      // TODO Auto-generated catch block
      fail();
    }
    assertEquals(organizer.numOfDevices(), 6);
    
    try {
      organizer.unregister(cView.getModel().getIdentifier());
    } catch (HubRegistrationException e) {
      e.printStackTrace();
      fail();
    }
    assertEquals(organizer.numOfDevices(), 5);
    
    try {
      organizer.unregister(lView.getModel().getIdentifier());
      // unregister nonexistant device
      organizer.unregister(cView.getModel().getIdentifier());
      fail();
    } catch (HubRegistrationException e) {
      // TODO Auto-generated catch block
    }
    assertEquals(organizer.numOfDevices(), 4);
    
    // test status off initially
    for( DeviceView d : organizer.getViews().values()) {
      if(d.getModel().getStatus().equals(Status.OFF)) {
        
      }else {
        fail();
      }
    }
    
    // test startup
    organizer.startup();
    for( DeviceView d : organizer.getViews().values()) {
      if(d.getModel().getStatus().equals(Status.NORMAL)) {
        
      }else {
        fail();
      }
    }
    
    // test shutdown
    organizer.shutdown();
    for( DeviceView d : organizer.getViews().values()) {
      if(d.getModel().getStatus().equals(Status.OFF)) {
        
      }else {
        fail();
      }
    }

  }

  @Test
  public void testC_Camera() {
    int space = 3;
    CameraModel model = new CameraModel(organizer);

    //assertTrue("failure message",organizer.numOfDevices() == 1);
    assertTrue("failure message",model.getStatus().equals(Status.OFF));

    assertEquals(model.diskSizeRemaining(), space);
    model.record();
    assertEquals(model.getStatus(), Status.NORMAL);
    assertEquals(model.isThisRecording(), true);
    assertEquals(model.getIsRecording(), true);

    model.record(); // stop recording
    assertEquals(model.getStatus(), Status.NORMAL);
    assertEquals(model.isThisRecording(), false);
    assertEquals(model.getIsRecording(), false);
    // test decrement of disk size
    assertEquals(model.diskSizeRemaining().intValue(), space-1);

    model.record(); // start
    model.record();
    model.record(); // start record
    model.record(); // stop last record

    // test recording when full
    model.record();
    assertEquals(model.getStatus(), Status.ERROR);
    assertEquals(model.isThisRecording(), false);
    assertEquals(model.getIsRecording(), false);
    assertEquals(model.diskSizeRemaining(), 0);

    // test memory reset method
    model.resetMemory();
    assertEquals(model.getStatus(), Status.NORMAL);
    assertEquals(model.isThisRecording(), false);
    assertEquals(model.getIsRecording(), false);
    assertEquals(model.diskSizeRemaining(), space);

    model.turnOff();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
    model.turnOn();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    
  }

  @Test
  public void testD_Thermostat() {
    ThermostatModel model = new ThermostatModel(organizer);
    //ThermostatView view = new ThermostatView(model, organizer);
    int temp = 5;

    //assertTrue("failure message",organizer.numOfDevices() == 1);
    assertTrue("failure message",model.getStatus().equals(Status.OFF));

    try {
      model.setSetting(temp);
    } catch (TemperatureOutofBoundsException e) {
      e.printStackTrace();
      fail();
    }

    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    assertTrue("failure message",model.getSetting().intValue() == temp);
    model.turnOff();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
    model.turnOn();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
  }

  @Test
  public void testE_Lightbulb() {
    LightbulbModel model = new LightbulbModel(organizer);

    //assertTrue("failure message",organizer.numOfDevices() == 1);
    //System.out.println(model.getStatus());
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
    model.toggle();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    model.toggle();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));

    model.turnOn();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    model.turnOff();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
  }

  @Test
  public void testF_SmartPlug() {
    SmartPlugModel model = new SmartPlugModel(organizer);

    //assertTrue("failure message",organizer.numOfDevices() == 1);
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
    model.toggle();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    model.toggle();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));

    model.turnOn();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    model.turnOff();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
  }
}