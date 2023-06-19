package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.MainApplication;
import nl.hva.ict.models.Boekingsoverzicht;
import nl.hva.ict.views.BoekingsoverzichtView;
import nl.hva.ict.views.View;

/**
 * Controller voor het boekingsoverzicht
 * @author HvA FDMCI HBO-ICT
 */
public class BoekingsoverzichtController extends Controller {
    private final BoekingsoverzichtView boekingsoverzichtView;

    public BoekingsoverzichtController() {
        // Maak instance van scherm
        boekingsoverzichtView = new BoekingsoverzichtView();

        // Maak verbinding met de DAO, haal arrayList op met alle boekingen en stop dit in een observable list
        ObservableList<Boekingsoverzicht> observableBoeking = FXCollections.observableList(
                MainApplication.getMysqlBoekingsoverzicht().getAll()
        );
        // Zet alle informatie in de listview
        boekingsoverzichtView.getBoekingsOverzichtListView().setItems(observableBoeking);
    }

    /**
     * Methode om de view door te geven zoals dat ook bij OOP2 ging
     * @return View
     */
    @Override
    public View getView() {
        return boekingsoverzichtView;
    }
}