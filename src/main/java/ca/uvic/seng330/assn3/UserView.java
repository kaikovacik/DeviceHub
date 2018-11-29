package ca.uvic.seng330.assn3;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ca.uvic.seng330.assn3.Client;
import ca.uvic.seng330.assn3.IncorrectPasswordException;
import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.UnknownUserException;
import ca.uvic.seng330.assn3.User;
import ca.uvic.seng330.assn3.devices.DeviceView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class UserView {

  private GridPane view;
  private User model;
  private Organizer organizer;

  UserView(User model, Organizer organizer) {

    this.model = model;
    this.organizer = organizer;
    this.view = new GridPane();

    createAndConfigurePane();

    Label titleLabel = new Label((model instanceof Admin)? "ADMIN:" : "USER:");
    Label nameLabel = new Label(model.getUsername());

    view.addRow(0, titleLabel, nameLabel);
    
    if (!(model instanceof Admin)) {
      MenuButton linkedDeviceMenu = new MenuButton("Linked devices"); 
      for (DeviceView d : model.getDevices().values()) {
        MenuItem menuItem = new MenuItem(d.toString());
        menuItem.setOnAction((new EventHandler<ActionEvent>() { 
          public void handle(ActionEvent event) {
            model.removeDevice(d.getModel().getIdentifier());
            linkedDeviceMenu.getItems().remove(menuItem);
          } 
        }));

        linkedDeviceMenu.getItems().add(menuItem);
      }

      MenuButton deviceMenu = new MenuButton("Devices");
      for (DeviceView d : organizer.getViews().values()) {
        MenuItem menuItem = new MenuItem(d.toString());
        menuItem.setOnAction((new EventHandler<ActionEvent>() { 
          public void handle(ActionEvent event) {
            try {
              model.addDevice(d);
              MenuItem menuItem = new MenuItem(d.toString());
              menuItem.setOnAction((new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent event) {
                  model.removeDevice(d.getModel().getIdentifier());
                  linkedDeviceMenu.getItems().remove(menuItem);
                } 
              }));
              linkedDeviceMenu.getItems().add(menuItem);
            } catch (Exception e) {
              System.err.println("Unable to link " + d);
            }
          } 
        }));

        deviceMenu.getItems().add(menuItem);
      }
      view.addRow(0, new Label("Link existing device:"), deviceMenu, new Label("Unlink Device:"), linkedDeviceMenu);
    }
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
