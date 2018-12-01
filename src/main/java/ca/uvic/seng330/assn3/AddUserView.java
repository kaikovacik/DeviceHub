package ca.uvic.seng330.assn3;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
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

public class AddUserView {
  
  private GridPane view;
  private Organizer organizer;
  
  public AddUserView(Organizer organizer) {
    this.organizer = organizer;
    
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
    
    createAndConfigurePane();
    
    constructLoginLayout();
  }
  
  private void constructLoginLayout() {
    Label addUserLabel = new Label("Add User");
    addUserLabel.setId("addUserLabel");
    
    Label alertLabel = new Label();
    alertLabel.setId("addUserViewAlertLabel"); // id for ui tests
    alertLabel.setStyle("-fx-font-style: italic");
    
    TextField usernameField = new TextField();
    usernameField.setId("addUserViewUsernameField"); // id for ui tests
    usernameField.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        usernameField.setText("");
        alertLabel.setText("");
      }
    }));   
    
    PasswordField passwordField = new PasswordField();
    passwordField.setId("addUserViewPasswordField"); // id for ui tests
    passwordField.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        passwordField.setText("");
        alertLabel.setText("");
      }
    }));
    
    PasswordField confirmPasswordField = new PasswordField();
    confirmPasswordField.setId("addUserViewConfirmPasswordField"); // id for ui tests
    passwordField.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        passwordField.setText("");
        alertLabel.setText("");
      }
    }));

    Button cancelB = new Button("Cancel");
    cancelB.setId("addUserViewCancelB"); // id for ui tests
    cancelB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        Client.logout();
      } 
    }));
    
    Button addUserB = new Button("Add user");
    addUserB.setId("addUserViewAddUserB"); // id for ui tests
    addUserB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        if (passwordField.getText().equals(confirmPasswordField.getText()) && usernameField.getText().trim().length() > 0) {
          User user = new User(usernameField.getText(), passwordField.getText());
          
          try {
            organizer.addUser(user);
          } catch (HubRegistrationException e) {
            System.out.println(e.getMessage());
          }
          
          Client.logout(user + " added!");
        } else if (usernameField.getText().trim().length() == 0) {
          alertLabel.setText("Invalid username!");
          passwordField.setText("");
          confirmPasswordField.setText("");
        } else {
          alertLabel.setText("Passwords don't match!");
          passwordField.setText("");
          confirmPasswordField.setText("");
        }
      } 
    }));
    
    //view.setAlignment(Pos.CENTER);
    view.addRow(0, addUserLabel, alertLabel);
    view.addRow(1, new Label("Username:"), usernameField);
    view.addRow(2, new Label("Password:"), passwordField);
    view.addRow(3, new Label("Confirm Pass:"), confirmPasswordField);
    view.addRow(4, cancelB, addUserB);
  }
  
  private void createAndConfigurePane() {
    view = new GridPane();
    view.setAlignment(Pos.CENTER);
    view.setHgap(5);
    view.setVgap(10);
    // black border
    view.setStyle(
        " -fx-padding: 10; " +
            " -fx-border-color: black; " +
            " -fx-border-radius: 5; " +
            " -fx-box-shadow: 10px; " +
            " -fx-background-color: linear-gradient(skyblue, lightgrey); " +
            " -fx-background-radius: 5; "
        );
  }
  
  public Parent asParent() {
    return view ;
  }
}