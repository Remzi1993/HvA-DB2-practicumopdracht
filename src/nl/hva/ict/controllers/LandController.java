package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.models.Land;
import nl.hva.ict.views.LandView;
import nl.hva.ict.views.View;
import static nl.hva.ict.MainApplication.getLandDAO;

/**
 * Controller om de landenpagina te bedienen
 * @author HvA FDMCI HBO-ICT
 */
public class LandController extends Controller {
    private final LandView view;
    ObservableList<Land> landen;
    private final String[] vragen = {
            "In welke landen spreken ze Engels ?", "In welke landen spreken ze Frans ?",
            "In welke landen kan je met euro's betalen ?", "Welke landen liggen er in West-Europa ?",
            "Welke landen liggen er in Afrika ?", "Hoeveel inwoners zijn er in Oost Afrika?"
    };

    /**
     * Voeg listeners toe om om te reageren op wat de gebruiker doet
     */
    public LandController() {
        view = new LandView();
        // Voeg de vragen aan de combobox toe
        view.getComboBox().setItems(FXCollections.observableArrayList(vragen));
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
        if (view.getComboBox().getSelectionModel().getSelectedItem() != null) {
            // Lees de velden.
            String selectedItem = view.getComboBox().getSelectionModel().getSelectedItem();
            boolean alleenAfrika = view.getCheckBox().isSelected();

            // Selecteer de juiste methode bij de juiste vraag uit de combobox
            if (selectedItem.equals(vragen[0])) {
                getLandDAO().wieSpreekt("English", alleenAfrika);
            } else if (selectedItem.equals(vragen[1])) {
                getLandDAO().wieSpreekt("French", alleenAfrika);
            } else if (selectedItem.equals(vragen[2])) {
                getLandDAO().waarBetaalJeMet("Euro", alleenAfrika);
            } else if (selectedItem.equals(vragen[3])) {
                getLandDAO().welkeLandenZijnErIn("Western Europe");
            } else if (selectedItem.equals(vragen[4])) {
                getLandDAO().welkeLandenZijnErIn("Africa");
            } else if (selectedItem.equals(vragen[5])) {
                int totaalInwoners = getLandDAO().hoeveelInwonersOostAfrika();
                view.getTextArea().setText("Er wonen in Oost- Afrika in totaal " + totaalInwoners + " inwoners");
            }

            // Haal de actuele data op
            landen = FXCollections.observableArrayList(getLandDAO().read());

            // stop dit in de lijstView
            view.getLandenInformatieListView().setItems(landen);
        }
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