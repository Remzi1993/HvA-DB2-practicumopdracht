package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.models.Boekingsoverzicht;
import nl.hva.ict.models.Reiziger;
import nl.hva.ict.views.BoekingsoverzichtPerKlantView;
import nl.hva.ict.views.View;
import static nl.hva.ict.MainApplication.getBoekingsoverzichtDAO;
import static nl.hva.ict.MainApplication.getReizigerDAO;

/**
 * Boekingsoverzicht DAO
 * @author HvA FDMCI HBO-ICT
 */
public class BoekingsoverzichtPerKlantController extends Controller {
    private final BoekingsoverzichtPerKlantView view;
    private ObservableList<Reiziger> reizigers;
    private ObservableList<Boekingsoverzicht> boekingsoverzichtList;

    public BoekingsoverzichtPerKlantController() {
        view = new BoekingsoverzichtPerKlantView();
        reizigers = FXCollections.observableArrayList(getReizigerDAO().read());

        // Update de listview
        view.getComboBox().setItems(reizigers);

        // Luister naar wijzigingen in de combobox en ga naar de functie ListViewPerKlant() als er iets wijzigt.
        view.getComboBox().getSelectionModel().selectedItemProperty().addListener(
                event -> ListViewPerKlant()
        );
    }

    private void ListViewPerKlant() {
        getBoekingsoverzichtDAO().read();

        // Wat is geselecteerd?
        Reiziger selectedReiziger = view.getComboBox().getSelectionModel().getSelectedItem();

        // Haal data op van deze reiziger
        boekingsoverzichtList = FXCollections.observableArrayList(
                getBoekingsoverzichtDAO().getBoekingVoor(selectedReiziger.getReizigerCode())
        );

        // Zet in listview
        view.getBoekingsOverzichtListView().setItems(boekingsoverzichtList);
    }

    /**
     * Methode om de view door te geven zoals dat ook bij OOP2 ging
     * @return View
     */
    @Override
    public View getView() {
        return view;
    }
}