package nl.hva.ict;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nl.hva.ict.controllers.MainController;
import nl.hva.ict.data.MySQL.*;
import nl.hva.ict.data.MongoDB.*;

/**
 * Main class met applicatie logica
 * @author HvA FDMCI HBO-ICT
 */
public class MainApplication extends Application {
    private static final String TITLE = "HvA FDMCI HBO-ICT DB2 practicumopdracht";

    // MySQL
    private static final String MYSQL_HOST = "jdbc:mysql://localhost:3306/big_five_safari?autoReconnect=true&serverTimezone=Europe/Amsterdam&useSSL=False";
    private static final String MYSQL_USERNAME = "Remzi";
    private static final String MYSQL_PASSWORD = "remzi12345?";

    // MongoDB - HvA FDMCI Databases 2 practicumopdracht - week 5K - Remzi Cavdar
    private static final String MONGODB_HOST = "mongodb+srv://remzicavdar:SJAvgrHExKJ7PMXU@cluster0.3fdaolf.mongodb.net/";
    private static final String MONGODB_DATABASE = "big_five_safari";

    // Data models
    private static final MySQLReizigers MYSQL_REIZIGERS = new MySQLReizigers();
    private static final MySQLLodge MYSQL_LODGE = new MySQLLodge();
    private static final MySQLHotel MYSQL_HOTEL = new MySQLHotel();
    private static final MySQLAccommodatie MYSQL_ACCOMMODATIE = new MySQLAccommodatie();
    private static final MySQLBoekingsoverzicht MYSQL_BOEKINGSOVERZICHT = new MySQLBoekingsoverzicht();
    // HvA FDMCI Databases 2 practicumopdracht - week 5L - Remzi Cavdar
    private static final MongoDBReizigers MONGODB_REIZIGERS = new MongoDBReizigers();
    private static final MongoDBLandeninformatie MONGODB_LANDENINFORMATIE = new MongoDBLandeninformatie();

    // JavaFX
    private static Stage stage;
    private static MainController mainController;

    public static void switchController(Parent pane) {
        mainController.getBorderPane().setCenter(pane);
    }

    public static String getMysqlHost() {
        return MYSQL_HOST;
    }

    public static String getMysqlUsername() {
        return MYSQL_USERNAME;
    }

    public static String getMysqlPassword() {
        return MYSQL_PASSWORD;
    }

    public static String getMongodbHost() {
        return MONGODB_HOST;
    }

    public static String getMongodbDatabase() {
        return MONGODB_DATABASE;
    }

    public static String getTITLE() {
        return TITLE;
    }

    public static MySQLReizigers getMysqlReizigers() {
        return MYSQL_REIZIGERS;
    }

    public static MySQLLodge getMysqlLodge() {
        return MYSQL_LODGE;
    }

    public static MySQLHotel getMysqlHotel() {
        return MYSQL_HOTEL;
    }

    public static MySQLBoekingsoverzicht getMysqlBoekingsoverzicht() {
        return MYSQL_BOEKINGSOVERZICHT;
    }

    public static MySQLAccommodatie getMysqlAccommodatie() {
        return MYSQL_ACCOMMODATIE;
    }

    public static MongoDBReizigers getMongodbReizigers() {
        return MONGODB_REIZIGERS;
    }

    public static MongoDBLandeninformatie getMongoLandenInformatie() {
        return MONGODB_LANDENINFORMATIE;
    }

    /**
     * Opstarten JavaFX applicatie.
     * Kijk voor meer informatie over JavaFX ook bij OOP2
     * @param stage de stage welke gebruikt wordt
     */
    @Override
    public void start(Stage stage) {
        MainApplication.stage = stage;
        MainApplication.stage.setTitle(TITLE);
        int WIDTH = 800;
        MainApplication.stage.setWidth(WIDTH);
        int HEIGHT = 800;
        MainApplication.stage.setHeight(HEIGHT);
        mainController = new MainController();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        MainApplication.stage.setX((primaryScreenBounds.getWidth() - WIDTH) / 2f);
        MainApplication.stage.setY((primaryScreenBounds.getHeight() - HEIGHT) / 2f);

        MainApplication.stage.setMinWidth(750);
        MainApplication.stage.setMinHeight(600);
        MainApplication.stage.setScene(new Scene(mainController.getView().getRoot()));
        stage.show();
    }
}