package nl.hva.ict;

import javafx.application.Application;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Databases 2 practicumopdracht
 * @author HvA FDMCI HBO-ICT
 */
public class Main {
    public static void main(String[] args) {
        // shut down MongoDB logging message
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        // start application
        Application.launch(MainApplication.class, args);
    }
}