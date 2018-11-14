package ca.uvic.seng330.assn3.MVCtestingDevices;

import ca.uvic.seng330.assn3.devices.Temperature.TemperatureOutofBoundsException;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ThermostatView {
 
  public enum Unit {
    CELSIUS,
    FAHRENHEIT,
    KELVIN
  }

  /** Temperature class. */
  public class Temperature {

    private double celsius;
    private double fahrenheit;
    private double kelvin;

    public Temperature(double temp, Unit unit) {
      switch (unit) {
        case CELSIUS:
          this.celsius = temp;
          this.fahrenheit = temp * 9 / 5 + 32;
          this.kelvin = temp + 273.15;
          break;
        case FAHRENHEIT:
          this.celsius = (temp - 32) * 5 / 9;
          this.fahrenheit = temp;
          this.kelvin = (temp - 32) * 5 / 9 + 273.15;
          break;
        case KELVIN:
          this.celsius = temp - 273.15;
          this.fahrenheit = (temp - 273.15) * 9 / 5 + 32;
          this.kelvin = temp;
          break;
        default:
          return;
      }
    }

    public double getCelsius() {
      return celsius;
    }

    public double getFahrenheit() {
      return fahrenheit;
    }

    public double getKelvin() {
      return kelvin;
    }
    
    public String toStringC() {
      return celsius + "K";
    }
    
    public String toStringF() {
      return fahrenheit + "F";
    }
    
    public String toStringK() {
      return kelvin + "K";
    }
  }
  

  private GridPane view ;
  
  private Label statusLabel;
  private Label settingLabel;
  private Label temperatureLabel;
  
  private TextField temperatureField;
  
  private Button toggleB;
  private Button celsiusB;
  private Button fahrenheitB;

  
  
  public ThermostatView(ThermostatModel model) {
    
    createAndConfigurePane();
    
    statusLabel = new Label();
    statusLabel.textProperty().bind(model.getStatusAsString());
    
    toggleB = new Button("Start");
    toggleB.setLayoutX(50);
    toggleB.setLayoutY(50);
    toggleB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        if ((model.getStatusAsString().getValue()).equals("OFF")) {
          model.turnOn();
          toggleB.setText("Turn OFF");
          showData();
        } else if ((model.getStatusAsString().getValue()).equals("NORMAL") || (model.getStatusAsString().getValue()).equals("ERROR")) {
          model.turnOff();
          toggleB.setText("Start");
          hideData();
        }
      } 
    })); 
      
    // The following is only set as visible when camera is on
    settingLabel = new Label("Set Thermostat:");
    temperatureLabel = new Label("0C");
    
    temperatureField = new TextField();
    configTextFieldForInts(temperatureField);
    temperatureField.setMaxWidth(40);
    temperatureField.setText("");
    
    celsiusB = new Button("Celsius");
    celsiusB.setLayoutX(50);
    celsiusB.setLayoutY(50);
    celsiusB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        if (temperatureField.getText().length() == 0) {
          System.err.println("ALERT"); //change to an actual alert
          return;
        }
        try {
          model.setSetting(Integer.parseInt(temperatureField.getText()));
          temperatureLabel.setText(temperatureField.getText() + "C");
          temperatureField.setText("");
        } catch (ThermostatModel.TemperatureOutofBoundsException e) {
          System.err.println("ALERT"); //change to an actual alert
        }   
      } 
    })); 
    
    fahrenheitB = new Button("Fahrenheit");
    fahrenheitB.setLayoutX(50);
    fahrenheitB.setLayoutY(50);
    fahrenheitB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        if (temperatureField.getText().length() == 0) { 
          System.err.println("ALERT"); //change to an actual alert
          return;
        }
        try {
          model.setSetting(Integer.parseInt(temperatureField.getText()));
          temperatureLabel.setText((Integer.parseInt(temperatureField.getText()) * 9 / 5 + 32) + "F");
          temperatureField.setText("");
        } catch (ThermostatModel.TemperatureOutofBoundsException e) {
          System.err.println("ALERT"); //change to an actual alert
        }  
      } 
    })); 
    
    // Construct UI
    view.addRow(0, new Label("Thermostat Status:"), statusLabel);
    view.addRow(1, toggleB);
    view.addRow(2, settingLabel, temperatureField, temperatureLabel, celsiusB, fahrenheitB);
    
    hideData();
  }
  
  private void showData() {
    settingLabel.setVisible(true);
    temperatureField.setVisible(true);
    temperatureLabel.setVisible(true);
    celsiusB.setVisible(true);
    fahrenheitB.setVisible(true);
  }
  
  private void hideData() {
    settingLabel.setVisible(false);
    temperatureField.setVisible(false);
    temperatureLabel.setVisible(false);
    celsiusB.setVisible(false);
    fahrenheitB.setVisible(false);
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
  
  private void configTextFieldForInts(TextField field) {
    field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
      if (c.getControlNewText().matches("-?\\d*")) {
        return c ;
      }
      return null ;
    }));
  }
  
}