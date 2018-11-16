package ca.uvic.seng330.assn3;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import ca.uvic.seng330.assn3.Client;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class UIAcceptanceTests extends ApplicationTest {
  @Override 
  public void start(Stage stage) {
    Scene scene = Client.createScene();
    stage.setScene(scene);
    stage.show();
  }

  // Scenario C
  @Test public void C1() {
    // when:
    clickOn("#addCameraB");
    clickOn("#cameraOnOffB");
    // then:
    verifyThat("#addThermostatB", hasText("Add"));
  }
}
