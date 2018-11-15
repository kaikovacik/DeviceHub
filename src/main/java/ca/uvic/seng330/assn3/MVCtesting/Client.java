package ca.uvic.seng330.assn3.MVCtesting;

import ca.uvic.seng330.assn3.MVCtesting.AllertView;
import ca.uvic.seng330.assn3.MVCtesting.Organizer;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraController;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraView;
import ca.uvic.seng330.assn3.MVCtestingDevices.DeviceModel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
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
    Organizer organizer = new Organizer();
    AllertView allertView = new AllertView(organizer);
    ConfigureView configureView = new ConfigureView(organizer);

    primaryStage.setTitle("Home Automation System");
    Group root = new Group();
    Scene scene = new Scene(root, 500, 400);

    BorderPane mainPane = new BorderPane();
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    
    // puts Cameras + other Dev in system
    PopulateSystem(organizer);

    // Cameras Tab
    Tab cameraTab = new Tab();
    cameraTab.setText("Cameras");
    VBox cameraVbox = new VBox();
    for ( Object d : organizer.getViews()) {
      if( d instanceof CameraView) {
        cameraVbox.getChildren().add( ((CameraView) d).asParent() );
      } 
    }
    cameraTab.setContent(cameraVbox);
    tabPane.getTabs().add(cameraTab);

    // Configure Tab
    Tab configTab = new Tab();
    configTab.setText("Device Configuration");
    //Add something in Tab
    //Button tabA_button = new Button("Button@Tab A");
    configTab.setContent(configureView.asParent());
    tabPane.getTabs().add(configTab);


    //Create Tabs
    Tab tabA = new Tab();
    tabA.setText("Tab A");
    //Add something in Tab
    Button tabA_button = new Button("Button@Tab A");
    tabA.setContent(tabA_button);
    tabPane.getTabs().add(tabA);

    Tab tabB = new Tab();
    tabB.setText("Tab B");
    //Add something in Tab
    StackPane tabB_stack = new StackPane();
    tabB_stack.setAlignment(Pos.CENTER);
    tabB_stack.getChildren().add(new Label("Label@Tab B"));
    tabB.setContent(tabB_stack);
    tabPane.getTabs().add(tabB);

    Tab tabC = new Tab();
    tabC.setText("Tab C");
    //Add something in Tab
    VBox tabC_vBox = new VBox();
    tabC_vBox.getChildren().addAll(
        new Button("Button 1@Tab C"),
        new Button("Button 2@Tab C"),
        new Button("Button 3@Tab C"),
        new Button("Button 4@Tab C"));
    tabC.setContent(tabC_vBox);
    tabPane.getTabs().add(tabC);

    mainPane.setCenter(tabPane);
    mainPane.setBottom(allertView.asParent());

    mainPane.prefHeightProperty().bind(scene.heightProperty());
    mainPane.prefWidthProperty().bind(scene.widthProperty());

    root.getChildren().add(mainPane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void PopulateSystem(Organizer organizer) {
    CameraModel cameraModel1 = new CameraModel();
    CameraController cameraController1 = new CameraController(cameraModel1, organizer);
    CameraView cameraView1 = new CameraView(cameraController1, organizer);

    CameraModel cameraModel2 = new CameraModel();
    CameraController cameraController2 = new CameraController(cameraModel2, organizer);
    CameraView cameraView2 = new CameraView(cameraController2, organizer);
  }
}