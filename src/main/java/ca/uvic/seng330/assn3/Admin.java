package ca.uvic.seng330.assn3;

public class Admin extends User {

  public Admin(String username, String password) {
    super(username, password);
    this.deviceList = Client.getOrganizer().getViews();
  }
  
  @Override
  public String toString() {
    return "[Admin: " + this.getUsername() +"]";
  }
}