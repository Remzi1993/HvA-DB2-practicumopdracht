package nl.hva.ict;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nl.hva.ict.controllers.MainController;
import nl.hva.ict.data.*;
import nl.hva.ict.data.MongoDB.MongoDBLandDAO;
import nl.hva.ict.data.MongoDB.MongoDBReizigerDAO;
import nl.hva.ict.data.MySQL.*;

/**
 * Main class met applicatie logica
 * @author HvA FDMCI HBO-ICT
 */
public class MainApplication extends Application {
    private static final String TITLE = "HvA FDMCI HBO-ICT DB2 practicumopdracht";

    // MySQL
    private static final String MYSQL_HOST = "jdbc:mysql://localhost:3306/big_five_safari?autoReconnect=true&serverTimezone=Europe/Amsterdam&useSSL=False";
    private static final String MYSQL_USERNAME = "Testadmin";
    private static final String MYSQL_PASSWORD = "DitIsEenDummyTestAccount";

    // MongoDB - HvA FDMCI Databases 2 practicumopdracht - week 5K - Remzi Cavdar
    private static final String MONGODB_HOST = "mongodb+srv://remzicavdar:SJAvgrHExKJ7PMXU@cluster0.3fdaolf.mongodb.net/";
    private static final String MONGODB_DATABASE = "big_five_safari";

    // DAO's - Om te switchen naar een andere DAO, pas je hieronder de classen aan:
    private static AccommodatieDAO accommodatieDAO = new MySQLAccommodatieDAO();
    private static BoekingsoverzichtDAO boekingsoverzichtDAO = new MySQLBoekingsoverzichtDAO();
    private static HotelDAO hotelDAO = new MySQLHotelDAO();
    private static LandDAO landDAO = new MongoDBLandDAO();
    private static LodgeDAO lodgeDAO = new MySQLLodgeDAO();
    private static ReizigerDAO reizigerDAO = new MongoDBReizigerDAO();

    // JavaFX
    private static Stage stage;
    private static MainController mainController;

    /**
     * Opstarten JavaFX applicatie.
     * Kijk voor meer informatie over JavaFX ook bij OOP2
     * @param stage de stage welke gebruikt wordt
     */
    @Override
    public void start(Stage stage) {
        MainApplication.stage = stage;
        int WIDTH = 800;
        int HEIGHT = 800;
        stage.setTitle(TITLE);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        mainController = new MainController();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primaryScreenBounds.getWidth() - WIDTH) / 2f);
        stage.setY((primaryScreenBounds.getHeight() - HEIGHT) / 2f);
        stage.setMinWidth(750);
        stage.setMinHeight(600);
        stage.setScene(new Scene(mainController.getView().getRoot()));
        stage.show();
    }

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

    public static AccommodatieDAO getAccommodatieDAO() {
        return accommodatieDAO;
    }

    public static BoekingsoverzichtDAO getBoekingsoverzichtDAO() {
        return boekingsoverzichtDAO;
    }

    public static HotelDAO getHotelDAO() {
        return hotelDAO;
    }

    public static LandDAO getLandDAO() {
        return landDAO;
    }

    public static LodgeDAO getLodgeDAO() {
        return lodgeDAO;
    }

    public static ReizigerDAO getReizigerDAO() {
        return reizigerDAO;
    }
}