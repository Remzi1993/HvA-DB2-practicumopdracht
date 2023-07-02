package nl.hva.ict.data.MongoDB;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import nl.hva.ict.MainApplication;
import nl.hva.ict.data.ReizigerDAO;
import nl.hva.ict.models.Reiziger;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Collections;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDBReizigerDAO is een class die de MongoDB database connectie maakt en de CRUD-principe toepast.
 * Ik heb de methodes add(), remove() en update() hernoemd naar create(), delete() en update() omdat ik de
 * CRUD-principe wil toepassen.
 *
 * @author HvA FDMCI HBO-ICT - Remzi Cavdar - remzi.cavdar@hva.nl
 */
public class MongoDBReizigerDAO extends ReizigerDAO {
    private final MongoDB mongoDB = new MongoDB();

    @Override
    public boolean create(Reiziger reiziger) {
        if (MainApplication.getMongodbHost().equals("")) {
            return false;
        }

        try {
            mongoDB.selectedCollection("reizigers");
            Document document = new Document();

            document.put("reiziger_code", reiziger.getReizigerCode());
            document.put("voornaam", reiziger.getVoornaam());
            document.put("achternaam", reiziger.getAchternaam());
            document.put("adres", reiziger.getAdres());
            document.put("postcode", reiziger.getPostcode());
            document.put("plaats", reiziger.getPlaats());
            document.put("land", reiziger.getLand());

            if (reiziger.getHoofdreiziger() == null) {
                document.put("hoofdreiziger", null);
            } else {
                document.put("hoofdreiziger", reiziger.getHoofdreiziger().getReizigerCode());
            }

            mongoDB.collection.insertOne(document);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Reiziger> read() {
        // Als je geen NoSQL server hebt opgegeven gaat de methode niet verder anders zou je een null pointer krijgen
        if (MainApplication.getMongodbHost().equals("")) {
            return null;
        }

        // Selecteer de juiste collection in de NoSQL server
        mongoDB.selectedCollection("reizigers");

        // Haal alles op uit deze collection en loop er 1 voor 1 doorheen
        try (
                MongoCursor<Document> cursor = mongoDB.collection.find().iterator()
        ) {
            // Maak arraylist leeg
            reizigers.clear();

            while (cursor.hasNext()) {
                Document document = cursor.next();

                if (document.get("hoofdreiziger") == null) {
                    reizigers.add(new Reiziger(
                            (String) document.get("reiziger_code"),
                            (String) document.get("voornaam"),
                            (String) document.get("achternaam"),
                            (String) document.get("adres"),
                            (String) document.get("postcode"),
                            (String) document.get("plaats"),
                            (String) document.get("land"),
                            null
                    ));
                } else {
                    reizigers.add(new Reiziger(
                            (String) document.get("reiziger_code"),
                            (String) document.get("voornaam"),
                            (String) document.get("achternaam"),
                            (String) document.get("adres"),
                            (String) document.get("postcode"),
                            (String) document.get("plaats"),
                            (String) document.get("land"),
                            read((String) document.get("hoofdreiziger"))
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

    @Override
    public Reiziger read(String reizigerCode) {
        mongoDB.selectedCollection("reizigers");

        // Search query
        Document searchQuery = new Document();
        searchQuery.put("reiziger_code", reizigerCode);
        FindIterable<Document> cursor = mongoDB.collection.find(searchQuery);

        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            while (cursorIterator.hasNext()) {
                Document document = cursorIterator.next();

                String hoofdreizigerCode = (String) document.get("hoofdreiziger");

                if(hoofdreizigerCode == null) {
                    return new Reiziger(
                            (String) document.get("reiziger_code"),
                            (String) document.get("voornaam"),
                            (String) document.get("achternaam"),
                            (String) document.get("adres"),
                            (String) document.get("postcode"),
                            (String) document.get("plaats"),
                            (String) document.get("land"),
                            null
                    );
                } else {
                    // Search query hoofdreiziger
                    Document searchQuery2 = new Document();
                    searchQuery2.put("reiziger_code", hoofdreizigerCode);
                    FindIterable<Document> cursor2 = mongoDB.collection.find(searchQuery2);

                    Reiziger hoofdreiziger = null;

                    try (final MongoCursor<Document> cursorIterator2 = cursor.cursor()) {
                        while (cursorIterator.hasNext()) {
                            Document document2 = cursorIterator.next();

                            hoofdreiziger = new Reiziger(
                                        (String) document2.get("reiziger_code"),
                                        (String) document2.get("voornaam"),
                                        (String) document2.get("achternaam"),
                                        (String) document2.get("adres"),
                                        (String) document2.get("postcode"),
                                        (String) document2.get("plaats"),
                                        (String) document2.get("land"),
                                        null
                                );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return new Reiziger(
                            (String) document.get("reiziger_code"),
                            (String) document.get("voornaam"),
                            (String) document.get("achternaam"),
                            (String) document.get("adres"),
                            (String) document.get("postcode"),
                            (String) document.get("plaats"),
                            (String) document.get("land"),
                            hoofdreiziger
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean update(Reiziger reiziger, String oudeReizigerCode) {
        if (MainApplication.getMongodbHost().equals("")) {
            return false;
        }

        try {
            // Query - Vind een reiziger met een specifieke reiziger_code
            Document query = new Document();
            query.put("reiziger_code", oudeReizigerCode);

            // Update - Verander de naam van de reiziger
            Document newDocument = new Document();
            newDocument.put("reiziger_code", reiziger.getReizigerCode());
            newDocument.put("voornaam", reiziger.getVoornaam());
            newDocument.put("achternaam", reiziger.getAchternaam());
            newDocument.put("adres", reiziger.getAdres());
            newDocument.put("postcode", reiziger.getPostcode());
            newDocument.put("plaats", reiziger.getPlaats());
            newDocument.put("land", reiziger.getLand());

            if (reiziger.getHoofdreiziger() == null) {
                newDocument.put("hoofdreiziger", null);
            } else {
                newDocument.put("hoofdreiziger", reiziger.getHoofdreiziger().getReizigerCode());
            }

            Document updateObject = new Document();
            updateObject.put("$set", newDocument);

            // Update de reiziger in de MongoDB server
            mongoDB.collection.updateOne(query, updateObject);

            // Update de reiziger in de arraylist
            Collections.fill(reizigers, reiziger);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        if (MainApplication.getMongodbHost().equals("")) {
            return false;
        }

        // Filter - Vind een reiziger met een specifieke reiziger_code
        Bson filter = eq("reiziger_code", reiziger.getReizigerCode());

        // Verwijder de reiziger uit de MongoDB server
        mongoDB.collection.deleteOne(filter);

        // Verwijder de reiziger uit de arraylist
        reizigers.remove(reiziger);
        return true;
    }
}