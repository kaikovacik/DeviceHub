package ca.uvic.seng330.assn3;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import ca.uvic.seng330.assn3.Client;
import ca.uvic.seng330.assn3.template2.ClickApplication;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class UIAcceptanceTests extends ApplicationTest {
  @Override 
  public void start(Stage stage) {
    Scene scene = Client.createScene();
    stage.setScene(scene);
    stage.show();
  }

  @Test public void should_click_on_button() {
    System.out.println("yo");
    // when:
    clickOn("#addThermostatB");
    // then:
    verifyThat("#addThermostatB", hasText("Add"));
  }
}
