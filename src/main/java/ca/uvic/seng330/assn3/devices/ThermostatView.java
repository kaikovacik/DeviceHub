package ca.uvic.seng330.assn3.devices;

import ca.uvic.seng330.assn3.HubRegistrationException;
import ca.uvic.seng330.assn3.Organizer;
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

public class ThermostatView extends DeviceView {
 
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
  private Label celsiusLabel;
  private Label fahrenheitLabel;
  
  private TextField temperatureField;
  
  private Button toggleB;
  private Button celsiusB;
  private Button fahrenheitB;
  private ThermostatModel model;
  
 
  public ThermostatView(Organizer organizer) {
    
    super(organizer);
    this.model = new ThermostatModel(organizer.deviceCount);
    organizer.addView(this);
    
    // Add to organizer
    try { 
      organizer.register(this);
      organizer.alert(this, ("Thermostat (" + model.getIdentifier() + ") added"));
    } catch (HubRegistrationException e) {
      System.out.println("Error Line " + new Exception().getStackTrace()[0].getLineNumber());
      e.printStackTrace();
    }
    
    createAndConfigurePane();
    
    statusLabel = new Label();
    statusLabel.setId("thermostatStatusLabel");
    statusLabel.textProperty().bind(model.getStatusAsString());
    
    toggleB = new Button("Start");
    toggleB.setId("thermostatOnOffB");
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
      
    // The following is only set as visible when thermostat is on
    settingLabel = new Label("Set Thermostat:");
    celsiusLabel = new Label("0C");
    celsiusLabel.setId("thermostatCelsiusLabel");
    fahrenheitLabel = new Label("32F");
    fahrenheitLabel.setId("thermostatFahrenheitLabel");
    
    temperatureField = new TextField();
    configTextFieldForInts(temperatureField);
    temperatureField.setId("thermostatTemperatureField");
    temperatureField.setMaxWidth(40);
    temperatureField.setText("");
    
    celsiusB = new Button("Celsius");
    celsiusB.setId("thermostatCelsiusB");
    celsiusB.setLayoutX(50);
    celsiusB.setLayoutY(50);
    celsiusB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        if (temperatureField.getText().length() == 0) {
          System.err.println("Warning: invalid entry"); //change to an actual alert
          return;
        }
        try {
          int data = Integer.parseInt(temperatureField.getText());
          model.setSetting(data);
          celsiusLabel.setText(data + "C");
          fahrenheitLabel.setText((data * 9 / 5 + 32) + "F");
          temperatureField.setText("");
        } catch (ThermostatModel.TemperatureOutofBoundsException e) {
          System.err.println((Integer.parseInt(temperatureField.getText()) > 50)? "Warning: entry too large" : "Warning: entry too small"); //change to an actual alert
        }   
      } 
    })); 
    
    fahrenheitB = new Button("Fahrenheit");
    fahrenheitB.setId("thermostatFahrenheitB");
    fahrenheitB.setLayoutX(50);
    fahrenheitB.setLayoutY(50);
    fahrenheitB.setOnMouseClicked((new EventHandler<MouseEvent>() { 
      public void handle(MouseEvent event) {
        if (temperatureField.getText().length() == 0) { 
          System.err.println("ALERT"); //change to an actual alert
          return;
        }
        try {
          model.setSetting((Integer.parseInt(temperatureField.getText()) - 32) * 5 / 9);
          celsiusLabel.setText((Integer.parseInt(temperatureField.getText()) - 32) * 5 / 9 + "C");
          fahrenheitLabel.setText(Integer.parseInt(temperatureField.getText()) + "F");
          temperatureField.setText("");
        } catch (ThermostatModel.TemperatureOutofBoundsException e) {
          System.err.println("ALERT"); //change to an actual alert
        }  
      } 
    })); 
    
    // Construct UI
    view.addRow(0, new Label("Thermostat Status:"), statusLabel, new Label("Device ID:"), new Label(""+ (organizer.deviceCount)));
    view.addRow(1, toggleB);
    view.addRow(2, settingLabel, temperatureField, celsiusB, fahrenheitB);
    view.addRow(3, celsiusLabel, fahrenheitLabel);
    
    hideData();
  }
  
  private void showData() {
    settingLabel.setVisible(true);
    temperatureField.setVisible(true);
    celsiusLabel.setVisible(true);
    fahrenheitLabel.setVisible(true);
    celsiusB.setVisible(true);
    fahrenheitB.setVisible(true);
  }
  
  private void hideData() {
    settingLabel.setVisible(false);
    temperatureField.setVisible(false);
    celsiusLabel.setVisible(false);
    fahrenheitLabel.setVisible(false);
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