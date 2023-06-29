package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.data.HotelDAO;
import nl.hva.ict.models.Hotel;
import nl.hva.ict.views.HotelView;
import nl.hva.ict.views.View;
import static nl.hva.ict.MainApplication.getHotelDAO;

/**
 * Controller voor de hotel view
 * @author HvA FDMCI HBO-ICT
 */
public class HotelController extends Controller {
    private final HotelView view;
    private HotelDAO hotelDAO;
    private ObservableList<Hotel> hotels;

    public HotelController() {
        hotelDAO = getHotelDAO();
        hotelDAO.read();
        hotels = FXCollections.observableArrayList(hotelDAO.read());
        view = new HotelView();

        // Luister naar wijzigingen in de listview en ga naar de functie getItemsInFields() als er een item wordt geselecteerd
        view.getHotelsViewListView().getSelectionModel().selectedItemProperty().addListener(
                e -> getItemsInFields()
        );

        view.getHotelsViewListView().setItems(hotels);

        // Set wat acties als op de buttons wordt geklikt
        view.getBtSave().setOnAction(e -> save());
        view.getBtUpdateData().setOnAction(e -> refreshListView());
        view.getBtNew().setOnAction(e -> clearFields());
        view.getBtDelete().setOnAction(e -> delete());
    }

    /**
     * reload de data uit de DAO
     */
    private void refreshListView() {
        hotels.setAll(hotelDAO.read());
   }


    /**
     * Save de data als op de knop wordt gedrukt
     */
    private void save() {
        // Lees de velden
        String accommodatieCode = view.getTxtAccommodatieCode().getText();
        String naam = view.getTxtNaam().getText();
        String stad = view.getTxtStad().getText();
        String land = view.getTxtLand().getText();
        String kamertype = view.getTxtKamertype().getText();
        int aantalPersonen = Integer.parseInt(view.getTxtAantalPersonen().getText());
        double prijs = Double.parseDouble(view.getTxtPrijsPerNacht().getText());
        boolean ontbijt = Boolean.parseBoolean(view.getCheckOntbijt().getText());

        // Maak een object (innerclass) en stuur dit naar de dao
        hotels.add(new Hotel(accommodatieCode, naam, stad, land, kamertype, aantalPersonen, prijs, ontbijt));

    }

    /**
     * Verwijder een hotel
     */
    private void delete() {
        // Welk hotel is geselecteerd?
        Hotel currentHotel = view.getHotelsViewListView().getSelectionModel().getSelectedItem();
        // Roep de DAO aan om het te verwijderen
        hotelDAO.delete(currentHotel);
        hotels.remove(currentHotel);

        //maak velden leeg
        clearFields();

        //werk de listview bij
        refreshListView();
    }

    /**
     * Maak alle velden leeg.
     */
    private void clearFields(){
        // maak alle velden leeg
        view.getTxtAccommodatieCode().setText("");
        view.getTxtNaam().setText("");
        view.getTxtStad().setText("");
        view.getTxtLand().setText("");
        view.getTxtKamertype().setText("");
        view.getTxtAantalPersonen().setText("");
        view.getTxtPrijsPerNacht().setText("");
        view.getCheckOntbijt().setSelected(false);
    }

    /**
     * Set alle velden als er een object in de Listview is aangeklikt
     */
    private void getItemsInFields() {
        Hotel currentHotel = view.getHotelsViewListView().getSelectionModel().getSelectedItem();
        if(currentHotel == null) {
            return;
        }
        view.getTxtAccommodatieCode().setText((currentHotel.getAccommodatieCode()));
        view.getTxtNaam().setText(currentHotel.getNaam());
        view.getTxtStad().setText(currentHotel.getStad());
        view.getTxtLand().setText(currentHotel.getLand());
        view.getTxtKamertype().setText(currentHotel.getKamer());
        view.getTxtAantalPersonen().setText((String.valueOf(currentHotel.getPersonen())));
        view.getTxtPrijsPerNacht().setText(String.valueOf(currentHotel.getPrijsPerNacht()));
        view.getCheckOntbijt().setSelected(currentHotel.isOntbijt());
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