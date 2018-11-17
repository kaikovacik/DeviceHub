package ca.uvic.seng330.assn3;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/*
 * Code sample from https://stackoverflow.com/questions/36868391/using-javafx-controller-without-fxml/36873768
 */
public class AllertView {

  private GridPane view ;
  //private CameraController cameraController ;
  private Label statusLabel;
  private Organizer organizer;

  public AllertView(Organizer pOrganizer) {
    this.organizer = pOrganizer;
    createAndConfigurePane();
 
    //recordingLabel.textProperty().bind(cameraController.isModelRecordingProperty().asString());
    statusLabel = new Label();
    statusLabel.textProperty().bind(organizer.getLastAllert());
    
    view.addRow(1, new Label("Allerts: "), statusLabel);

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
