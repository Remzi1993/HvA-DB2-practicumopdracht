package nl.hva.ict.data.MongoDB;

import nl.hva.ict.MainApplication;
import nl.hva.ict.data.LandDAO;
import nl.hva.ict.models.Land;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.eq;

public class MongoDBLandDAO extends LandDAO {
    private final MongoDB mongoDB = new MongoDB();

    // TODO: Implementeren van deze methode
    @Override
    public boolean create(Land land) {
        return false;
    }

    // TODO: Implementeren van deze methode
    @Override
    public List<Land> read() {
        return landen;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean update(Land land) {
        return false;
    }

    // TODO: Implementeren van deze methode
    @Override
    public boolean delete(Land land) {
        return false;
    }

    /**
     * Haal alle informatie op uit de NoSQL server over welke landen een bepaalde taal spreken. Gebruik hiervoor aggregation.
     * Zet het resultaat in de arraylist
     * @param taal Welke taal wil je weten
     * @param alleenAfrika filter het resultaat zodat wel of niet alleen afrikaanse landen terug komen
     */
    public void wieSpreekt(String taal, boolean alleenAfrika) {
        // Als je geen NoSQL server hebt opgegeven gaat de methode niet verder anders zou je een nullpointer krijgen
        if (MainApplication.getMongodbHost().equals(""))
            return;

        // Reset arraylist
        landen.clear();

        // Selecteer collection
        mongoDB.selectedCollection("landen");

        // Aggregation functie in Mongo
        Bson match = match(eq("languages.name", taal));

        List<Document> results = mongoDB.collection.aggregate(List.of(match)).into(new ArrayList<>());

        // Maak models en voeg resultaat toe aan arraylist
        for (Document land : results) {
            landen.add(new Land(land.get("name").toString(), land.get("capital").toString()));
        }
    }

    /**
     * Haal alle informatie op uit de NoSQL server in welke landen je met een bepaalde valuta kan betalen. Gebruik hiervoor aggregation.
     * Zet het resultaat in de arraylist
     * @param valuta Welke valuta wil je weten
     * @param alleenAfrika filter het resultaat zodat wel of niet alleen afrikaanse landen terug komen
     */
    public void waarBetaalJeMet(String valuta, boolean alleenAfrika) {
    }

    /**
     * Welke landen zijn er in welk werelddeel. Haal deze informatie uit de database.
     * Gebruik hiervoor aggregation.
     * Zet het resultaat in de arraylist
     * @param werelddeel Welke valuta wil je weten
     */
    public void welkeLandenZijnErIn(String werelddeel) {
    }

    /**
     * Hoeveel inwoners heeft Oost-Afrika?. Haal deze informatie uit de database en gebruik hiervoor aggregation.
     */
    public int hoeveelInwonersOostAfrika() {
        // Reset arraylist
        landen.clear();

        // Om geen compile error te krijgen wordt tijdelijk 0 teruggegeven.
        return 0;
    }
}