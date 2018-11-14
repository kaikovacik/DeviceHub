package ca.uvic.seng330.assn3.MVCtestingDevices;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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

/*
 * Code sample from https://stackoverflow.com/questions/36868391/using-javafx-controller-without-fxml/36873768
 */
public class CameraView {

  private GridPane view ;
  private CameraController cameraController ;
  
  private Label recordingLabel;
  private Label statusLabel;
  private Label memoryLabel;
  private Label currentMemoryLabel;
  
  private Button onOffB;
  private Button recordB;
  private Button eraseB;

  public CameraView(CameraController controller) {
    
    createAndConfigurePane();
    this.cameraController = controller ;
    
    statusLabel = new Label();
    statusLabel.textProperty().bind(controller.aStatus);
    
    onOffB = new Button("Start");
    onOffB.setLayoutX(50);
    onOffB.setLayoutY(50);
    onOffB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      
      public void handle(MouseEvent event) {
        if ((controller.aStatus.getValue()).equals("OFF")) {
          controller.turnOn();
          recordingLabel.setText((controller.isModelRecordingProperty().getValue())? "Camera is recording" : "Camera is not recording");
          onOffB.setText("Turn OFF");
          showData();
        } else {
          controller.turnOff();
          onOffB.setText("Start");
          //recordingLabel.setText((controller.isModelRecordingProperty().getValue())? "Camera is recording" : "Camera is not recording");
          hideData();
        }
      } 
    })); 
      
    // The following is only set as visible when camera is on
    recordingLabel = new Label("Camera is not recording");
    
    recordB = new Button("Toggle Recording"); 
    recordB.setLayoutX(50); 
    recordB.setLayoutY(50);  
    recordB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) { 
        controller.record();
        recordingLabel.setText((controller.isModelRecordingProperty().getValue())? "Camera is recording" : "Camera is not recording");
      } 
    })); 

    memoryLabel = new Label();
    memoryLabel.textProperty().bind(controller.diskSpaceLeft().asString());
    currentMemoryLabel = new Label("Memory: ");
    
    eraseB = new Button("Erase Memory");
    eraseB.setLayoutX(50); 
    eraseB.setLayoutY(50); 
    eraseB.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        controller.resetMemory();
      }
    }));    
    
    // Construct UI
    view.addRow(0, new Label("Camera Status:"), statusLabel);
    view.addRow(1, onOffB);
    view.addRow(2, recordB, recordingLabel); 
    view.addRow(3, eraseB, currentMemoryLabel, memoryLabel);
    
    hideData();
  }
  
  private void showData() {
    recordingLabel.setVisible(true);
    recordB.setVisible(true);
    memoryLabel.setVisible(true);
    currentMemoryLabel.setVisible(true);
    eraseB.setVisible(true);
  }
  
  private void hideData() {
    recordingLabel.setVisible(false);
    recordB.setVisible(false);
    memoryLabel.setVisible(false);
    currentMemoryLabel.setVisible(false);
    eraseB.setVisible(false);
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
    // black border
    view.setStyle(
        " -fx-padding: 10; " +
        " -fx-border-color: black; " +
        " -fx-border-radius: 5; " +
        " -fx-box-shadow: 10px; " +
        " -fx-background-color: lightgrey; " +
        " -fx-background-radius: 5; "
        );
  }
  
  public Parent asParent() {
    return view ;
  }
  
}
