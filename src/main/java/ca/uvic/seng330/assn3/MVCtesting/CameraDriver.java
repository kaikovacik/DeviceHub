package ca.uvic.seng330.assn3.MVCtesting;

import ca.uvic.seng330.assn3.MVCtestingDevices.CameraController;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CameraDriver extends Application {

  @Override
  public void start(Stage primaryStage) {
    
    Organizer organizer = new Organizer();
    AllertView allertView = new AllertView(organizer);
    
    // Cam Controller needs to be aware of organizer so it can send it alerts when the cam is full.
    CameraModel cameraModel1 = new CameraModel();
    CameraController cameraController1 = new CameraController(cameraModel1, organizer);
    CameraView cameraView1 = new CameraView(cameraController1);
    
    CameraModel cameraModel2 = new CameraModel();
    CameraController cameraController2 = new CameraController(cameraModel2, organizer);
    CameraView cameraView2 = new CameraView(cameraController2);
     
    VBox vBox = new VBox(cameraView1.asParent(),cameraView2.asParent());
    vBox.getChildren().add(allertView.asParent());  
    
    //blue border
    vBox.setStyle(
        "-fx-padding: 10;" +
        " -fx-border-style: solid inside;" + 
        "-fx-border-width: 2;  -fx-border-insets: 5;" + 
        "-fx-border-radius: 5; -fx-border-color: blue;"
    );
    System.out.println(organizer.numOfDevices());
    Scene scene1 = new Scene(vBox, 400, organizer.numOfDevices()*150+75);
    
    // you can only have one scene/pane in a stage at a time, 
    // but a pane can contain multiple panes (Vbox)
    primaryStage.setScene(scene1);
    primaryStage.setTitle("Cameras");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
