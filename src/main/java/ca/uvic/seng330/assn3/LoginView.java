package ca.uvic.seng330.assn3;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    
    User kai = new Admin("kai", "iak");
    User seth = new Admin("seth", "htes");
    User guest = new User("guest", "tseug");
    
    
    try {
      organizer.addUser(kai);
      organizer.addUser(seth);
      organizer.addUser(guest);
    } catch (HubRegistrationException e) {
      System.out.println(e.getMessage());
    }
    
    TextField usernameField = new TextField();
    usernameField.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        usernameField.setText("");
      }
    }));   
    
    PasswordField passwordField = new PasswordField();
    passwordField.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        passwordField.setText("");
      }
    }));
    
    Button loginB = new Button("login");
    loginB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        try {
          Client.login(usernameField.getText(), passwordField.getText());
        } catch (UnknownUserException e) {
          usernameField.setText("Unknown user!");
        } catch (IncorrectPasswordException e) {
          usernameField.setText("Incorrect password!");
        }
      } 
    }));
    
    // Temporary bypass logs in as "kai" with pass "iak"
    Button bypassB = new Button("bypass");
    bypassB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        try {
          Client.login("kai", "iak");
        } catch (UnknownUserException e) {
          usernameField.setText("Unknown user!");
        } catch (IncorrectPasswordException e) {
          usernameField.setText("Incorrect password!");
        }
      } 
    }));
    
//    Button loginB = new Button("Admin");
//    loginB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
//      public void handle(MouseEvent event) {
//        Client.login(new Admin("kai", "password123"));
//      } 
//    }));
//    
//    //Simulate not admin
//    Button loginB2 = new Button("User");
//    loginB2.setOnMouseClicked((new EventHandler<MouseEvent>() { 
//      public void handle(MouseEvent event) {
//        Client.login(new User("kai", "password123"));
//      } 
//    }));
    
    view.setAlignment(Pos.BOTTOM_LEFT);
    view.addRow(0, new Label("Login"));
    view.addRow(1, new Label("Username:"), usernameField);
    view.addRow(2, new Label("Password:"), passwordField);
    view.addRow(3, loginB, bypassB);
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