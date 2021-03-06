package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.devices.CameraView;
import ca.uvic.seng330.assn3.devices.DeviceView;
import ca.uvic.seng330.assn3.devices.LightbulbView;
import ca.uvic.seng330.assn3.devices.SmartPlugView;
import ca.uvic.seng330.assn3.devices.ThermostatView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ConfigureView {
  private Button addCameraB;
  private Button addThermostatB;
  private Button addLightbulbB;
  private Button addSmartPlugB;
  private Button shutdownB;
  private Button statusCheckB;
  private Button startB;
  private MenuButton deviceMenu;
  private GridPane view;
  private Organizer organizer;


  public ConfigureView(Organizer pOrganizer) {
    this.organizer = pOrganizer;
    createAndConfigurePane();
    
    deviceMenu = new MenuButton("Devices"); 
    deviceMenu.setId("deviceMenuB"); // id for ui tests
    for (DeviceView d : organizer.getViews().values()) {
      MenuItem menuItem = new MenuItem(d.toString());
      menuItem.setId("menuItem" + d.getModel().getIdentifier()); // id for ui tests
      menuItem.setOnAction((new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent event) {
          try {
            organizer.unregister(d.getModel().getIdentifier());
            deviceMenu.getItems().remove(menuItem);
          } catch(HubRegistrationException e) {
            System.err.println("Could not remove " + d);
          }
        } 
      }));

      deviceMenu.getItems().add(menuItem);
    }
    
    addCameraB = new Button("Camera");
    addCameraB.setId("addCameraB"); // id for tests
    addCameraB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addCamera();
      } 
    }));

    addThermostatB = new Button("Thermostat");
    addThermostatB.setId("addThermostatB"); // id for ui tests
    addThermostatB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addThermostat();
      } 
    })); 

    addLightbulbB = new Button("Lightbulb");
    addLightbulbB.setId("addLightbulbB"); // id for ui tests
    addLightbulbB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addLightbulb();
      } 
    })); 

    addSmartPlugB = new Button("Smart Plug");
    addSmartPlugB.setId("addSmartPlugB"); // id for ui tests
    addSmartPlugB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addSmartPlug();
      } 
    }));

    shutdownB = new Button("Shutdown");
    shutdownB.setId("shutdownB"); // id for ui tests
    shutdownB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        organizer.shutdown();
      } 
    }));
    
    startB = new Button("Start-up");
    startB.setId("startB"); // id for ui tests
    startB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        organizer.startup();
      } 
    }));

    statusCheckB = new Button("Check Device Status");
    statusCheckB.setId("statusCheckB"); // id for ui tests
    statusCheckB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        Thread t = new Thread(() -> organizer.statusCheck());
        t.start();
      } 
    }));
      
    view.addRow(0, new Label("Remove existing device:"), deviceMenu);
    view.addRow(1, new Label("New device:"));
    view.addColumn(1, addCameraB, addThermostatB, addLightbulbB, addSmartPlugB);
    view.addRow(8, new Label("System:"), startB, shutdownB, statusCheckB );
  }
  
  protected void addToDeviceMenu(DeviceView d) {
    MenuItem menuItem = new MenuItem(d.toString());
    menuItem.setId("menuItem" + d.getModel().getIdentifier()); // id for ui tests
    menuItem.setOnAction((new EventHandler<ActionEvent>() { 
      public void handle(ActionEvent event) {
        try {
          organizer.unregister(d.getModel().getIdentifier());
          deviceMenu.getItems().remove(menuItem);
        } catch(HubRegistrationException e) {
          System.err.println("Could not remove " + d);
        }
      } 
    }));
    
    deviceMenu.getItems().add(menuItem);
  }

  public void addCamera() {
    addToDeviceMenu(new CameraView(organizer));
  }

  public void addThermostat() {
    
    addToDeviceMenu(new ThermostatView(organizer));
  }

  public void addLightbulb() {
    addToDeviceMenu(new LightbulbView(organizer));
  }

  public void addSmartPlug() {
    addToDeviceMenu(new SmartPlugView(organizer));
  }

  private void createAndConfigurePane() {
    view = new GridPane();
    view.setStyle("-fx-background-color: linear-gradient(white, lightgrey);");

    ColumnConstraints leftCol = new ColumnConstraints(150);
    leftCol.setHalignment(HPos.RIGHT);
    leftCol.setHgrow(Priority.NEVER);

    ColumnConstraints rightCol = new ColumnConstraints(100);
    rightCol.setHgrow(Priority.SOMETIMES);
    
    //view.getColumnConstraints().add(new ColumnConstraints(100)); // column 0 is 100 wide
    //view.getColumnConstraints().add(new ColumnConstraints(200));
    
    view.getColumnConstraints().addAll(leftCol, rightCol);
    view.setAlignment(Pos.TOP_LEFT);
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