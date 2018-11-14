package ca.uvic.seng330.assn3.sethMVCtesting;

import ca.uvic.seng330.assn3.sethMVCtestingDevices.CameraController;
import ca.uvic.seng330.assn3.sethMVCtestingDevices.CameraModel;
import ca.uvic.seng330.assn3.sethMVCtestingDevices.CameraView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CameraDriver extends Application {

  @Override
  public void start(Stage primaryStage) {

    Organizer organizer = new Organizer();

    ThermostatModel thermostatModel1 = new ThermostatModel();
    // ThermostatController thermostatController1 = new CameraController(cameraModel1);
    ThermostatView thermostatView1 = new ThermostatView(thermostatModel1);

    ThermostatModel thermostatModel2 = new ThermostatModel();
    // CameraController cameraController2 = new CameraController(cameraModel2);
    ThermostatView thermostatView2 = new ThermostatView(thermostatModel2); 

    try {
      organizer.register(cameraModel1);
    } catch (HubRegistrationException e) {
      System.out.println("Error Line " + new Exception().getStackTrace()[0].getLineNumber());
      e.printStackTrace();
    }

    VBox vBox = new VBox(thermostatView1.asParent(), thermostatView2.asParent());
    // blue border
    vBox.setStyle("-fx-padding: 10;" + " -fx-border-style: solid inside;"
        + "-fx-border-width: 2;  -fx-border-insets: 5;" + "-fx-border-radius: 5; -fx-border-color: blue;");
    Scene scene1 = new Scene(vBox, 400, 400);

    // you can only have one scene/pane in a stage at a time,
    // but a pane can contain multiple panes (Vbox)
    primaryStage.setScene(scene1);
    primaryStage.setTitle("Thermostats");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
