package ca.uvic.seng330.assn3;

import org.junit.Test;
import ca.uvic.seng330.assn3.*;

public class BasicTests {
  
  @Test
  public void testShitWorks() {
    System.out.println("hi");
    //return true;
  }
  
  @Test
  public void testBasicMVC() {
    //    AdditionModel model = new AdditionModel();
    //    AdditionController controller = new AdditionController(model);
    //    AdditionView view = new AdditionView(controller, model);

    System.out.println("Test Basic MVC");

    HubController hubC = new HubController();
    Camera camera1 = new Camera(hubC);
    Thermostat thermostat1 = new Thermostat(hubC);
    Lightbulb lightbulb1 = new Lightbulb(hubC);
    SmartPlug smartplug1 = new SmartPlug(hubC);

    hubC.register(camera1);
    hubC.register(thermostat1);
    hubC.register(lightbulb1);
    hubC.register(smartplug1);

  }
  
}
