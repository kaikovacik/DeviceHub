package ca.uvic.seng330.assn3.sethMVCtesting;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MVCExample extends Application {

  @Override
  public void start(Stage primaryStage) {
    
    CameraModel cameraModel1 = new CameraModel();
    CameraController cameraController1 = new CameraController(cameraModel1);
    View cameraView1 = new View(cameraController1, cameraModel1);
    
    CameraModel cameraModel2 = new CameraModel();
    CameraController cameraController2 = new CameraController(cameraModel2);
    View cameraView2 = new View(cameraController2, cameraModel2);

    VBox vBox = new VBox(cameraView1.asParent(),cameraView2.asParent());
    //blue border
    vBox.setStyle("-fx-padding: 10; -fx-border-style: solid inside;" + 
        "-fx-border-width: 2;  -fx-border-insets: 5;" + 
        "-fx-border-radius: 5; -fx-border-color: blue;");
    Scene scene1 = new Scene(vBox, 400, 300);
    
    // you can only have one scene/pane in a stage at a time, 
    // but a pane can contain multiple panes (Vbox)
    primaryStage.setScene(scene1);
    primaryStage.setTitle("Sample application");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
