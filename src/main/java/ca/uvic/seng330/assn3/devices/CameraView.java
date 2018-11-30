package ca.uvic.seng330.assn3.devices;

import java.net.URL;

import ca.uvic.seng330.assn3.Organizer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class CameraView extends DeviceView{

  private GridPane view;
  private CameraModel model;

  private Label recordingLabel;
  private Label statusLabel;
  private Label memoryLabel;
  private Label currentMemoryLabel;
  private Button onOffB;
  private Button recordB;
  private Button eraseB;
  private Button mediaControlB;
  private MediaView mediaView;

  public CameraView(Organizer organizer) {    
    super(organizer);
    try {
      organizer.register(this);
      this.model = new CameraModel(organizer);
      super.setModel(model);   

    } catch (Exception e) {
      System.out.println("Reg Error: Error Line " + new Exception().getStackTrace()[0].getLineNumber());
      e.printStackTrace();
    }

    createAndConfigurePane();

    statusLabel = new Label("OFF");
    statusLabel.setId("cameraStatusLabel"); // id for ui tests
    statusLabel.textProperty().bind(model.getStatusAsString());

    onOffB = new Button("Start");
    onOffB.setId("cameraOnOffB"); // id for ui tests
    onOffB.setLayoutX(50);
    onOffB.setLayoutY(50);
    onOffB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        if ((model.getStatusAsString().getValue()).equals("OFF")) {
          model.turnOn();
          recordingLabel.setText((model.getIsRecording())? "Camera is recording" : "Camera is not recording");
        } else {
          model.turnOff();
        }
      } 
    })); 
    // seperated from the above method so that shutdown works.
    // OnOffB is not used during shutdown, so its handler doesnt trigger
    model.getStatusAsString().addListener((obs, prev, curr) -> {
      if (curr.equals("NORMAL")) {
        onOffB.setText("Turn OFF"); 
        showData();
      }
      else if (curr.equals("OFF")) {
        onOffB.setText("Start"); 
        hideData();
      }
    });

    // The following is only set as visible when camera is on
    recordingLabel = new Label("Camera is not recording");
    recordingLabel.setId("cameraRecordingLabel"); // id for ui tests
    
    recordB = new Button("Toggle Recording"); 
    recordB.setId("cameraRecordB"); // id for ui tests
    recordB.setLayoutX(50); 
    recordB.setLayoutY(50);  
    recordB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) { 
        model.record();
        recordingLabel.setText((model.isThisRecording().getValue())? "Camera is recording" : "Camera is not recording");
      } 
    })); 

    memoryLabel = new Label();
    memoryLabel.setId("cameraMemoryLabel"); // id for ui tests
    memoryLabel.textProperty().bind(model.diskSizeRemaining().asString());
    
    // set as variable such that it can be hidden
    currentMemoryLabel = new Label("Memory: ");

    eraseB = new Button("Erase Memory");
    eraseB.setId("cameraEraseB"); // id for ui tests
    eraseB.setLayoutX(50); 
    eraseB.setLayoutY(50); 
    eraseB.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        model.resetMemory();
      }
    }));   
    
    //Media media = new Media(getClass().getResource("video.mp4").toExternalForm());
    Media media = new Media("https://www.youtube.com/embed/vIeFt88Hm8s");
    
    MediaPlayer player = new MediaPlayer(media);
    player.setAutoPlay(false);
    
    mediaView = new MediaView(player);
    mediaView.setId("cameraMediaView"); // id for ui tests
    mediaView.setFitHeight(200.0);
    mediaView.setPreserveRatio(true);
    
    mediaControlB = new Button("Play");
    mediaControlB.setId("cameraMediaControlB"); // id for ui tests
    mediaControlB.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        switch (mediaControlB.getText()) {
        case "Play":
          System.out.println("Playing");
          player.play();
          mediaControlB.setText("Pause");
          break;
        case "Pause":
          System.out.println("Pausing");
          player.pause();
          mediaControlB.setText("Play");
        }
      }
    }));  

    // Construct UI
    view.addRow(0, new Label("Camera Status:"), statusLabel, new Label("Device ID:"), new Label(""+(organizer.deviceCount)));
    view.addRow(1, onOffB);
    view.addRow(2, recordB, recordingLabel); 
    view.addRow(3, eraseB, currentMemoryLabel, memoryLabel);
    view.addRow(4, mediaControlB);
    view.add(mediaView, 0, 4, 3, 1);
    
    hideData();
  }

  private void showData() {
    recordingLabel.setVisible(true);
    recordB.setVisible(true);
    memoryLabel.setVisible(true);
    currentMemoryLabel.setVisible(true);
    eraseB.setVisible(true);
    mediaControlB.setVisible(true);
    mediaView.setVisible(true);
    mediaView.setFitHeight(200.0);
  }

  private void hideData() {
    recordingLabel.setVisible(false);
    recordB.setVisible(false);
    memoryLabel.setVisible(false);
    currentMemoryLabel.setVisible(false);
    eraseB.setVisible(false);
    mediaControlB.setVisible(false);
    mediaView.setVisible(false);
    mediaView.setFitHeight(0.1);
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
    return view;
  }
}