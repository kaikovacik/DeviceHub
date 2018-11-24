package ca.uvic.seng330.assn3;

public class User {
  protected String username;
  private String password;
  
  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
  
  public void changeName(String username) {
    this.username = username;
  }
  
  public String getUsername() {
    return username;
  }
  
  public Boolean checkPassword(String password) {
    return this.password.equals(password);
  }
  
  public String toString() {
    return "[USER: " + username +"]";
  }
}
