package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.Admin;
import ca.uvic.seng330.assn3.AlertsView;
import ca.uvic.seng330.assn3.HubRegistrationException;
import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.User;
import ca.uvic.seng330.assn3.devices.CameraView;
import ca.uvic.seng330.assn3.devices.LightbulbView;
import ca.uvic.seng330.assn3.devices.SmartPlugView;
import ca.uvic.seng330.assn3.devices.ThermostatView;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class Client extends Application {

  private static Group root;
  private static Scene scene;
  private static Organizer organizer;
  private static User currentUser;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Scene scene = createScene();

    // load stylesheet
    scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.show();
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      public void handle(WindowEvent we) {
        closeClient();
      }
    });     
  }

  // scene object for unit tests
  public static Scene createScene() { 
    organizer = new Organizer();
   
    try {
      organizer.addUser(new Admin("kai", "iak"));
      organizer.addUser(new Admin("seth", "htes"));
      organizer.addUser(new User("guest", "tseug"));
    } catch (HubRegistrationException e) {
      System.out.println(e.getMessage());
    }

    root = new Group();
    scene = new Scene(root, 720, 480);

    logout();

    return scene;
  }
  
  public static void logout() {
    logout(null);
  }
  public static void logout(String initialMessage) {
    root.getChildren().clear(); 

    BorderPane mainPane = new BorderPane();
    LoginView loginView = (initialMessage == null)? new LoginView(organizer) : new LoginView(initialMessage, organizer);

    //mainPane.prefHeightProperty().bind(scene.heightProperty().divide(2));
    //mainPane.prefWidthProperty().bind(scene.widthProperty().divide(2));
    mainPane.setCenter(loginView.asParent());

    root.getChildren().add(mainPane);
  }
  
  public static void addUserLayout() {
    root.getChildren().clear(); 
    
    BorderPane mainPane = new BorderPane();
    AddUserView addUserView = new AddUserView(organizer);

    mainPane.prefHeightProperty().bind(scene.heightProperty().divide(2));
    //mainPane.prefWidthProperty().bind(scene.widthProperty().divide(2));
    mainPane.setCenter(addUserView.asParent());

    root.getChildren().add(mainPane);
  }

  public static void login(String username, String password) throws UnknownUserException, IncorrectPasswordException {
    if (!organizer.getUsers().containsKey(username)) throw new UnknownUserException("Unknown user!");    
    if (!organizer.getUsers().get(username).checkPassword(password)) throw new IncorrectPasswordException("Incorrect password!");

    root.getChildren().clear();

    currentUser = organizer.getUsers().get(username);
    
    // Login info to console
    System.out.println(currentUser + 
      " logged in and has access to " + 
      ((currentUser instanceof Admin)? 
        "ALL existing devices." 
      : ((currentUser.getDevices().size() == 0)?
          "NO devices"
        : "the following devices:\n" + 
          currentUser.getDevices().values())));
      
    BorderPane mainPane = new BorderPane();
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

    // Only show configure tab when user is an admin
    if (currentUser instanceof Admin) {

      // Configure Tab
      ConfigureView configureView = new ConfigureView(organizer);
      LogView prevActivitiesView = new LogView(organizer);
      VBox configVbox = new VBox();
      Tab configTab = new Tab();

      configTab.setText("Device Configuration");
      configVbox.getChildren().add(configureView.asParent() );
      configVbox.getChildren().add(prevActivitiesView.asParent() );
      configTab.setContent(configVbox);
      tabPane.getTabs().add(configTab);
      
      // User Tab
      Tab userTab = new Tab("Users");
      userTab.setId("userTab");
      VBox userVbox = new VBox();
      refreshUserTab(userVbox);
      userTab.setOnSelectionChanged(new EventHandler<Event>() {
        public void handle(Event event) {
          if(userTab.isSelected()) {
            refreshUserTab(userVbox);
          }
        }
      });
      userTab.setContent(userVbox);
      tabPane.getTabs().add(userTab);
    }
    
    // Cameras Tab
    Tab cameraTab = new Tab();
    cameraTab.setId("cameraTab");
    cameraTab.setText("Cameras");
    VBox cameraVbox = new VBox();
    refreshCameraTab(cameraVbox);
    cameraTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        if(cameraTab.isSelected()) {
          refreshCameraTab(cameraVbox);
        }
      }
    });
    cameraTab.setContent(cameraVbox);
    tabPane.getTabs().add(cameraTab);

    // Thermostat Tab
    Tab thermostatTab = new Tab();
    thermostatTab.setId("thermostatTab");
    thermostatTab.setText("Thermostats");
    VBox thermostatVbox = new VBox();
    refreshThermostatTab(thermostatVbox);
    thermostatTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        if(thermostatTab.isSelected()) {
          refreshThermostatTab(thermostatVbox);
        }
      }
    });
    thermostatTab.setContent(thermostatVbox);
    tabPane.getTabs().add(thermostatTab);

    // Lightbulb Tab
    Tab lightbulbTab = new Tab();
    lightbulbTab.setText("Lightbulbs");
    VBox lightbulbVbox = new VBox();
    refreshLightbulbTab(lightbulbVbox);
    lightbulbTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        if(lightbulbTab.isSelected()) {
          refreshLightbulbTab(lightbulbVbox);
        }
      }
    });
    lightbulbTab.setContent(lightbulbVbox);
    tabPane.getTabs().add(lightbulbTab);

    // SmartPlug Tab
    Tab smartPlugTab = new Tab();
    smartPlugTab.setText("Smart Plugs");
    VBox smartPlugVbox = new VBox();
    refreshSmartPlugTab(smartPlugVbox);
    smartPlugTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        if(smartPlugTab.isSelected()) {
          refreshSmartPlugTab(smartPlugVbox);
        }
      }
    });
    smartPlugTab.setContent(smartPlugVbox);
    tabPane.getTabs().add(smartPlugTab);

    // Logout Tab
    Tab logoutTab = new Tab("logout");
    logoutTab.setId("logoutTab");
    //    logoutTab.setText("logout");
    logoutTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        logout();
      }
    });

    // puts Cameras + other Dev in system
    // PopulateSystem();

    tabPane.getTabs().add(logoutTab);
    mainPane.setCenter(tabPane);

    AlertsView alertsView = new AlertsView(organizer);
    mainPane.setBottom(alertsView.asParent());

    mainPane.prefHeightProperty().bind(scene.heightProperty());
    mainPane.prefWidthProperty().bind(scene.widthProperty());

    root.getChildren().add(mainPane);

  }

  private static void PopulateSystem() {
    new CameraView(organizer);
    new CameraView(organizer);
    int i = 0;
    while ( i < 0) {
      new CameraView(organizer);
      i ++;
    }
  }

  private static void closeClient() {
    organizer.shutdown();
    organizer.logString("Client Closed");
  }
  
  public static Organizer getOrganizer() {
    return organizer;
  }
  
  private static void refreshUserTab(VBox vbox) {
    vbox.getChildren().clear();
    for (User u : organizer.getUsers().values()) {
      vbox.getChildren().add(new UserView(u, organizer).asParent());
    }
  }

  private static void refreshCameraTab(VBox vbox) {
    vbox.getChildren().clear();
    for (Object d : currentUser.getDevices().values()) {
      if (d instanceof CameraView) {
        vbox.getChildren().add( ((CameraView) d).asParent() );
      }
    }
  }

  private static void refreshThermostatTab(VBox vbox) {
    vbox.getChildren().clear();
    for (Object d : currentUser.getDevices().values()) {
      if (d instanceof ThermostatView) {
        vbox.getChildren().add( ((ThermostatView) d).asParent() );
      }
    }
  }

  private static void refreshLightbulbTab(VBox vbox) {
    vbox.getChildren().clear();
    for (Object d : currentUser.getDevices().values()) {
      if (d instanceof LightbulbView) {
        vbox.getChildren().add( ((LightbulbView) d).asParent() );
      }
    }
  }

  private static void refreshSmartPlugTab(VBox vbox) {
    vbox.getChildren().clear();
    for (Object d : currentUser.getDevices().values()) {
      if (d instanceof SmartPlugView) {
        vbox.getChildren().add( ((SmartPlugView) d).asParent() );
      }
    }
  }
}