package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.AllertView;
import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.devices.CameraController;
import ca.uvic.seng330.assn3.devices.CameraModel;
import ca.uvic.seng330.assn3.devices.CameraView;
import ca.uvic.seng330.assn3.devices.DeviceModel;
import ca.uvic.seng330.assn3.devices.LightbulbView;
import ca.uvic.seng330.assn3.devices.SmartPlugView;
import ca.uvic.seng330.assn3.devices.ThermostatView;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class Client extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Scene scene = createScene();
    primaryStage.setScene(scene);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.show();
  }

  // scene object for unit tests
  public static Scene createScene() { 
    Organizer organizer = new Organizer();
    AllertView allertView = new AllertView(organizer);
    ConfigureView configureView = new ConfigureView(organizer);

    Group root = new Group();
    Scene scene = new Scene(root, 500, 400);

    BorderPane mainPane = new BorderPane();
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

    // puts Cameras + other Dev in system
    PopulateSystem(organizer);

    // Configure Tab
    Tab configTab = new Tab();
    configTab.setText("Device Configuration");
    configTab.setContent(configureView.asParent());
    tabPane.getTabs().add(configTab);

    // Cameras Tab
    Tab cameraTab = new Tab();
    cameraTab.setId("cameraTab");
    cameraTab.setText("Cameras");
    VBox cameraVbox = new VBox();
    refreshCameraTab(organizer, cameraVbox);
    cameraTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        if(cameraTab.isSelected()) {
          refreshCameraTab(organizer, cameraVbox);
        }
      }
    });
    cameraTab.setContent(cameraVbox);
    tabPane.getTabs().add(cameraTab);

    // Thermostat Tab
    Tab thermostatTab = new Tab();
    thermostatTab.setId("thermostatTab");
    thermostatTab.setText("Thermostats");
    VBox thermostatVbox = new VBox();
    refreshThermostatTab(organizer, thermostatVbox);
    thermostatTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        if(thermostatTab.isSelected()) {
          refreshThermostatTab(organizer, thermostatVbox);
        }
      }
    });
    thermostatTab.setContent(thermostatVbox);
    tabPane.getTabs().add(thermostatTab);

    // Lightbulb Tab
    Tab lightbulbTab = new Tab();
    lightbulbTab.setText("Lightbulbs");
    VBox lightbulbVbox = new VBox();
    refreshLightbulbTab(organizer, lightbulbVbox);
    lightbulbTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        if(lightbulbTab.isSelected()) {
          refreshLightbulbTab(organizer, lightbulbVbox);
        }
      }
    });
    lightbulbTab.setContent(lightbulbVbox);
    tabPane.getTabs().add(lightbulbTab);

    // SmartPlug Tab
    Tab smartPlugTab = new Tab();
    smartPlugTab.setText("Smart Plugs");
    VBox smartPlugVbox = new VBox();
    refreshSmartPlugTab(organizer, smartPlugVbox);
    smartPlugTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        if(smartPlugTab.isSelected()) {
          refreshSmartPlugTab(organizer, smartPlugVbox);
        }
      }
    });
    smartPlugTab.setContent(smartPlugVbox);
    tabPane.getTabs().add(smartPlugTab);

    mainPane.setCenter(tabPane);
    mainPane.setBottom(allertView.asParent());
    mainPane.prefHeightProperty().bind(scene.heightProperty());
    mainPane.prefWidthProperty().bind(scene.widthProperty());

    root.getChildren().add(mainPane);
    
    return scene;
  }


  private static void PopulateSystem(Organizer organizer) {
    CameraModel cameraModel1 = new CameraModel();
    CameraController cameraController1 = new CameraController(cameraModel1, organizer);
    CameraView cameraView1 = new CameraView(cameraController1, organizer);

    CameraModel cameraModel2 = new CameraModel();
    CameraController cameraController2 = new CameraController(cameraModel2, organizer);
    CameraView cameraView2 = new CameraView(cameraController2, organizer);
  }

  private static void refreshCameraTab(Organizer organizer, VBox vbox) {
    vbox.getChildren().clear();
    for ( Object d : organizer.getViews()) {
      if( d instanceof CameraView) {
        vbox.getChildren().add( ((CameraView) d).asParent() );
      } 
    }
  }

  private static void refreshThermostatTab(Organizer organizer, VBox vbox) {
    vbox.getChildren().clear();
    for ( Object d : organizer.getViews()) {
      if( d instanceof ThermostatView) {
        vbox.getChildren().add( ((ThermostatView) d).asParent() );
      } 
    }
  }

  private static void refreshLightbulbTab(Organizer organizer, VBox vbox) {
    vbox.getChildren().clear();
    for ( Object d : organizer.getViews()) {
      if( d instanceof LightbulbView) {
        vbox.getChildren().add( ((LightbulbView) d).asParent() );
      } 
    }
  }

  private static void refreshSmartPlugTab(Organizer organizer, VBox vbox) {
    vbox.getChildren().clear();
    for ( Object d : organizer.getViews()) {
      if( d instanceof SmartPlugView) {
        vbox.getChildren().add( ((SmartPlugView) d).asParent() );
      } 
    }
  }
}