package ca.uvic.seng330.assn3.MVCtesting;

import ca.uvic.seng330.assn3.MVCtestingDevices.CameraController;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraView;
import ca.uvic.seng330.assn3.MVCtestingDevices.LightbulbModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.LightbulbView;
import ca.uvic.seng330.assn3.MVCtestingDevices.SmartPlugModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.SmartPlugView;
import ca.uvic.seng330.assn3.MVCtestingDevices.ThermostatModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.ThermostatView;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ConfigureView {
  private Button addCameraB;
  private Button addThermostatB;
  private Button addLightbulbB;
  private Button addSmartPlugB;
  private Button removeCameraB;
  private Button removeThermostatB;
  private Button removeLightbulbB;
  private Button removeSmartPlugB;
  
  private GridPane view;
  private Label statusLabel;
  private Organizer organizer;
  private VBox vBox;


  public ConfigureView(Organizer pOrganizer) {
    this.organizer = pOrganizer;
    createAndConfigurePane();

    //vBox = new VBox(view);
    
    // Camera Buttons
    removeCameraB = new Button("Remove");
    removeCameraB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        //removeCamera();
      } 
    }));
    removeCameraB.setVisible(false);
    addCameraB = new Button("Add");
    addCameraB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addCamera();
        removeCameraB.setVisible(true);
      } 
    }));
    
    // Thermostat Buttons
    removeThermostatB = new Button("Remove");
    removeThermostatB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        //removeCamera();
      } 
    }));
    removeThermostatB.setVisible(false);
    addThermostatB = new Button("Add");
    addThermostatB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addThermostat();
        removeThermostatB.setVisible(true);
      } 
    })); 
    
    // Lightbulb Buttons
    removeLightbulbB = new Button("Remove");
    removeLightbulbB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        //removeCamera();
      } 
    }));
    removeLightbulbB.setVisible(false);
    addLightbulbB = new Button("Add");
    addLightbulbB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addLightbulb();
        removeLightbulbB.setVisible(true);
      } 
    })); 
    
    // SmartPlug Buttons
    removeSmartPlugB = new Button("Remove");
    removeSmartPlugB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        //removeCamera();
      } 
    }));
    removeSmartPlugB.setVisible(false);
    addSmartPlugB = new Button("Add");
    addSmartPlugB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addSmartPlug();
        removeSmartPlugB.setVisible(true);
      } 
    }));
    
    view.setAlignment(Pos.TOP_LEFT);
    view.addRow(0, new Label("Cameras: "), addCameraB, removeCameraB);
    view.addRow(1, new Label("Thermostats: "), addThermostatB, removeThermostatB);
    view.addRow(2, new Label("Lightbulbs: "), addLightbulbB, removeLightbulbB);
    view.addRow(3, new Label("Smart Plugs: "), addSmartPlugB, removeSmartPlugB);
  }

  public void addCamera() {
    CameraModel cameraModel1 = new CameraModel();
    CameraController cameraController1 = new CameraController(cameraModel1, organizer);
    CameraView cameraView1 = new CameraView(cameraController1, organizer);
  }

  public void removeCamera() {
    //organizer.getViews().pop();
    //organizer.unregister(device);
  }
  
  public void addThermostat() {
    ThermostatModel thermostatModel = new ThermostatModel(organizer);
    ThermostatView thermostatView = new ThermostatView(thermostatModel, organizer);
  }
  
  public void addLightbulb() {
    LightbulbModel LightbulbModel = new LightbulbModel(organizer);
    LightbulbView LightbulbView = new LightbulbView(LightbulbModel, organizer);
  }
  
  public void addSmartPlug() {
    SmartPlugModel SmartPlugModel = new SmartPlugModel(organizer);
    SmartPlugView SmartPlugView = new SmartPlugView(SmartPlugModel, organizer);
  }

  private void createAndConfigurePane() {
    view = new GridPane();

    ColumnConstraints leftCol = new ColumnConstraints();
    leftCol.setHalignment(HPos.RIGHT);
    leftCol.setHgrow(Priority.NEVER);

    ColumnConstraints rightCol = new ColumnConstraints();
    rightCol.setHgrow(Priority.SOMETIMES);

    view.getColumnConstraints().addAll(leftCol, rightCol);
    view.setAlignment(Pos.CENTER);
    view.setHgap(5);
    view.setVgap(10);
    view.borderProperty();
    view.setPadding(new Insets(5));
    // black border
    view.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
  }

  public Parent asParent() {
    return view ;
  }


}
