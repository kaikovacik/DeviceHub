package ca.uvic.seng330.assn3;

import org.junit.Test;

public class BasicTests {
  
  @Test
  public void testShitWorks() {
    System.out.println("hi");
    //return true;
  }
  
  @Test
  public void testBasicMVC() {
    AdditionModel model = new AdditionModel();
    AdditionController controller = new AdditionController(model);
    AdditionView view = new AdditionView(controller, model);
  }
}
