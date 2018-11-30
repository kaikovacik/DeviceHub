package ca.uvic.seng330.assn3;


import java.io.File;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class VidTest2 extends Application{

  StackPane stack = new StackPane();



  public void start(Stage primaryStage) throws Exception {

    //Media media = new Media("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv");

    //MediaPlayer player = new MediaPlayer(media);


    final File file = new File("oow2010-2.flv");
    final String MEDIA_URL = file.toURI().toString();

    // Create a Media
    final Media media = new Media(MEDIA_URL);

    // Create a Media Player
    final MediaPlayer player = new MediaPlayer(media);

    primaryStage.setScene(new Scene(new Group(new MediaView(player)), 540, 208));
    primaryStage.setTitle("Title");
    primaryStage.show();

    player.play();
  }

  public static void main(String [] args) {
    Application.launch();
  }

}