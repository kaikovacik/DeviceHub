package ca.uvic.seng330.assn3;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import ca.uvic.seng330.assn3.Client;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class UIAcceptanceTests extends ApplicationTest {
  
  private Scene scene;
  
  @Override 
  public void start(Stage stage) {
    scene = Client.createScene();
    stage.setScene(scene);
    stage.setAlwaysOnTop(true);
    stage.show();
  }
  
  @Test
  public void test_user_add() {
    
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
    clickOn("#addUserViewUsernameField");
    write("neil");
    clickOn("#addUserViewPasswordField");
    write("password");
    clickOn("#addUserViewConfirmPasswordField");
    write("password");
    clickOn("#addUserViewAddUserB");
    verifyThat("#alertLabel", hasText("[USER: neil] added!"));
  }
  
  @Test
  public void test_user_login() {
    
  }

  // Scenario C ============================================
   
//  @Test 
//  public void test_camera_ui_functionality() {
//    // GIVEN a camera:
//    clickOn("#addCameraB");
//    clickOn("#cameraTab");
//    
//    // WHEN I click "Start" on the Client camera control:
//    clickOn("#cameraOnOffB");
//    
//    // THEN the camera turns on and I see the data from the Camera:
//    verifyThat("#cameraStatusLabel", hasText("NORMAL"));
//    
//    
//    // WHEN I click on toggle recording:
//    clickOn("#cameraRecordB");
//    
//    // THEN the camera starts recording:
//    verifyThat("#cameraRecordingLabel", hasText("Camera is recording"));
//    
//    
//    // WHEN I click on toggle recording:
//    clickOn("#cameraRecordB");
//    
//    // THEN the camera stops recording:
//    verifyThat("#cameraRecordingLabel", hasText("Camera is not recording"));
//    
//    
//    // GIVEN camera is out of memory: [CAMERA MEMORY MUST BE 3]:
//    clickOn("#cameraRecordB");
//    clickOn("#cameraRecordB");
//    clickOn("#cameraRecordB");
//    clickOn("#cameraRecordB");
//
//    // WHEN I click record:
//    clickOn("#cameraRecordB");
//    
//    // THEN the camera does not start recording:
//    verifyThat("#cameraRecordingLabel", hasText("Camera is not recording"));
//    
//    
//    // WHEN I click Turn Off:
//    clickOn("#cameraOnOffB");
//    
//    // THEN the camera shuts down:
//    verifyThat("#cameraStatusLabel", hasText("OFF"));
//  }
//  
//  @Test 
//  public void test_thermostat_ui_functionality() {
//    // GIVEN a thermostat:
//    clickOn("#addThermostatB");
//    clickOn("#thermostatTab");
//     
//    // WHEN I click "Start" on the Client Camera control:
//    clickOn("#thermostatOnOffB");
//    
//    // THEN the camera turns on and I see the data from the Camera:
//    verifyThat("#thermostatStatusLabel", hasText("NORMAL"));
//    
//    
//    // GIVEN entered data in the setting field:
//    clickOn("#thermostatTemperatureField").write("27");
//    
//    // WHEN I click the Celsius button:
//    clickOn("#thermostatCelsiusB");
//    
//    // THEN that value is set in the Celsius field and its
//    // corresponding value is set in the Fahrenheit field:
//    verifyThat("#thermostatCelsiusLabel", hasText("27C"));
//    verifyThat("#thermostatFahrenheitLabel", hasText("80F"));
//    
//    
//    // GIVEN entered data in the setting field:
//    clickOn("#thermostatTemperatureField").write("45");
//    
//    // WHEN I click the Fahrenheit button:
//    clickOn("#thermostatFahrenheitB");
//    
//    // THEN that value is set in the Fahrenheit field and its
//    // corresponding value is set in the Celsius field:
//    verifyThat("#thermostatCelsiusLabel", hasText("7C"));
//    verifyThat("#thermostatFahrenheitLabel", hasText("45F"));
//    
//    
//    // GIVEN entered data in the setting field:
//    clickOn("#thermostatTemperatureField").write("70");
//    
//    // WHEN I click either button and the corresponding 
//    // temperature in Celsius, temp is < 0 or > 50:
//    clickOn("#thermostatCelsiusB");
//    
//    // THEN the interaction does not change the Thermostat's setting:
//    verifyThat("#thermostatCelsiusLabel", hasText("7C"));
//    verifyThat("#thermostatFahrenheitLabel", hasText("45F"));
//    
//    
//    // WHEN I click "Turn Off"
//    clickOn("#thermostatOnOffB");
//    
//    // Then the Thermostat shuts down and hides data:
//    verifyThat("#thermostatStatusLabel", hasText("OFF"));
//  }
}
