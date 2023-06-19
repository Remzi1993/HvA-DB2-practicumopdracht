package nl.hva.ict.views;

import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import nl.hva.ict.models.Boekingsoverzicht;

public class BoekingsoverzichtView extends View {
    private final GridPane rootPane;
    private final ListView<Boekingsoverzicht> boekingsOverzichtListView;

    public BoekingsoverzichtView() {
        rootPane = new GridPane();
        boekingsOverzichtListView = new ListView<>();
        createRoot();
    }

    private void createRoot() {
        boekingsOverzichtListView.setPrefWidth(800);
        boekingsOverzichtListView.setPrefHeight(800);
        rootPane.getChildren().addAll(boekingsOverzichtListView);
    }

    @Override
    public Parent getRoot() {
        return rootPane;
    }

    public ListView<Boekingsoverzicht> getBoekingsOverzichtListView() {
        return boekingsOverzichtListView;
    }
}