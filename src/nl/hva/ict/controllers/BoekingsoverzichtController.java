package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.models.Boekingsoverzicht;
import nl.hva.ict.views.BoekingsoverzichtView;
import nl.hva.ict.views.View;
import static nl.hva.ict.MainApplication.getBoekingsoverzichtDAO;

/**
 * Controller voor het boekingsoverzicht
 * @author HvA FDMCI HBO-ICT
 */
public class BoekingsoverzichtController extends Controller {
    private final BoekingsoverzichtView view;
    private ObservableList<Boekingsoverzicht> boekingsoverzichtList;

    public BoekingsoverzichtController() {
        boekingsoverzichtList = FXCollections.observableArrayList(getBoekingsoverzichtDAO().read());
        view = new BoekingsoverzichtView();
        view.getBoekingsOverzichtListView().setItems(boekingsoverzichtList);
    }

    @Override
    public View getView() {
        return view;
    }
}