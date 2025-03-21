package net.neoooo.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import net.neoooo.Lighty;
import org.bson.Document;

public class MongoDB_Manager {

    private String uri = "mongodb://" + Lighty.getDotenv().get("username") + ":" + Lighty.getDotenv().get("password") + "@" + Lighty.getDotenv().get("address");
    private MongoClient client = MongoClients.create(uri);
    private MongoDatabase database = client.getDatabase("lighty_bot");


    public MongoDatabase getDatabase() {
        return database;
    }


    public Document getQuery(String serverid, String collectionname){
        BasicDBObject query = new BasicDBObject("serverid", serverid);
        var cursor = database.getCollection(collectionname).find(query);

        while(cursor.cursor().hasNext()) {
            return cursor.cursor().next();
        }
        return null;
    }

    public boolean isCollectionEmpty(String modulename){
        return database.getCollection(modulename).countDocuments() == 0 ? true : false;
    }

    public void updateValue(String serverid, String collectionname, String key, Object value){
        BasicDBObject query = new BasicDBObject();
        query.put("serverid", serverid); // (1)

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(key, value); // (2)

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument); // (3)

        database.getCollection(collectionname).updateOne(query, updateObject); // (4)
    }
}
