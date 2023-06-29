package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.data.LodgeDAO;
import nl.hva.ict.models.Lodge;
import nl.hva.ict.views.LodgeView;
import nl.hva.ict.views.View;
import static nl.hva.ict.MainApplication.getLodgeDAO;

/**
 * Controller voor de Lodge view
 * @author HvA FDMCI HBO-ICT
 */
public class LodgeController extends Controller {
    private final LodgeView view;
    private LodgeDAO lodgeDAO;
    ObservableList<Lodge> lodges;

    public LodgeController() {
        lodgeDAO = getLodgeDAO();
        // Alle lodges worden opgehaald uit de database
        lodgeDAO.read();
        // Alle lodges worden in een observable list gezet en getoond op de lodge overzicht
        lodges = FXCollections.observableArrayList(lodgeDAO.read());
        view = new LodgeView();

        // Luister naar wijzigingen in de listview en ga naar de functie getItemsInFields() als er een item wordt geselecteerd
        view.getLodgeViewListView().getSelectionModel().selectedItemProperty().addListener(
                e -> getItemsInFields()
        );

        view.getLodgeViewListView().setItems(lodges);

        // Set wat acties als op de buttons wordt geklikt
        view.getBtSave().setOnAction(e -> save());
        view.getBtUpdateData().setOnAction(e -> refreshData());
        view.getBtNew().setOnAction(e -> insert());
        view.getBtDelete().setOnAction(e -> delete());
    }

    // Opnieuw laden van de data
    private void refreshData() {
        lodges.setAll(lodgeDAO.read());
    }

    /**
     * Set alle velden als er een object in de Listview is aangeklikt
     */
    private void getItemsInFields() {
        Lodge currentLodge = view.getLodgeViewListView().getSelectionModel().getSelectedItem();
        if(currentLodge == null) {
            return;
        }
        view.getTxtAccommodatieCode().setText((currentLodge.getAccommodatieCode()));
        view.getTxtNaam().setText(currentLodge.getNaam());
        view.getTxtStad().setText(currentLodge.getStad());
        view.getTxtLand().setText(currentLodge.getLand());
        view.getTxtKamertype().setText(currentLodge.getKamer());
        view.getTxtAantalPersonen().setText((String.valueOf(currentLodge.getPersonen())));
        view.getTxtPrijsPerWeek().setText(String.valueOf(currentLodge.getPrijsPerWeek()));
        view.getCheckAutohuur().setSelected(currentLodge.isAutoHuren());
    }

    /**
     * Methode om de view door te geven zoals dat ook bij OOP2 ging
     * @return View
     */
    @Override
    public View getView() {
        return view;
    }

    /**
     * Bewaar een lodge
     */
    private void save() {
        // bewaar (update) record
    }

    /**
     * Verwijder een lodge
     */
    private void delete() {
        // delete dit record
    }

    /**
     * Maak alle velden lees
     */
    private void insert() {
        //Voeg toe
    }
}