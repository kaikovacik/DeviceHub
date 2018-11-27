package ca.uvic.seng330.assn3;

import java.util.Collection;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class UserView {
  
  private GridPane view;
  private Collection<User> users;
  
  UserView(Collection<User> users) {
    this.users = users;
    this.view = new GridPane();
    
    int i = 0;
    for (User user : users) {
      view.addRow(i++, new Label(user.toString()));
    }
  }
  
  public Parent asParent() {
    return view;
  }
}
