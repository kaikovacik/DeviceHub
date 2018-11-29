package ca.uvic.seng330.assn3;


import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.User;
import ca.uvic.seng330.assn3.devices.DeviceView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class UserView {

  private GridPane view;
  private User user;
  private Organizer organizer;

  UserView(User user, Organizer organizer) {

    this.user = user;
    this.organizer = organizer;
    this.view = new GridPane();

    createAndConfigurePane();

    Label titleLabel = new Label((user instanceof Admin)? "ADMIN:" : "USER:");
    Label nameLabel = new Label(user.getUsername());

    view.addRow(0, titleLabel, nameLabel);

    if (!(user instanceof Admin)) {
      MenuButton linkedDeviceMenu = new MenuButton("Linked devices"); 

      for (DeviceView d : user.getDevices().values()) {           // when switch to tab
        MenuItem linkedMenuItem = new MenuItem(d.toString());

        linkedMenuItem.setOnAction((new EventHandler<ActionEvent>() { 
          public void handle(ActionEvent event) {
            user.removeDevice(d.getModel().getIdentifier());
            linkedDeviceMenu.getItems().remove(linkedMenuItem);
            organizer.logString(d.getModel().getName() + " ("+ d.getModel().getIdentifier() + ") hidden from " + user.getUsername());
          } 
        }));
        linkedDeviceMenu.getItems().add(linkedMenuItem);
      }

      MenuButton deviceMenu = new MenuButton("Devices");
      for (DeviceView d : organizer.getViews().values()) {  
        // If not already linked, add to linked devices UI
        if (!user.getDevices().containsValue(d)) {
          MenuItem menuItem = new MenuItem(d.toString());
          deviceMenu.getItems().add(menuItem);
          menuItem.setOnAction((new EventHandler<ActionEvent>() {   //if clicked, add to linkedDeviceMenu, add to user list, remove from deviceMenu
            public void handle(ActionEvent event) {

              MenuItem linkedMenuItem = new MenuItem(d.toString());
              linkedMenuItem.setOnAction((new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent event) {
                  user.removeDevice(d.getModel().getIdentifier());
                  linkedDeviceMenu.getItems().remove(linkedMenuItem);
                  organizer.logString(d.getModel().getName() + " ("+ d.getModel().getIdentifier() + ") hidden from " + user.getUsername());
                  deviceMenu.getItems().add(menuItem);
                } 
              }));
              linkedDeviceMenu.getItems().add(linkedMenuItem);

              // Link device
              try {
                user.addDevice(d);
                deviceMenu.getItems().remove(menuItem);
                organizer.logString(d.getModel().getName() + " ("+ d.getModel().getIdentifier() + ") registered to " + user.getUsername());
              } catch (Exception e) {
                System.err.println("Unable to link " + d);
              }
            } 
          }));

          
        }
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
