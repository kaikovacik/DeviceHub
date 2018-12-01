package ca.uvic.seng330.assn3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import ca.uvic.seng330.assn3.*;
import ca.uvic.seng330.assn3.devices.*;
import ca.uvic.seng330.assn3.devices.ThermostatModel.TemperatureOutofBoundsException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class NonUIAcceptanceTests {

  private Organizer organizer;
  private Scene scene;

  @Before
  public void initializeSystem() {
    organizer = new Organizer();
  }

  @Test
  public void testC_Camera() {
    int space = 3;
    CameraModel model = new CameraModel(organizer);

    //assertTrue("failure message",organizer.numOfDevices() == 1);
    assertTrue("failure message",model.getStatus().equals(Status.OFF));
    assertEquals(model.diskSizeRemaining().get(), space);
    model.record();
    assertEquals(model.getStatus(), Status.NORMAL);
    assertEquals(model.isThisRecording().getValue(), true);
    assertEquals(model.getIsRecording(), true);

    model.record(); // stop recording
    assertEquals(model.getStatus(), Status.NORMAL);
    assertEquals(model.getIsRecording(), false);
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
    assertEquals(model.getIsRecording(), false);
    assertEquals(model.getIsRecording(), false);
    assertEquals(model.diskSizeRemaining().get(), 0);

    // test memory reset method
    model.resetMemory();
    assertEquals(model.getStatus(), Status.NORMAL);
    assertEquals(model.isThisRecording().getValue(), false);
    assertEquals(model.getIsRecording(), false);
    assertEquals(model.diskSizeRemaining().get(), space);

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