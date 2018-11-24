package ca.uvic.seng330.assn3;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class LoginView {
  
  private GridPane view;
  private Organizer organizer;
  
  public LoginView(Organizer organizer) {
    this.organizer = organizer;
    
    createAndConfigurePane();
    
    Button loginB = new Button("Admin");
    loginB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        Client.login(new Admin("kai", "password123"));
      } 
    }));
    
    //Simulate not admin
    Button loginB2 = new Button("User");
    loginB2.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        Client.login(new User("kai", "password123"));
      } 
    }));
    
    view.setAlignment(Pos.TOP_LEFT);
    view.addRow(0, new Label("Login"), loginB, loginB2);
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
    view.setPadding(new Insets(5));
    // black border
    view.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
  }
  
  public Parent asParent() {
    return view ;
  }
}