package ca.uvic.seng330.assn3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.json.JSONObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class LogView {
  private GridPane view ;
  private Organizer organizer;
  private ListView<String> list;
  private ObservableList<String> items;
  private int x;

  public LogView(Organizer pOrganizer) {
    x = 0;
    this.organizer = pOrganizer;
    createAndConfigurePane();
    list = new ListView<String>();
    list.setPrefHeight(150);
    items = FXCollections.observableArrayList ();
    list.setItems(items);
        
    readOldLogs();
    
    organizer.getLastLog().addListener((obs, prev, curr) -> {
      addMessage(organizer.getLastLog().getValue());
    });
    
    //list.scrollTo(list.getItems().size()-1);
    view.addRow(1, new Label("Logs: "), list);
  
  }

  private void addMessage( String message ) {
    JSONObject jsonObj = new JSONObject(message);
    items.add(++x +": \t" +  jsonObj.getString("payload") + " \t on " + jsonObj.getString("created_at"));
  }
  
  private void readOldLogs() {
    items.add(++x +": Old Activity... ");
    try {
      Path path = Paths.get("src/main/PersistedData/dataLog.txt");
      List<String> lines = Files.readAllLines(path,StandardCharsets.ISO_8859_1);
      for (String line : lines) {
        addMessage(line);
      }
    } catch (IOException e) {
      System.out.println(e);
    }
    items.add(++x +": New Activity... ");
  }
  
  private void createAndConfigurePane() {
    view = new GridPane();

    ColumnConstraints leftCol = new ColumnConstraints();
    leftCol.setHalignment(HPos.RIGHT);
    leftCol.setHgrow(Priority.NEVER);

    ColumnConstraints rightCol = new ColumnConstraints();
    rightCol.setHgrow(Priority.SOMETIMES);

    view.getColumnConstraints().addAll(leftCol, rightCol);
    //view.setAlignment(Pos.CENTER);
    view.setHgap(5);
    view.setVgap(10);
    view.borderProperty();
    // black borders
    view.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
  }
  
  public Parent asParent() {
    return view ;
  }
}
