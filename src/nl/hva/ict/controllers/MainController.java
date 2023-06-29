package nl.hva.ict.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import nl.hva.ict.MainApplication;
import nl.hva.ict.views.MainView;
import nl.hva.ict.views.View;
import java.util.Optional;

/**
 * Main controller zorgt dat alle onderliggende schermen worden aangemaakt en de juiste controller wordt aangesproken
 * @author HvA FDMCI HBO-ICT
 */
public class MainController extends Controller {
    private final MainView mainView;
    private final ReizigerController reizigerController;
    private final HotelController hotelController;
    private final LodgeController lodgeController;
    private final AboutController aboutController;
    private final BoekingsoverzichtController boekingsOverzichtController;
    private final BoekingsoverzichtPerKlantController boekingsoverzichtPerKlantController;
    private final BoekingsGeboektOpController boekingsGeboektOpController;
    private final LandController landController;

    public MainController() {
        mainView = new MainView();

        // Maak controllers aan
        reizigerController = new ReizigerController();
        hotelController = new HotelController();
        lodgeController = new LodgeController();
        aboutController = new AboutController();
        boekingsOverzichtController = new BoekingsoverzichtController();
        boekingsoverzichtPerKlantController = new BoekingsoverzichtPerKlantController();
        boekingsGeboektOpController = new BoekingsGeboektOpController();
        landController = new LandController();

        // Default header message
        mainView.getMessage().setText("Home");

        mainView.getStartschermMenuItem().setOnAction(event -> {
            MainApplication.switchController(mainView.createRoot());
            mainView.getMessage().setText("Home");
        });

        mainView.getCloseMenuItem().setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Afsluiten?");
            alert.setHeaderText("Klik op OK als u de applicatie wilt afsluiten");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        // Acties als je op het menu drukt
        mainView.getHotelMenuItem().setOnAction(event -> {
            MainApplication.switchController(hotelController.getView().getRoot());
            mainView.getMessage().setText("Hotels");
        });

        mainView.getLodgeMenuItem().setOnAction(event -> {
            MainApplication.switchController(lodgeController.getView().getRoot());
            mainView.getMessage().setText("Lodges");

        });

        mainView.getReizigersMenuItem().setOnAction(event -> {
            MainApplication.switchController(reizigerController.getView().getRoot());
            mainView.getMessage().setText("Reizigers");
        });

        mainView.getOverzichtAlleBoekingen().setOnAction(event -> {
            // laad hier de controller
            MainApplication.switchController(boekingsOverzichtController.getView().getRoot());
            mainView.getMessage().setText("Boekingsoverzicht");
        });

        mainView.getOverzichtPerKlant().setOnAction(event -> {
            MainApplication.switchController(boekingsoverzichtPerKlantController.getView().getRoot());
            mainView.getMessage().setText("Boekingsoverzicht per klant");
        });

        mainView.getOverzichtGeboektOp().setOnAction(event -> {
            MainApplication.switchController(boekingsGeboektOpController.getView().getRoot());
            mainView.getMessage().setText("Boekingsoverzicht op datum");
        });

        mainView.getAboutMenuItem().setOnAction(event -> {
            MainApplication.switchController(aboutController.getView().getRoot());
            mainView.getMessage().setText("Over deze applicatie");
        });

        mainView.getInformatieMenu().setOnAction(event -> {
            MainApplication.switchController(landController.getView().getRoot());
            mainView.getMessage().setText("Landen informatie");
        });
    }

    /**
     * Haal de borderpane op
     * @return borderpane
     */
    public BorderPane getBorderPane() {
        return mainView.getBorderPane();
    }

    /**
     * Methode om de view door te geven zoals dat ook bij OOP2 ging
     * @return View
     */
    @Override
    public View getView() {
        return mainView;
    }
}