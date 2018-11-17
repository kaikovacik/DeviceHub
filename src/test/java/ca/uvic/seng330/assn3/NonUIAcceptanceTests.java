package ca.uvic.seng330.assn3;

import static org.junit.Assert.assertEquals;
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
  public void testA_Users() {
    // Place holder, to be added
  }
  
  @Test
  public void testB_Hub() {
    CameraModel cModel = new CameraModel();
    CameraController cController = new CameraController(cModel, organizer);
    ThermostatModel tModel = new ThermostatModel(organizer);
    LightbulbModel lModel = new LightbulbModel(organizer);
    SmartPlugModel sPModel1 = new SmartPlugModel(organizer);
    SmartPlugModel sPModel2 = new SmartPlugModel(organizer);
    SmartPlugModel sPModel3 = new SmartPlugModel(organizer);

    
    assertTrue("failure message", organizer.getDevices().containsKey(sPModel2.getIdentifier()));
    
    try {
      organizer.register(cModel);
      
      //
    } catch (HubRegistrationException e) {
      // TODO Auto-generated catch block
      fail();
    }
    assertEquals(organizer.numOfDevices(), 6);
    
    try {
      organizer.unregister(cModel);
    } catch (HubRegistrationException e) {
      e.printStackTrace();
      fail();
    }
    assertEquals(organizer.numOfDevices(), 5);
    
    try {
      organizer.unregister(lModel);
      // unregister nonexistant device
      organizer.unregister(cModel);
      fail();
    } catch (HubRegistrationException e) {
      // TODO Auto-generated catch block
    }
    assertEquals(organizer.numOfDevices(), 4);
    
    // test status off initially
    for( DeviceModel d : organizer.getDevices().values()) {
      if(d.getStatus().equals(Status.OFF)) {
        
      }else {
        fail();
      }
    }
    
    // test startup
    organizer.startup();
    for( DeviceModel d : organizer.getDevices().values()) {
      if(d.getStatus().equals(Status.NORMAL)) {
        
      }else {
        fail();
      }
    }
    
    // test shutdown
    organizer.shutdown();
    for( DeviceModel d : organizer.getDevices().values()) {
      if(d.getStatus().equals(Status.OFF)) {
        
      }else {
        fail();
      }
    }

  }

  @Test
  public void testC_Camera() {
    int space = 3;
    CameraModel model = new CameraModel();
    CameraController controller = new CameraController(model, organizer);

    assertTrue("failure message",organizer.numOfDevices() == 1);
    assertTrue("failure message",model.getStatus().equals(Status.OFF));

    assertEquals(controller.diskSpaceLeft().intValue(), space);
    controller.record();
    assertEquals(model.getStatus(), Status.NORMAL);
    assertEquals(controller.isModelRecordingProperty().getValue(), true);
    assertEquals(model.getIsRecording(), true);

    controller.record(); // stop recording
    assertEquals(model.getStatus(), Status.NORMAL);
    assertEquals(controller.isModelRecordingProperty().getValue(), false);
    assertEquals(model.getIsRecording(), false);
    // test decrement of disk size
    assertEquals(controller.diskSpaceLeft().intValue(), space-1);

    controller.record(); // start
    controller.record();
    controller.record(); // start record
    controller.record(); // stop last record

    // test recording when full
    controller.record();
    assertEquals(model.getStatus(), Status.ERROR);
    assertEquals(controller.isModelRecordingProperty().getValue(), false);
    assertEquals(model.getIsRecording(), false);
    assertEquals(controller.diskSpaceLeft().intValue(), 0);

    // test memory reset method
    controller.resetMemory();
    assertEquals(model.getStatus(), Status.NORMAL);
    assertEquals(controller.isModelRecordingProperty().getValue(), false);
    assertEquals(model.getIsRecording(), false);
    assertEquals(controller.diskSpaceLeft().intValue(), space);

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
    model.turnOff();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
    model.turnOn();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
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

    model.turnOn();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    model.turnOff();
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

    model.turnOn();
    assertTrue("failure message",model.getStatus().equals(Status.NORMAL));
    model.turnOff();
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
  }
}