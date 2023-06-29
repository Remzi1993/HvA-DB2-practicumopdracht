package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.data.AccommodatieDAO;
import nl.hva.ict.data.BoekingsoverzichtDAO;
import nl.hva.ict.models.Accommodatie;
import nl.hva.ict.models.Boekingsoverzicht;
import nl.hva.ict.models.Reiziger;
import nl.hva.ict.views.GeboektOpView;
import nl.hva.ict.views.View;
import java.time.LocalDate;
import static nl.hva.ict.MainApplication.getAccommodatieDAO;
import static nl.hva.ict.MainApplication.getBoekingsoverzichtDAO;

/**
 * Controller voor de pagina geboekt.
 * @author HvA FDMCI HBO-ICT
 */
public class BoekingsGeboektOpController extends Controller {
    private final GeboektOpView view;
    private BoekingsoverzichtDAO boekingsoverzichtDAO;
    private ObservableList<Boekingsoverzicht> boekingsoverzichtList;
    private AccommodatieDAO accommodatieDAO;
    private ObservableList<Accommodatie> accommodaties;

    public BoekingsGeboektOpController() {
        view = new GeboektOpView();

        // BoekingsoverzichtDAO
        boekingsoverzichtDAO = getBoekingsoverzichtDAO();
        boekingsoverzichtDAO.read();
        boekingsoverzichtList = FXCollections.observableArrayList(boekingsoverzichtDAO.read());

        // AccommodatieDAO
        accommodatieDAO = getAccommodatieDAO();
        accommodatieDAO.read();
        accommodaties = FXCollections.observableArrayList(accommodatieDAO.read());

        //voeg alles toe aan de listview
        view.getComboBoxAccommodaties().setItems(accommodaties);

        // luister naar wijzigingen in de combobox en ga naar de methode ListAccommodaties() als er iets veranderd.
        view.getComboBoxAccommodaties().getSelectionModel().selectedItemProperty().addListener(
                event -> ListAccommodaties()
        );

        // luister naar wijzigingen in de datepicker en ga naar de methode ListAccommodaties() als er iets veranderd.
        view.getDatePicker().valueProperty().addListener(event -> ListAccommodaties());
    }

    /**
     * Welke accommodatie is er geboekt op welke datum?
     */
    private void ListAccommodaties() {
        // welke accommodate is geselecteerd in de combobox?
        if (view.getComboBoxAccommodaties().getSelectionModel().getSelectedItem() != null) {
            Accommodatie accommodatieSelected = view.getComboBoxAccommodaties().getSelectionModel().getSelectedItem();

            // Haal de AccommodatieCode op van het geselecteerde model
            String accommodatieCode = accommodatieSelected.getAccommodatieCode();

            // Welke datum is in de view geselecteerd?
            LocalDate datum = view.getDatePicker().getValue();

            // haal de info op uit de DAO
            ObservableList<Reiziger> reizigers = FXCollections.observableArrayList(
                    boekingsoverzichtDAO.GeboektOp(accommodatieCode, datum)
            );

            // Zet de data het in de listview
            view.getBoekingsOverzichtListView().setItems(reizigers);
        }
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