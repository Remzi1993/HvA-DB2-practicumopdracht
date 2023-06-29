package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.models.Reiziger;
import nl.hva.ict.views.ReizigerView;
import nl.hva.ict.views.View;
import static nl.hva.ict.MainApplication.getReizigerDAO;

/**
 * ReizigerController
 * @author HvA FDMCI HBO-ICT - Remzi Cavdar - remzi.cavdar@hva.nl
 */
public class ReizigerController extends Controller {
    private ReizigerView view;
    private ObservableList<Reiziger> reizigers;
    private Reiziger geselecteerdeReiziger;

    public ReizigerController() {
        view = new ReizigerView();
        /* Alle reizigers worden opgehaald uit de database en in een observable list gezet
         * HvA FDMCI Databases 2 practicumopdracht - week 5M
         */
        reizigers = FXCollections.observableArrayList(getReizigerDAO().read());
        // Alle reizigers worden in de listview en combobox gezet en daarbij worden alle reizigers getoond in de listview
        view.getReizigersListView().setItems(reizigers);
        view.getComboReistSamenMet().setItems(reizigers);

        // Luister naar wijzigingen in de listview en voer functie getInputsInView() uit als er een item wordt geselecteerd
        view.getReizigersListView().getSelectionModel().selectedItemProperty().addListener(
                e -> setInputsInView()
        );

        // Luister naar de knoppen en voer de bijbehorende functies uit
        view.getBtNew().setOnAction(e -> create());
        view.getBtSave().setOnAction(e -> update());
        view.getBtDelete().setOnAction(e -> delete());

        view.getBtUpdateData().setOnAction(e -> reloadData());
    }

    private void setInputsInView() {
        geselecteerdeReiziger = view.getReizigersListView().getSelectionModel().getSelectedItem();
        if(geselecteerdeReiziger == null) {
            return;
        }
        view.getTxtReizigersCode().setText(geselecteerdeReiziger.getReizigerCode());
        view.getTxtVoornaam().setText(geselecteerdeReiziger.getVoornaam());
        view.getTxtAchternaam().setText(geselecteerdeReiziger.getAchternaam());
        view.getTxtAdres().setText(geselecteerdeReiziger.getAdres());
        view.getTxtPostcode().setText(geselecteerdeReiziger.getPostcode());
        view.getTxtPlaats().setText(geselecteerdeReiziger.getPlaats());
        view.getTxtLand().setText(geselecteerdeReiziger.getLand());
        view.getComboReistSamenMet().getSelectionModel().select(geselecteerdeReiziger.getHoofdreiziger());
    }

    private void clearInputsInView() {
        geselecteerdeReiziger = null;
        view.getTxtReizigersCode().clear();
        view.getTxtVoornaam().clear();
        view.getTxtAchternaam().clear();
        view.getTxtAdres().clear();
        view.getTxtPostcode().clear();
        view.getTxtPlaats().clear();
        view.getTxtLand().clear();
        view.getComboReistSamenMet().getSelectionModel().clearSelection();
        view.getReizigersListView().getSelectionModel().clearSelection();
    }

    private void reloadData() {
        clearInputsInView();
        reizigers.setAll(getReizigerDAO().read());
    }

    @Override
    public View getView() {
        return view;
    }

    // Voeg record toe
    private void create() {
        if(geselecteerdeReiziger != null) {
            return;
        }

        Reiziger reiziger;
        if (view.getComboReistSamenMet().getSelectionModel().getSelectedItem() == null) {
            reiziger = new Reiziger(
                    view.getTxtAchternaam().getText() + view.getTxtVoornaam().getText().charAt(0),
                    view.getTxtVoornaam().getText(),
                    view.getTxtAchternaam().getText(),
                    view.getTxtAdres().getText(),
                    view.getTxtPostcode().getText(),
                    view.getTxtPlaats().getText(),
                    view.getTxtLand().getText()
            );
        } else {
            reiziger = new Reiziger(
                    view.getTxtAchternaam().getText() + view.getTxtVoornaam().getText().charAt(0),
                    view.getTxtVoornaam().getText(),
                    view.getTxtAchternaam().getText(),
                    view.getTxtAdres().getText(),
                    view.getTxtPostcode().getText(),
                    view.getTxtPlaats().getText(),
                    view.getTxtLand().getText(),
                    view.getComboReistSamenMet().getSelectionModel().getSelectedItem()
            );
        }

        // Voeg record toe aan database en reload de data
        if(getReizigerDAO().create(reiziger)) {
            reloadData();
        }
    }

    // Update record
    private void update() {
        // bewaar (update) record
    }

    // Verwijder record
    private void delete() {
        // delete dit record
    }
}