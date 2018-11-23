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
//        removeThermostatB.setVisible(true);
      } 
    })); 
    
    addLightbulbB = new Button("Add");
    addLightbulbB.setId("addLightbulbB");
    addLightbulbB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addLightbulb();
//        removeLightbulbB.setVisible(true);
      } 
    })); 
    
    // SmartPlug Buttons
//    removeSmartPlugB = new Button("Remove");
//    removeSmartPlugB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
//      public void handle(MouseEvent event) {
//        //removeCamera();
//      } 
//    }));
//    removeSmartPlugB.setVisible(false);
    addSmartPlugB = new Button("Add");
    addSmartPlugB.setId("addSmartPlugB");
    addSmartPlugB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addSmartPlug();
//        removeSmartPlugB.setVisible(true);
      } 
    }));
    
    view.setAlignment(Pos.TOP_LEFT);
    view.addRow(0, new Label("Remove device by Id:"), removeField, removeB);
    view.addRow(1, new Label("Cameras:"), addCameraB);
    view.addRow(2, new Label("Thermostats:"), addThermostatB);
    view.addRow(3, new Label("Lightbulbs:"), addLightbulbB);
    view.addRow(4, new Label("Smart Plugs:"), addSmartPlugB);
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