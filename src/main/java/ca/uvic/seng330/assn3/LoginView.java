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

public class LoginView {
  
  private GridPane view;
  private Organizer organizer;
  
  private final String initialMessage;
  
  public LoginView(Organizer organizer) {
    this(null, organizer);
  }
  
  public LoginView(String initialMessage, Organizer organizer) {
    this.initialMessage = initialMessage;
    this.organizer = organizer;
    
    createAndConfigurePane();
    
    constructLoginLayout();
  }
  
  private void constructLoginLayout() {
    
    Label alertLabel = new Label(initialMessage);
    alertLabel.setId("alertLabel"); // id for ui tests
    alertLabel.setStyle("-fx-font-style: italic");
    
    TextField usernameField = new TextField();
    usernameField.setId("usernameField"); // id for ui tests
    usernameField.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        usernameField.setText("");
        alertLabel.setText("");
      }
    }));   
    
    PasswordField passwordField = new PasswordField();
    passwordField.setId("passwordField"); // id for ui tests
    passwordField.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        passwordField.setText("");
        alertLabel.setText("");
      }
    }));
    
    Button loginB = new Button("Login");
    loginB.setId("loginB"); // id for ui tests
    loginB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        try {
          Client.login(usernameField.getText(), passwordField.getText());
        } catch (UnknownUserException e) {
          alertLabel.setText("Unknown user!");
        } catch (IncorrectPasswordException e) {
          alertLabel.setText("Incorrect password!");
        }
      } 
    }));
    
    // TEMPORARY
    Button kaiBypassB = new Button("\"kai\"");
    kaiBypassB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        try {
          Client.login("kai", "iak");
        } catch (UnknownUserException e) {
          alertLabel.setText("Unknown user!");
        } catch (IncorrectPasswordException e) {
          alertLabel.setText("Incorrect password!");
        }
      } 
    }));
    
    Button sethBypassB = new Button("\"seth\"");
    sethBypassB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        try {
          Client.login("seth", "htes");
        } catch (UnknownUserException e) {
          alertLabel.setText("Unknown user!");
        } catch (IncorrectPasswordException e) {
          alertLabel.setText("Incorrect password!");
        }
      } 
    }));
    
    Button guestBypassB = new Button("\"guest\"");
    guestBypassB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        try {
          Client.login("guest", "tseug");
        } catch (UnknownUserException e) {
          alertLabel.setText("Unknown user!");
        } catch (IncorrectPasswordException e) {
          alertLabel.setText("Incorrect password!");
        }
      } 
    }));
    // END TEMPORARY
    
    Button newUserB = new Button("New user");
    newUserB.setId("newUserB"); // id for ui tests
    newUserB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        Client.addUserLayout();
      } 
    }));
    
    //view.setAlignment(Pos.CENTER);
    view.addRow(0, new Label("Login"), alertLabel);
    view.addRow(1, new Label("Username:"), usernameField);
    view.addRow(2, new Label("Password:"), passwordField);
    view.addRow(3, loginB, newUserB);
    
    // TEMPORARY
    view.addRow(4, kaiBypassB, sethBypassB, guestBypassB);
    // END TEMPORARY
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