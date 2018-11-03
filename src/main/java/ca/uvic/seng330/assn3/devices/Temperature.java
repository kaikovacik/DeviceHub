package ca.uvic.seng330.assn3.devices;

/** Temperature class. */
public class Temperature {

  public class TemperatureOutofBoundsException extends Exception {

    public TemperatureOutofBoundsException(String message) {
      super(message);
    }
  }
  public enum Unit {
    CELSIUS,
    FAHRENHEIT,
    KELVIN
  }
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
}