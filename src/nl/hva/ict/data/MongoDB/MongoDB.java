package nl.hva.ict.data.MongoDB;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import nl.hva.ict.MainApplication;
import org.bson.Document;

/**
 * MongoDB class die de verbinding maakt met de Mongo server
 * @author Pieter Leek
 */
public class MongoDB {
    protected MongoCollection<Document> collection;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    /**
     * Verbind direct met de server als dit object wordt aangemaakt
     */
    public MongoDB() {
        connect();
    }

    // Connect database
    private void connect() {
        // Heb je geen gegevens in de MainApplication staan slaat hij het maken van de verbinding over
        if (MainApplication.getMongodbHost().equals("")) {
            System.err.println("NoSQL host is not set");
            return;
        }

        // Verbind alleen als er nog geen actieve verbinding is.
        if (this.mongoClient == null) {
            try {
                // Open pijpleiding
                this.mongoClient = MongoClients.create(MainApplication.getMongodbHost());
                // Selecteer de juiste database
                this.mongoDatabase = mongoClient.getDatabase(MainApplication.getMongodbDatabase());
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Selecteer de juiste collection
     * @param collection de collection die je wilt gebruiken
     */
    public void selectedCollection(String collection) {
        this.collection = mongoDatabase.getCollection(collection);
    }
}