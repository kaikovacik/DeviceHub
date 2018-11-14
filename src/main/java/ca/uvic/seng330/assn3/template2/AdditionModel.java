package ca.uvic.seng330.assn3.template2;

/*
* Code sample from https://stackoverflow.com/questions/36868391/using-javafx-controller-without-fxml/36873768
 */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;

public class AdditionModel {
  private final IntegerProperty x = new SimpleIntegerProperty();
  private final IntegerProperty y = new SimpleIntegerProperty();
  private final ReadOnlyIntegerWrapper sum = new ReadOnlyIntegerWrapper();

  public AdditionModel() {
    sum.bind(x.add(y));
  }

  public final IntegerProperty xProperty() {
    return x;
  }

  public final int getX() {
    return x.get();
  }

  public final void setX(final int x) {
    this.x.set(x);
  }

  public final IntegerProperty yProperty() {
    return this.y;
  }

  public final int getY() {
    return this.yProperty().get();
  }

  public final void setY(final int y) {
    this.yProperty().set(y);
  }

  public final javafx.beans.property.ReadOnlyIntegerProperty sumProperty() {
    return this.sum.getReadOnlyProperty();
  }
  

  public final int getSum() {
    return this.sumProperty().get();
  }

}