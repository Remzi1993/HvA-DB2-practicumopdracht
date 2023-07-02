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

        // Luister naar wijzigingen in de listview
        view.getReizigersListView().getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oudeReiziger, nieuweReiziger) -> {
                    if (nieuweReiziger == null) {
                        view.getTxtReizigersCode().clear();
                        view.getTxtVoornaam().clear();
                        view.getTxtAchternaam().clear();
                        view.getTxtAdres().clear();
                        view.getTxtPostcode().clear();
                        view.getTxtPlaats().clear();
                        view.getTxtLand().clear();
                        view.getComboReistSamenMet().getSelectionModel().clearSelection();
                        view.getComboReistSamenMet().getSelectionModel().select(null);
                    } else {
                        view.getTxtReizigersCode().setText(nieuweReiziger.getReizigerCode());
                        view.getTxtVoornaam().setText(nieuweReiziger.getVoornaam());
                        view.getTxtAchternaam().setText(nieuweReiziger.getAchternaam());
                        view.getTxtAdres().setText(nieuweReiziger.getAdres());
                        view.getTxtPostcode().setText(nieuweReiziger.getPostcode());
                        view.getTxtPlaats().setText(nieuweReiziger.getPlaats());
                        view.getTxtLand().setText(nieuweReiziger.getLand());
                        view.getComboReistSamenMet().getSelectionModel().clearSelection();
                        view.getComboReistSamenMet().getSelectionModel().select(null);

                        if (nieuweReiziger.getHoofdreiziger() != null) {
                            view.getComboReistSamenMet().getSelectionModel().select(nieuweReiziger.getHoofdreiziger());
                        }
                    }
                }
        );

        view.getBtUpdateData().setOnAction(e -> reloadData());
        // Luister naar de knoppen en voer de bijbehorende functies uit
        view.getBtSave().setOnAction(e -> createOrUpdate());
        view.getBtDelete().setOnAction(e -> delete());
    }

    private void reloadData() {
        view.getReizigersListView().getSelectionModel().clearSelection();
        reizigers.setAll(getReizigerDAO().read());
    }

    @Override
    public View getView() {
        return view;
    }

    // Voeg record toe (create) of pas deze aan (update)
    private void createOrUpdate() {
        geselecteerdeReiziger = view.getReizigersListView().getSelectionModel().getSelectedItem();
        String oudeReizigerCode = geselecteerdeReiziger.getReizigerCode();
        Reiziger reiziger;

        if (view.getComboReistSamenMet().getSelectionModel().getSelectedItem() == null) {
            reiziger = new Reiziger(
                    view.getTxtReizigersCode().getText(),
                    view.getTxtVoornaam().getText(),
                    view.getTxtAchternaam().getText(),
                    view.getTxtAdres().getText(),
                    view.getTxtPostcode().getText(),
                    view.getTxtPlaats().getText(),
                    view.getTxtLand().getText(),
                    null
            );
        } else {
            reiziger = new Reiziger(
                    view.getTxtReizigersCode().getText(),
                    view.getTxtVoornaam().getText(),
                    view.getTxtAchternaam().getText(),
                    view.getTxtAdres().getText(),
                    view.getTxtPostcode().getText(),
                    view.getTxtPlaats().getText(),
                    view.getTxtLand().getText(),
                    view.getComboReistSamenMet().getSelectionModel().getSelectedItem()
            );
        }

        if (geselecteerdeReiziger == null) {
            // Voeg record toe aan database en reload de data
            if (getReizigerDAO().create(reiziger)) {
                reloadData();
            }
        } else {
            // Voeg record toe aan database en reload de data
            if (getReizigerDAO().update(reiziger, oudeReizigerCode)) {
                reloadData();
            }
        }
    }

    // Verwijder record (delete) uit de database
    private void delete() {
        geselecteerdeReiziger = view.getReizigersListView().getSelectionModel().getSelectedItem();
        if (geselecteerdeReiziger == null) {
            return;
        }
        // Verwijder record uit database en reload de data
        if (getReizigerDAO().delete(geselecteerdeReiziger)) {
            reloadData();
        }
    }
}