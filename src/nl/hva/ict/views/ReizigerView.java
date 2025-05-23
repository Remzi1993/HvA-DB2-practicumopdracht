package nl.hva.ict.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.hva.ict.models.Reiziger;

public class ReizigerView extends View {
    private final GridPane rootPane;
    private TextField txtReizigersCode, txtVoornaam, txtAchternaam, txtAdres, txtPostcode, txtPlaats, txtLand;
    private ComboBox<Reiziger> comboReistSamenMet;
    private Button btDelete, btSave, btUpdateData;
    private ListView<Reiziger> reizigersViewListView;

    public ReizigerView() {
        rootPane = new GridPane();
        createRoot();
    }

    private void createRoot() {
        //layout
        rootPane.setPadding(new Insets(10, 10, 10, 10));
        rootPane.setHgap(5);
        rootPane.setVgap(5);

        // maak velden
        txtReizigersCode = new TextField();
        txtVoornaam = new TextField();
        txtAchternaam = new TextField();
        txtAdres = new TextField();
        txtPostcode = new TextField();
        txtPlaats = new TextField();
        txtLand = new TextField();
        comboReistSamenMet = new ComboBox<>();

        //zet velden in leftPane
        GridPane leftPane = new GridPane();
        leftPane.setMinWidth(400);
        leftPane.setPadding(new Insets(10, 10, 10, 10));
        leftPane.setHgap(5);
        leftPane.setVgap(5);
        leftPane.add(new Label("Reizigerscode"), 0, 0);
        leftPane.add(txtReizigersCode, 1, 0);
        leftPane.add(new Label("Voornaam"), 0, 1);
        leftPane.add(txtVoornaam, 1, 1);
        leftPane.add(new Label("Achternaam"), 0, 2);
        leftPane.add(txtAchternaam, 1, 2);
        leftPane.add(new Label("Adres"), 0, 3);
        leftPane.add(txtAdres, 1, 3);
        leftPane.add(new Label("Postcode"), 0, 4);
        leftPane.add(txtPostcode, 1, 4);
        leftPane.add(new Label("Plaats"), 0, 5);
        leftPane.add(txtPlaats, 1, 5);
        leftPane.add(new Label("Land"), 0, 6);
        leftPane.add(txtLand, 1, 6);
        leftPane.add(new Label("Reist samen met"), 0, 7);
        leftPane.add(comboReistSamenMet, 1, 7);

        // voeg buttons toe
        btSave = new Button("Opslaan");
        btDelete = new Button("Verwijderen");
        HBox buttonBox = new HBox(btSave, btDelete);
        buttonBox.setSpacing(5);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        leftPane.add(buttonBox, 1, 8);

        //voeg leftPane toe aan rootPane
        rootPane.add(leftPane, 0, 0);

        // maak een rightPane
        reizigersViewListView = new ListView<>();
        btUpdateData = new Button("Herladen en selectie verwijderen");
        VBox rightPane = new VBox();
        rightPane.setMinWidth(300);
        rightPane.setSpacing(5);
        rightPane.getChildren().addAll(reizigersViewListView, btUpdateData);
        rightPane.setAlignment(Pos.CENTER_RIGHT);

        //voeg rightPane toe aan rootPane
        rootPane.add(rightPane, 1, 0);
    }

    public TextField getTxtReizigersCode() {
        return txtReizigersCode;
    }

    public TextField getTxtVoornaam() {
        return txtVoornaam;
    }

    public TextField getTxtAchternaam() {
        return txtAchternaam;
    }

    public TextField getTxtAdres() {
        return txtAdres;
    }

    public TextField getTxtPostcode() {
        return txtPostcode;
    }

    public TextField getTxtPlaats() {
        return txtPlaats;
    }

    public TextField getTxtLand() {
        return txtLand;
    }

    public ComboBox<Reiziger> getComboReistSamenMet() {
        return comboReistSamenMet;
    }

    public Button getBtDelete() {
        return btDelete;
    }

    public Button getBtSave() {
        return btSave;
    }

    public Button getBtUpdateData() {
        return btUpdateData;
    }

    public ListView<Reiziger> getReizigersListView() {
        return reizigersViewListView;
    }

    public GridPane getRootPane() {
        return rootPane;
    }

    @Override
    public Parent getRoot() {
        return rootPane;
    }
}