package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.HubRegistrationException;
import ca.uvic.seng330.assn3.Organizer;
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

public class LightbulbView extends DeviceView{
 
  private GridPane view;
  private Label statusLabel;
  private Button toggleB;
  private LightbulbModel model;
  public int index;

  
  public LightbulbView(Organizer organizer) {
    
    super(organizer);
    try { 
      organizer.register(this);
      this.model = new LightbulbModel(organizer);
      super.setModel(model);
   
    } catch (HubRegistrationException e) {
      System.out.println("Error Line " + new Exception().getStackTrace()[0].getLineNumber());
      e.printStackTrace();
    }
    
    createAndConfigurePane();
    
    statusLabel = new Label("OFF");
    statusLabel.setId("lightbulbStatusLabel"); // id for ui tests
    statusLabel.textProperty().bind(model.getStatusAsString());
    
    toggleB = new Button("Turn ON");
    toggleB.setId("lightbulbToggleB"); // id for ui tests
    toggleB.setLayoutX(50);
    toggleB.setLayoutY(50);
    toggleB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        model.toggle();
      } 
    }));
    
    model.getStatusAsString().addListener((obs, prev, curr) -> {
      if (curr.equals("NORMAL")) {
        toggleB.setText("Turn OFF");
      }
      else if (curr.equals("OFF")) {
        toggleB.setText("Turn ON");
      }
    });
    
    // Construct UI
    view.addRow(1, new Label("Lightbulb Status:"), statusLabel, new Label("Device ID:"), new Label(""+(organizer.deviceCount)));
    view.addRow(2, toggleB);
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
    view.setStyle(
        " -fx-padding: 10; " +
        " -fx-border-color: black; " +
        " -fx-border-radius: 5; " +
        " -fx-box-shadow: 10px; " +
        " -fx-background-color: linear-gradient(white, lightgrey); " +
        " -fx-background-radius: 5; "
        );
  }
  
  public Parent asParent() {
    return view ;
  }
}