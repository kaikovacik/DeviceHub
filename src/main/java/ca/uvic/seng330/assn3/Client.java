package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.Admin;
import ca.uvic.seng330.assn3.AlertsView;
import ca.uvic.seng330.assn3.HubRegistrationException;
import ca.uvic.seng330.assn3.Organizer;
import ca.uvic.seng330.assn3.User;
import ca.uvic.seng330.assn3.devices.CameraView;
import ca.uvic.seng330.assn3.devices.DeviceView;
import ca.uvic.seng330.assn3.devices.LightbulbView;
import ca.uvic.seng330.assn3.devices.SmartPlugView;
import ca.uvic.seng330.assn3.devices.ThermostatView;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
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
  private static ConfigureView configureView;
  private static AlertsView alertsView;
  private static LogView prevActivitiesView;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Scene scene = createScene();

    // LOAD STYLE SHEET                                                                           // COMMENT THIS OUT IF NEEDED
    scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    // LOAD STYLE SHEET                                                                           // COMMENT THIS OUT IF NEEDED

    primaryStage.setScene(scene);
    primaryStage.setAlwaysOnTop(false);
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

    // Register predefined users
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
    root.setStyle(
        " -fx-padding: 10; " +
            " -fx-border-color: black; " +
            " -fx-border-radius: 5; " +
            " -fx-box-shadow: 10px; " +
            " -fx-background-color: lightgrey; " +
            " -fx-background-radius: 5; "
        );
    root.setAutoSizeChildren(true);

