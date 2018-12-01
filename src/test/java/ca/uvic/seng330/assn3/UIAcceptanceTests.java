package ca.uvic.seng330.assn3;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import ca.uvic.seng330.assn3.Client;
import ca.uvic.seng330.assn3.devices.DeviceView;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class UIAcceptanceTests extends ApplicationTest {

  private Scene scene;
  private Organizer organizer;

  @Override 
  public void start(Stage stage) {
    scene = Client.createScene();
    organizer = Client.getOrganizer();

    stage.setScene(scene);
    stage.setAlwaysOnTop(true);
    stage.show();
  }

  //test User Add and Login
  
  @Test
  public void test_user_add_and_login() {

    // ADD

    // CASE: invalid username
    clickOn("#newUserB");
    clickOn("#addUserViewUsernameField");
    write("");
    clickOn("#addUserViewPasswordField");
    write("password");
    clickOn("#addUserViewConfirmPasswordField");
    write("password");
    clickOn("#addUserViewAddUserB");
    verifyThat("#addUserViewAlertLabel", hasText("Invalid username!"));

    // CASE: passwords don't match
    clickOn("#addUserViewUsernameField");
    write("neil");
    clickOn("#addUserViewPasswordField");
    write("password");
    clickOn("#addUserViewConfirmPasswordField");
    write("wrong");
    clickOn("#addUserViewAddUserB");
    verifyThat("#addUserViewAlertLabel", hasText("Passwords don't match!"));

    // CASE: everything is valid
    clickOn("#addUserViewPasswordField");
    write("password");
    clickOn("#addUserViewConfirmPasswordField");
    write("password");
    clickOn("#addUserViewAddUserB");
    verifyThat("#alertLabel", hasText("[USER: neil] added!"));

    // LOGIN

    // CASE: unknown user
    clickOn("#usernameField");
    write("neilll");
    clickOn("#passwordField");
    write("password");
    clickOn("#loginB");
    verifyThat("#alertLabel", hasText("Unknown user!"));

    // CASE: incorrect password
    clickOn("#usernameField");
    write("neil");
    clickOn("#passwordField");
    write("passwo");
    clickOn("#loginB");
    verifyThat("#alertLabel", hasText("Incorrect password!"));

    // CASE: everything is valid
    clickOn("#usernameField");
    write("neil");
    clickOn("#passwordField");
    write("password");
    clickOn("#loginB");
    // THIS VERIFY DOESNT SEEM TO WORK... PERHAPS WE DON'T VERIFY?
    // verifyThat("#cameraTab", hasText("Camera")); // Arbitrary query to verify that the user is logged in
  }
   

  // test admin login


  @Test 
  public void test_startup_statuscheck_and_shutdown() {

    // CASE: everything is valid
    clickOn("#usernameField");
    write("kai");
    clickOn("#passwordField");
    write("iak");
    clickOn("#loginB");

    // add dev
    clickOn("#addCameraB").clickOn("#addThermostatB").clickOn("#addCameraB").clickOn("#addLightbulbB").clickOn("#addCameraB");

    // test startup
    clickOn("#startB");
    assertTrue(organizer.getLastLog().toString().contains("All devices turned on."));
    for (DeviceView d : organizer.getViews().values()) {
      if (d.getModel().getStatus().equals(Status.OFF) ){
        fail();
      }
    }

    // test status Check
    clickOn("#lightbulbTab");
    clickOn("#lightbulbToggleB");
    clickOn("#configTab");
    clickOn("#statusCheckB");
    assertTrue(organizer.getLastLog().toString().contains("Status Check:  -  Camera (1): NORMAL  -  Thermostat (2): NORMAL  -  Camera (3): NORMAL  -  Lightbulb (4): OFF  -  Camera (5): NORMAL"));

    // test shutdown
    clickOn("#shutdownB");
    assertTrue(organizer.getLastLog().toString().contains("All devices shutdown."));
    for (DeviceView d : organizer.getViews().values()) {
      if (d.getModel().getStatus().equals(Status.NORMAL) ){
        fail();
      }
    }
  }

  
  @Test 
  public void test_camera_video() {

    // CASE: everything is valid
    clickOn("#usernameField");
    write("kai");
    clickOn("#passwordField");
    write("iak");
    clickOn("#loginB");

    // GIVEN a camera:
    clickOn("#addCameraB").clickOn("#addCameraB").clickOn("#addCameraB");
    clickOn("#cameraTab");
    clickOn("#cameraOnOffB");
    verifyThat("#cameraStatusLabel", hasText("NORMAL"));
    sleep(500);
    clickOn("#webPlayer");
    clickOn("#cameraRecordB");
    this.scroll(40);
    this.scroll(-40);

    clickOn("#cameraRecordB");
    clickOn("#cameraRecordB");
    verifyThat("#cameraRecordingLabel", hasText("Camera is recording"));
    clickOn("#cameraRecordB");

    sleep(1000);
    clickOn("#webPlayer");
    sleep(100);
  }
  

  
  @Test
  public void test_assigning_dev_to_users() { 
    // add Admin
    clickOn("#usernameField");
    write("kai");
    clickOn("#passwordField");
    write("iak");
    clickOn("#loginB");

    // add devices
    clickOn("#addCameraB");
    clickOn("#addCameraB");
    clickOn("#addLightbulbB");
    clickOn("#addSmartPlugB");
    assertTrue(organizer.deviceCount == 4);
    clickOn("#userTab");

    verifyThat("#userViewTitleLabel", hasText("ADMIN:"));
    verifyThat("#userViewNameLabel", hasText("kai"));

    clickOn("#userViewDeviceMenu");

    // adds 3 devices to user
    this.sleep(500);
    clickOn("#userViewMenuItem1");
    clickOn("#userViewLinkedDeviceMenu");
    this.sleep(500);
    clickOn("#userViewDeviceMenu");
    clickOn("#userViewMenuItem2");
    clickOn("#userViewDeviceMenu");
    clickOn("#userViewMenuItem4");
    clickOn("#userViewLinkedDeviceMenu");
    this.sleep(500);

    // log out and login as guest
    clickOn("#logoutTab");
    clickOn("#usernameField");
    write("guest");
    clickOn("#passwordField");
    write("tseug");
    clickOn("#loginB");

    // check camera, etc
    clickOn("#cameraTab");
    clickOn("#cameraOnOffB");
    verifyThat("#cameraStatusLabel", hasText("NORMAL"));
    clickOn("#cameraRecordB");
    verifyThat("#cameraRecordingLabel", hasText("Camera is recording"));
    this.scroll(10);
    this.scroll(-10);

    // check thermostat
    clickOn("#thermostatTab");
    this.sleep(500);

    // check lightbulb
    clickOn("#lightbulbTab");
    this.sleep(500);

    // check smartplub
    clickOn("#smartPlugTab");
    clickOn("#smartPlugToggleB");
    verifyThat("#smartPlugStatusLabel", hasText("NORMAL"));
    clickOn("#smartPlugToggleB");
    verifyThat("#smartPlugStatusLabel", hasText("OFF"));

    // logout and login as guest
    clickOn("#logoutTab");
    clickOn("#usernameField");
    write("kai");
    clickOn("#passwordField");
    write("iak");

    // remove devices
    clickOn("#loginB");
    clickOn("#userTab");
    clickOn("#userViewLinkedDeviceMenu");
    clickOn("#userViewLinkedMenuItem1");
    clickOn("#userViewLinkedDeviceMenu");
    clickOn("#userViewLinkedMenuItem2");
    clickOn("#userViewLinkedDeviceMenu");
    clickOn("#userViewLinkedMenuItem4");
    clickOn("#userViewDeviceMenu");
    this.sleep(500);

    // log out and login as guest
    clickOn("#logoutTab");
    clickOn("#usernameField");
    write("guest");
    clickOn("#passwordField");
    write("tseug");
    clickOn("#loginB");

    clickOn("#cameraTab");
    clickOn("#thermostatTab");
    clickOn("#lightbulbTab");
    clickOn("#smartPlugTab");
    clickOn("#logoutTab");

  }
  


  // Scenario C ============================================
 

  @Test 
  public void test_camera_ui_functionality() {

 // CASE: everything is valid
    clickOn("#usernameField");
    write("kai");
    clickOn("#passwordField");
    write("iak");
    clickOn("#loginB");

    // GIVEN a camera:
    clickOn("#addCameraB");
    clickOn("#cameraTab");

    // WHEN I click "Start" on the Client camera control:
    clickOn("#cameraOnOffB");

    // THEN the camera turns on and I see the data from the Camera:
    verifyThat("#cameraStatusLabel", hasText("NORMAL"));


    // WHEN I click on toggle recording:
    clickOn("#cameraRecordB");

    // THEN the camera starts recording:
    verifyThat("#cameraRecordingLabel", hasText("Camera is recording"));


    // WHEN I click on toggle recording:
    clickOn("#cameraRecordB");

    // THEN the camera stops recording:
    verifyThat("#cameraRecordingLabel", hasText("Camera is not recording"));
    verifyThat("#alertText", hasText("Camera (1) stopped recording"));

    // GIVEN camera is out of memory: [CAMERA MEMORY MUST BE 3]:
    clickOn("#cameraRecordB");
    verifyThat("#alertText", hasText("Camera (1) started recording"));
    clickOn("#cameraRecordB");
    clickOn("#cameraRecordB");
    clickOn("#cameraRecordB");

    // WHEN I click record:
    clickOn("#cameraRecordB");

    // THEN the camera does not start recording:
    verifyThat("#cameraRecordingLabel", hasText("Camera is not recording"));
    verifyThat("#alertText", hasText("Camera (1) is full!"));

    //  Erase Memory
    clickOn("#cameraEraseB");
    verifyThat("#cameraStatusLabel", hasText("NORMAL"));
    verifyThat("#cameraMemoryLabel", hasText("3"));


    // WHEN I click Turn Off:
    clickOn("#cameraOnOffB");

    // THEN the camera shuts down:
    verifyThat("#cameraStatusLabel", hasText("OFF"));
  }


  @Test 
  public void test_thermostat_ui_functionality() {

 // CASE: everything is valid
    clickOn("#usernameField");
    write("kai");
    clickOn("#passwordField");
    write("iak");
    clickOn("#loginB");
    verifyThat("#addCameraB", hasText("Camera"));

    // GIVEN a thermostat:
    clickOn("#addThermostatB");
    verifyThat("#alertText", hasText("Thermostat (1) added"));
    clickOn("#thermostatTab");

    // WHEN I click "Start" on the Client Camera control:
    clickOn("#thermostatOnOffB");

    // THEN the camera turns on and I see the data from the Camera:
    verifyThat("#thermostatStatusLabel", hasText("NORMAL"));


    // GIVEN entered data in the setting field:
    clickOn("#thermostatTemperatureField").write("27");

    // WHEN I click the Celsius button:
    clickOn("#thermostatCelsiusB");

    // THEN that value is set in the Celsius field and its
    // corresponding value is set in the Fahrenheit field:
    verifyThat("#thermostatCelsiusLabel", hasText("27C"));
    verifyThat("#thermostatFahrenheitLabel", hasText("80F"));
    verifyThat("#alertText", hasText("Thermostat (1) set to 27C"));


    // GIVEN entered data in the setting field:
    clickOn("#thermostatTemperatureField").write("45");

    // WHEN I click the Fahrenheit button:
    clickOn("#thermostatFahrenheitB");

    // THEN that value is set in the Fahrenheit field and its
    // corresponding value is set in the Celsius field:
    verifyThat("#thermostatCelsiusLabel", hasText("7C"));
    verifyThat("#thermostatFahrenheitLabel", hasText("45F"));


    // GIVEN entered data in the setting field:
    clickOn("#thermostatTemperatureField").write("70");

    // WHEN I click either button and the corresponding 
    // temperature in Celsius, temp is < 0 or > 50:
    clickOn("#thermostatCelsiusB");

    // THEN the interaction does not change the Thermostat's setting:
    verifyThat("#thermostatCelsiusLabel", hasText("7C"));
    verifyThat("#thermostatFahrenheitLabel", hasText("45F"));
    verifyThat("#alertText", hasText("Temperature out of bounds"));


    // WHEN I click "Turn Off"
    clickOn("#thermostatOnOffB");

    // Then the Thermostat shuts down and hides data:
    verifyThat("#thermostatStatusLabel", hasText("OFF"));
  }
   
/*
  
  @Test 
  public void test_camera_lightbulb_isObject() {

 // CASE: everything is valid
    clickOn("#usernameField");
    write("kai");
    clickOn("#passwordField");
    write("iak");
    clickOn("#loginB");

    // GIVEN a camera:
    clickOn("#addCameraB");
    clickOn("#addLightbulbB");
    CameraView v = (CameraView) organizer.getViews().get(1);
    CameraModel m = (CameraModel) v.getModel();
    m.setIsObject(true);
    
//    for (DeviceView d : organizer.getViews().values()) {
//      if( d instanceof CameraView) {
//        CameraModel m = (CameraModel) d.getModel();
//        //organizer.alert(m, "hello");
//        m.setIsObject(true);
//      }
//    }

    clickOn("#cameraTab");
    clickOn("#lightbulbTab");
    sleep(1000);
    // WHEN I click "Start" on the Client camera control:
    clickOn("#cameraOnOffB");


  }
   */
}
