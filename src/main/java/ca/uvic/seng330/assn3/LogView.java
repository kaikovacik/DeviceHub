package ca.uvic.seng330.assn3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import jdk.nashorn.internal.parser.JSONParser;

public class LogView {
  private GridPane view ;
  private Label statusLabel;
  private Organizer organizer;
  private LinkedList<String> logList;
  private ListView<String> list;
  private ObservableList<String> items;
  private int x;
  private int oldMaxDevID;

  public LogView(Organizer pOrganizer) {
    x = 0;
    this.organizer = pOrganizer;
    createAndConfigurePane();
    logList = new LinkedList<>();
    list = new ListView<String>();
    //list.setPrefWidth(50);
    list.setPrefHeight(150);
    items = FXCollections.observableArrayList ();
    list.setItems(items);
    
    readOldLogs();
    
    organizer.getLastLog().addListener((obs, prev, curr) -> {
      addMessage(organizer.getLastLog().getValue());
    });
    
    view.addRow(1, new Label("Previous Activities: "), list);
  
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

  private void addMessage( String message ) {
    JSONObject jsonObj = new JSONObject(message);
    items.add(++x +": " +  jsonObj.getString("payload") + " \t on " + jsonObj.getString("created_at"));
  }
  
  private void readOldLogs() {
    items.add(++x +": Old Activity... ");
    try {
      Path path = Paths.get("src/main/PersistedData/dataLog.txt");
      List<String> lines = Files.readAllLines(path,StandardCharsets.ISO_8859_1);
      for (String line : lines) {
        //JSONObject jsonObj = new JSONObject(line);
        addMessage(line);
      }
    } catch (IOException e) {
      System.out.println(e);
    }
    items.add(++x +": New Activity... ");
  }
  
  
  public Parent asParent() {
    return view ;
  }
}