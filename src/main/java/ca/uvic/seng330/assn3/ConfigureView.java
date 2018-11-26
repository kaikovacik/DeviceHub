package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.devices.CameraView;
import ca.uvic.seng330.assn3.devices.LightbulbView;
import ca.uvic.seng330.assn3.devices.SmartPlugView;
import ca.uvic.seng330.assn3.devices.ThermostatView;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ConfigureView {
  private TextField removeField;
  private Button addCameraB;
  private Button addThermostatB;
  private Button addLightbulbB;
  private Button addSmartPlugB;
  private Button shutdownB;
  private Button statusCheckB;
  private Button removeB;
  private GridPane view;
  private Organizer organizer;


  public ConfigureView(Organizer pOrganizer) {
    this.organizer = pOrganizer;
    createAndConfigurePane();
    
    // Camera Buttons
    removeField = new TextField();
    removeField.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        removeField.setText("");
    }}));
    removeB = new Button("Remove");
    removeB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        try {
          remove(removeField.getText());
        } catch (Exception e) {
          removeField.setText("Invalid device!");
        }
      } 
    }));
    addCameraB = new Button("Add");
    addCameraB.setId("addCameraB");
    addCameraB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addCamera();
      } 
    }));
    
    addThermostatB = new Button("Add");
    addThermostatB.setId("addThermostatB");
    addThermostatB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addThermostat();
      } 
    })); 
    
    addLightbulbB = new Button("Add");
    addLightbulbB.setId("addLightbulbB");
    addLightbulbB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addLightbulb();
      } 
    })); 
    
    addSmartPlugB = new Button("Add");
    addSmartPlugB.setId("addSmartPlugB");
    addSmartPlugB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addSmartPlug();
      } 
    }));
    
    shutdownB = new Button("Shutdown");
    shutdownB.setId("shutdownB");
    shutdownB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        organizer.shutdown();
      } 
    }));
    
    statusCheckB = new Button("Shutdown");
    statusCheckB.setId("shutdownB");
    statusCheckB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        organizer.statusCheck();
      } 
    }));
      
    view.setAlignment(Pos.TOP_LEFT);
    view.addRow(0, new Label("Remove device by Id:"), removeB);
    view.addRow(1, new Label("Cameras:"), addCameraB);
    view.addRow(2, new Label("Thermostats:"), addThermostatB);
    view.addRow(3, new Label("Lightbulbs:"), addLightbulbB);
    view.addRow(4, new Label("Smart Plugs:"), addSmartPlugB);
    view.addRow(8, new Label("System:"), shutdownB, statusCheckB);
  }

  public void addCamera() {
    new CameraView(organizer);
  }

  public void remove(String id) throws Exception {
    try {
      organizer.unregister(id);
      removeField.setText("");

    } catch (Exception e) {
      removeField.setText("Invalid device!");
    }
  }
  
  public void addThermostat() {
    new ThermostatView(organizer);
  }
  
  public void addLightbulb() {
    new LightbulbView(organizer);
  }
  
  public void addSmartPlug() {
    new SmartPlugView(organizer);
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