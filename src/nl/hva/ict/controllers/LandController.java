package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import nl.hva.ict.data.LandDAO;
import nl.hva.ict.views.LandView;
import nl.hva.ict.views.View;
import static nl.hva.ict.MainApplication.getLandDAO;

/**
 * Controller om de landenpagina te bedienen
 * @author HvA FDMCI HBO-ICT
 */
public class LandController extends Controller {
    private final LandView view;
    private final String[] vragen = {
            "In welke landen spreken ze Engels ?", "In welke landen spreken ze Frans ?",
            "In welke landen kan je met euro's betalen ?", "Welke landen liggen er in West-Europa ?",
            "Welke landen liggen er in Afrika ?", "Hoeveel inwoners zijn er in Oost Afrika?"
    };
    private LandDAO landDAO;

    /**
     * Voeg listeners toe om om te reageren op wat de gebruiker doet
     */
    public LandController() {
        landDAO = getLandDAO();

        view = new LandView();
        // Voeg de vragen aan de combobox toe
        view.getComboBox().setItems(FXCollections.observableArrayList(this.vragen));
        // luister naar wijzigingen in de combobox
        view.getComboBox().getSelectionModel().selectedItemProperty().addListener(event -> BeantwoordVraag());
        // luister naar de checkbox en reageer als die wordt aan of uitgezet.
        view.getCheckBox().selectedProperty().addListener(event -> BeantwoordVraag());
    }

    /**
     * Methode om de juiste data te raadplegen. Het antwoord wordt in de arraylist gezet van de DAO en op het einde gekoppeld aan de listview
     */
    private void BeantwoordVraag() {
        /* Even checken of de combobox wel geselecteerd is. Misschien heb je wel alleen de "Alleen uit Afrika"
         * checkbox aangeklikt zonder een selectie te maken in de combobox. Dit geeft anders een null pointer error.
         */
//        if (view.getComboBox().getSelectionModel().getSelectedItem() != null) {
//            // Lees de velden.
//            String selectedItem = view.getComboBox().getSelectionModel().getSelectedItem().toString();
//            boolean alleenAfrika = view.getCheckBox().isSelected();
//
//            // Selecteer de juiste methode bij de juiste vraag uit de combobox
//            if (selectedItem == vragen[0]) {
//                MainApplication.getMongoLandenInformatie().wieSpreekt("English", alleenAfrika);
//            } else if (selectedItem == vragen[1]) {
//                MainApplication.getMongoLandenInformatie().wieSpreekt("French", alleenAfrika);
//            } else if (selectedItem == vragen[2]) {
//                MainApplication.getMongoLandenInformatie().waarBetaalJeMet("Euro", alleenAfrika);
//            } else if (selectedItem == vragen[3]) {
//                MainApplication.getMongoLandenInformatie().welkeLandenZijnErIn("Western Europe");
//            } else if (selectedItem == vragen[4]) {
//                MainApplication.getMongoLandenInformatie().welkeLandenZijnErIn("Africa");
//            } else if (selectedItem == vragen[5]) {
//                int totaalInwoners = MainApplication.getMongoLandenInformatie().hoeveelInwonersOostAfrika();
//                view.getTextArea().setText("Er wonen in Oost- Afrika in totaal " + totaalInwoners + " inwoners");
//            }
//
//            // Haal de actuele data op
//            ObservableList<Land> land = FXCollections.observableArrayList(landDAO.getAll());
//
//            // stop dit in de lijstView
//            view.getLandenInformatieListView().setItems(land);
//        }
    }

    /**
     * Methode om de view door te geven zoals dat ook bij OOP2 ging
     *
     * @return View
     */
    @Override
    public View getView() {
        return view;
    }
}