package ca.uvic.seng330.assn3.MVCtesting;

import ca.uvic.seng330.assn3.MVCtestingDevices.CameraController;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.CameraView;
import ca.uvic.seng330.assn3.MVCtestingDevices.ThermostatModel;
import ca.uvic.seng330.assn3.MVCtestingDevices.ThermostatView;
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
import javafx.scene.layout.VBox;

public class ConfigureView {
  private Button addCameraB;
  private GridPane view;
  private Label statusLabel;
  private Organizer organizer;
  private VBox vBox;


  public ConfigureView(Organizer pOrganizer) {
    this.organizer = pOrganizer;
    createAndConfigurePane();

    //vBox = new VBox(view);

    addCameraB = new Button("+");
    addCameraB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        addCamera();
      } 
    })); 
    view.addRow(0, new Label("Add Camera:"), addCameraB);
  }

  public void addCamera() {
    CameraModel cameraModel1 = new CameraModel();
    CameraController cameraController1 = new CameraController(cameraModel1, organizer);
    CameraView cameraView1 = new CameraView(cameraController1, organizer);

     // reg done in controller
    /*try {
      organizer.register(thermostatModel);
    } catch (HubRegistrationException e) {
      System.out.println("Error Line " + new Exception().getStackTrace()[0].getLineNumber());
      e.printStackTrace();
    }*/

    //vBox.getChildren().add(thermostatView.asParent());
    //numOfThermostats++;
    //System.out.println(numOfThermostats);
  }

  public void removeCamera(ThermostatView view) {
    //vBox.getChildren().remove(view);

    //numOfThermostats--;
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
