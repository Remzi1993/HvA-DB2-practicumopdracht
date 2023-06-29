package nl.hva.ict.data.MongoDB;

import com.mongodb.client.MongoCursor;
import nl.hva.ict.MainApplication;
import nl.hva.ict.data.ReizigerDAO;
import nl.hva.ict.models.Reiziger;
import org.bson.Document;
import java.util.List;
import java.util.Objects;
import static nl.hva.ict.MainApplication.getReizigerDAO;

public class MongoDBReizigerDAO extends ReizigerDAO {
    private final MongoDB mongoDB = new MongoDB();

    // TODO: Implementeren van deze methode
    @Override
    public boolean create(Reiziger reiziger) {
        return false;
    }

    @Override
    public List<Reiziger> read() {
        // Als je geen NoSQL server hebt opgegeven gaat de methode niet verder anders zou je een null pointer krijgen
        if (MainApplication.getMongodbHost().equals("")) {
            return null;
        }

        // Selecteer de juiste collection in de NoSQL server
        mongoDB.selectedCollection("reiziger");

        // Haal alles op uit deze collection en loop er 1 voor 1 doorheen
        try (
                MongoCursor<Document> cursor = mongoDB.collection.find().iterator()
        ) {
            // Maak arraylist leeg
            reizigers.clear();

            while (cursor.hasNext()) {
                Document document = cursor.next();

                if(Objects.equals(document.get("Hoofdreiziger"), "")) {
                    reizigers.add(new Reiziger(
                            (String) document.get("Reizigers code"),
                            (String) document.get("Voornaam"),
                            (String) document.get("Achternaam"),
                            (String) document.get("Adres"),
                            (String) document.get("Postcode"),
                            (String) document.get("Plaats"),
                            (String) document.get("Land")
                    ));
                } else {
                    reizigers.add(new Reiziger(
                            (String) document.get("Reizigers code"),
                            (String) document.get("Voornaam"),
                            (String) document.get("Achternaam"),
                            (String) document.get("Adres"),
                            (String) document.get("Postcode"),
                            (String) document.get("Plaats"),
                            (String) document.get("Land"),
                            getReizigerDAO().read((String) document.get("Hoofdreiziger"))
                    ));
                }
            }

            return reizigers;
        } catch (Exception e) {
            System.err.println("Something went wrong!");
            e.printStackTrace();
        }

        return null;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean update(Reiziger reiziger) {
        return false;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean delete(Reiziger reiziger) {
        return false;
    }
}