package ca.uvic.seng330.assn3;

public class UnknownUserException extends Exception {

  private static final long serialVersionUID = 1L;

  public UnknownUserException(String message) {
    super(message);
  }
}