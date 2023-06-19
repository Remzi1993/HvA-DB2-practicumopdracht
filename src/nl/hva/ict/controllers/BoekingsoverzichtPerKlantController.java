package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.MainApplication;
import nl.hva.ict.models.Boekingsoverzicht;
import nl.hva.ict.models.Reiziger;
import nl.hva.ict.views.BoekingsoverzichtPerKlantView;
import nl.hva.ict.views.View;

/**
 * Boekingsoverzicht DAO
 * @author HvA FDMCI HBO-ICT
 */
public class BoekingsoverzichtPerKlantController extends Controller {
    private final BoekingsoverzichtPerKlantView boekingsoverzichtPerKlantView;

    public BoekingsoverzichtPerKlantController() {
        // Maak instance van view
        boekingsoverzichtPerKlantView = new BoekingsoverzichtPerKlantView();

        // luister naar wijzigingen in de combobox en ga naar de functie ListViewPerKlant() als er iets wijzigt.
        boekingsoverzichtPerKlantView.getComboBox().getSelectionModel().selectedItemProperty().addListener(
                event -> ListViewPerKlant()
        );

        // Maak verbinding met de DAO, haal arrayList op met alle boekingen en stop dit in een observable list
        ObservableList<Reiziger> reizigers = FXCollections.observableArrayList(
                MainApplication.getMysqlReizigers().getAll());

        // Update de listview
        boekingsoverzichtPerKlantView.getComboBox().setItems(reizigers);
    }

    private void ListViewPerKlant() {
        // Wat is geselecteerd?
        Reiziger selectedReiziger = (Reiziger) boekingsoverzichtPerKlantView.getComboBox().getSelectionModel().getSelectedItem();

        // Haal data op van deze reiziger
        ObservableList<Boekingsoverzicht> boekingsoverzichtList = FXCollections.observableArrayList(
                MainApplication.getMysqlBoekingsoverzicht().getBoekingVoor(selectedReiziger.getReizigersCode()));

        // Zet in listview
        boekingsoverzichtPerKlantView.getBoekingsOverzichtListView().setItems(boekingsoverzichtList);
    }

    /**
     * Methode om de view door te geven zoals dat ook bij OOP2 ging
     * @return View
     */
    @Override
    public View getView() {
        return boekingsoverzichtPerKlantView;
    }
}