package ca.uvic.seng330.assn3.sethMVCtesting;

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

  public CameraView(CameraController controller, CameraModel model) {
    
    createAndConfigurePane();
    this.cameraController = controller ;
    
    //Creating Toggle
    Button toggleB = new Button("Toggle Recording"); 
    toggleB.setLayoutX(50); 
    toggleB.setLayoutY(50); 
    
    toggleB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) { 
        cameraController.record();
      } 
   })); 
    
    recordingLabel = new Label();
    recordingLabel.textProperty().bind(cameraController.isModelRecordingProperty().asString());
    
    statusLabel = new Label();
    statusLabel.textProperty().bind(cameraController.aStatus);
    
    view.addRow(0,new Label("Camera Recording:"), recordingLabel);
    view.addRow(0,new Label(), toggleB);
    view.addRow(1, new Label("Camera Status:"), statusLabel);

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
    view.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
  }
  
  public Parent asParent() {
    return view ;
  }
  
  
}
