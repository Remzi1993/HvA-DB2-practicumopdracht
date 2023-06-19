package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.MainApplication;
import nl.hva.ict.models.Reiziger;
import nl.hva.ict.views.ReizigersView;
import nl.hva.ict.views.View;

/**
 * ReizigerController
 * @author HvA FDMCI HBO-ICT
 */
public class ReizigerController extends Controller {
    private final ReizigersView view;

    public ReizigerController() {
        view = new ReizigersView();
        view.getReizigersViewListView().getSelectionModel().selectedItemProperty()
                .addListener(e -> getItemsInFields());





        view.getComboReistSamenMet().getSelectionModel().selectedItemProperty()
                .addListener(e -> getItemsComboBox());



        view.getBtSave().setOnAction(e -> save());
        view.getBtUpdateData().setOnAction(e -> refreshData());
        view.getBtNew().setOnAction(e -> insert());
        view.getBtDelete().setOnAction(e -> delete());
        loadData();
    }

    private void loadData() {
        /* HvA FDMCI Databases 2 practicumopdracht - week 5M
         * Alle reizigers worden opgehaald en getoond op de reizigers overzicht - Remzi Cavdar
         * Voor MySQL gebruik MainApplication.getMysqlReizigers().getAll()
         * Voor MongoDB gebruik MainApplication.getMongodbReizigers().getAll()
         */
        ObservableList<Reiziger> reizigers = FXCollections.observableArrayList(
                MainApplication.getMongodbReizigers().getAll()
        );

        view.getReizigersViewListView().setItems(reizigers);
        view.getComboReistSamenMet().setItems(reizigers);
        view.getComboReistSamenMet().getSelectionModel().select(null);
    }

    private void refreshData() {
        /* haal de data opnieuw op uit de database
         * Voor MySQL gebruik MainApplication.getMysqlReizigers().reload()
         * Voor MongoDB gebruik MainApplication.getMongodbReizigers().reload()
         */
        MainApplication.getMongodbReizigers().reload();
    }

    private void save() {
        // bewaar (update) record
    }

    private void delete() {
        // delete dit record
    }

    private void insert() {
        //Voeg toe
    }

    private void getItemsInFields() {
        Reiziger currentReiziger = view.getReizigersViewListView().getSelectionModel().getSelectedItem();
        view.getTxtReizigersCode().setText(currentReiziger.getReizigersCode());
        view.getTxtVoornaam().setText(currentReiziger.getVoornaam());
        view.getTxtAchternaam().setText(currentReiziger.getAchternaam());
        view.getTxtAdres().setText(currentReiziger.getAdres());
        view.getTxtPostcode().setText(currentReiziger.getPostcode());
        view.getTxtPlaats().setText(currentReiziger.getPlaats());
        view.getTxtLand().setText(currentReiziger.getLand());
        view.getComboReistSamenMet().getSelectionModel().select(currentReiziger.getHoofdreiziger());
    }


    /**
     * Nog niets mee gedaan
     */
    private void getItemsComboBox() {

        // view.getComboBoxBelongsTo().setItems(observableListPersons);
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