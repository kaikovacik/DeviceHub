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
  public void ez_Test() {
    
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