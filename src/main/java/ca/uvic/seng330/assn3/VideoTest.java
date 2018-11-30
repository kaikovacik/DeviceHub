package ca.uvic.seng330.assn3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class VideoTest extends Application {

  public static void main(String[] args) { launch(args); }

  @Override public void start(Stage stage) throws Exception {
    WebView webview = new WebView();
    webview.getEngine().load(
        "https://www.youtube.com/embed/vIeFt88Hm8s"
        );
    webview.setPrefSize(640, 390);

    stage.setScene(new Scene(webview));
    stage.show();
  }    
}