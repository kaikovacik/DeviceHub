package ca.uvic.seng330.assn3.MVCtesting;

import ca.uvic.seng330.assn3.MVCtestingDevices.CameraController;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraView;
import ca.uvic.seng330.assn3.MVCtestingDevices.ThermostatModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.ThermostatView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ThermostatDriver extends Application {
  private Organizer organizer;
  private VBox vBox;
  private GridPane controls;
  private Button addThermostatB;

  @Override
  public void start(Stage primaryStage) {

    organizer = new Organizer();
    
    createAndConfigureControls();
    
    vBox = new VBox(controls);
    
    addThermostatB = new Button("+");
    addThermostatB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
         addThermostat();
      } 
    })); 
    
    controls.addRow(0, new Label("Add Thermostat:"), addThermostatB);

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
  
  public void addThermostat() {
    ThermostatModel thermostatModel = new ThermostatModel(organizer);
    // ThermostatController thermostatController1 = new CameraController(cameraModel1);
    ThermostatView thermostatView = new ThermostatView(thermostatModel, organizer);

    try {
      organizer.register(thermostatModel);
    } catch (HubRegistrationException e) {
      System.out.println("Error Line " + new Exception().getStackTrace()[0].getLineNumber());
      e.printStackTrace();
    }

    vBox.getChildren().add(thermostatView.asParent());
  }
  
  public void removeThermostat(ThermostatView view) {
    vBox.getChildren().remove(view.asParent());
  }
  
  private void createAndConfigureControls() {
    controls = new GridPane();

    ColumnConstraints leftCol = new ColumnConstraints();
    leftCol.setHalignment(HPos.RIGHT);
    leftCol.setHgrow(Priority.NEVER);

    ColumnConstraints rightCol = new ColumnConstraints();
    rightCol.setHgrow(Priority.SOMETIMES);

    controls.getColumnConstraints().addAll(leftCol, rightCol);

    controls.setAlignment(Pos.CENTER);
    controls.setHgap(5);
    controls.setVgap(10);
    controls.borderProperty();
    // black border
    controls.setStyle(
        " -fx-padding: 10; " +
        " -fx-border-color: black; " +
        " -fx-border-radius: 5; " +
        " -fx-box-shadow: 10px; " +
        " -fx-background-color: lightgrey; " +
        " -fx-background-radius: 5; "
        );
  }

  public static void main(String[] args) {
    launch(args);
  }
}