//    BorderPane mainPane = new BorderPane();
    LoginView loginView = (initialMessage == null)? new LoginView(organizer) : new LoginView(initialMessage, organizer);
    
    BorderPane mainPane = new BorderPane();
    mainPane.prefHeightProperty().bind(scene.heightProperty());
    mainPane.prefWidthProperty().bind(scene.widthProperty());
    mainPane.setCenter(loginView.asParent());

    root.getChildren().add(mainPane);
  }

  public static void addUserLayout() {
    root.getChildren().clear(); 
    
    AddUserView addUserView = new AddUserView(organizer);
    
    BorderPane mainPane = new BorderPane();
    mainPane.prefHeightProperty().bind(scene.heightProperty());
    mainPane.prefWidthProperty().bind(scene.widthProperty());
    mainPane.setCenter(addUserView.asParent());

    root.getChildren().add(mainPane);
  }

  public static void login(String username, String password) throws UnknownUserException, IncorrectPasswordException {
    if (!organizer.getUsers().containsKey(username)) throw new UnknownUserException("Unknown user!");    
    if (!organizer.getUsers().get(username).checkPassword(password)) throw new IncorrectPasswordException("Incorrect password!");

    root.getChildren().clear();

    currentUser = organizer.getUsers().get(username);

    // Print login info to console
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

    configureView = new ConfigureView(organizer); //instantiated outside of if because PopulateSystem needs to acces it.
    // Only show configure tab when user is an admin
    if (currentUser instanceof Admin) {

      // Configure Tab
      prevActivitiesView = new LogView(organizer);
      VBox configVbox = new VBox();
      configVbox.setStyle("-fx-background-color: linear-gradient(skyblue, lightgrey);");
      Tab configTab = new Tab();
      configTab.setId("configTab"); // id for ui tests
      configTab.setText("Device Configuration");
      configVbox.getChildren().add(configureView.asParent() );
      configVbox.getChildren().add(prevActivitiesView.asParent() );
      configTab.setContent(configVbox);
      tabPane.getTabs().add(configTab);

      // User Tab
      Tab userTab = new Tab("Users");
      userTab.setId("userTab"); // id for ui tests
      VBox userVbox = new VBox();
      refreshUserTab(userVbox);
      userTab.setOnSelectionChanged(new EventHandler<Event>() {
        public void handle(Event event) {
          if(userTab.isSelected()) {
            refreshUserTab(userVbox);
          }
        }
      });
      ScrollPane userScroll = new ScrollPane(userVbox);
      userScroll.setId("userScroll"); // id for ui tests
      userScroll.setFitToHeight(true);
      userScroll.setFitToWidth(true);
      userTab.setContent(userScroll);
      tabPane.getTabs().add(userTab);
    }

    // Cameras Tab
    Tab cameraTab = new Tab();
    cameraTab.setId("cameraTab"); // id for ui tests
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
    ScrollPane cameraScroll = new ScrollPane(cameraVbox);
    cameraScroll.setId("cameraScroll"); // id for ui tests
    cameraScroll.setFitToHeight(true);
    cameraScroll.setFitToWidth(true);
    cameraTab.setContent(cameraScroll);
    tabPane.getTabs().add(cameraTab);

    // Thermostat Tab
    Tab thermostatTab = new Tab();
    thermostatTab.setId("thermostatTab"); // id for ui tests
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
    ScrollPane thermostatScroll = new ScrollPane(thermostatVbox);
    thermostatScroll.setId("thermostatScroll"); // id for ui tests
    thermostatScroll.setFitToHeight(true);
    thermostatScroll.setFitToWidth(true);
    thermostatTab.setContent(thermostatScroll);
    tabPane.getTabs().add(thermostatTab);

    // Lightbulb Tab
    Tab lightbulbTab = new Tab();
    lightbulbTab.setId("lightbulbTab"); // id for ui tests
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
    ScrollPane lightbulbScroll = new ScrollPane(lightbulbVbox);
    lightbulbScroll.setId("lightbulbScroll"); // id for ui tests
    lightbulbScroll.setFitToHeight(true);
    lightbulbScroll.setFitToWidth(true);
    lightbulbTab.setContent(lightbulbScroll);
    tabPane.getTabs().add(lightbulbTab);

    // SmartPlug Tab
    Tab smartPlugTab = new Tab();
    smartPlugTab.setId("smartPlugTab"); // id for ui tests
    smartPlugTab.setText("Smart Plugs"); // id for ui tests
    VBox smartPlugVbox = new VBox();
    refreshSmartPlugTab(smartPlugVbox);
    smartPlugTab.setOnSelectionChanged(new EventHandler<Event>() {
      public void handle(Event event) {
        if(smartPlugTab.isSelected()) {
          refreshSmartPlugTab(smartPlugVbox);
        }
      }
    });
    ScrollPane smartPlugScroll = new ScrollPane(smartPlugVbox);
    smartPlugScroll.setId("smartPlugScroll"); // id for ui tests
    smartPlugScroll.setFitToHeight(true);
    smartPlugScroll.setFitToWidth(true);
    smartPlugTab.setContent(smartPlugScroll);
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
    //     PopulateSystem();

    tabPane.getTabs().add(logoutTab);
    mainPane.setCenter(tabPane);

    alertsView = new AlertsView(organizer);
    mainPane.setBottom(alertsView.asParent());

    mainPane.prefHeightProperty().bind(scene.heightProperty());
    mainPane.prefWidthProperty().bind(scene.widthProperty());

    root.getChildren().add(mainPane);
  }

  private static void PopulateSystem() {
    int i = 0;
    while ( i < 0) {
      configureView.addToDeviceMenu(new CameraView(organizer));
      i ++;
    }

    configureView.addToDeviceMenu(new CameraView(organizer));
    configureView.addToDeviceMenu(new CameraView(organizer));
    configureView.addToDeviceMenu(new ThermostatView(organizer));
    configureView.addToDeviceMenu(new SmartPlugView(organizer));
    configureView.addToDeviceMenu(new LightbulbView(organizer));
  }

  private static void closeClient() {
    organizer.shutdown();
    for ( DeviceView d : organizer.getViews().values()) {
      try {
        organizer.unregister(d.getModel().getIdentifier());
      } catch (HubRegistrationException e) {
        e.printStackTrace();
      }
    }
    organizer.logString("All Devices Removed");
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