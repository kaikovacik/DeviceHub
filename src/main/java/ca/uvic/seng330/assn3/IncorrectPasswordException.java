package ca.uvic.seng330.assn3;

public class IncorrectPasswordException extends Exception {

  private static final long serialVersionUID = 1L;

  public IncorrectPasswordException(String message) {
    super(message);
  }
}