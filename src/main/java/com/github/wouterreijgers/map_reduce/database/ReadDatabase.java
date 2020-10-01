package com.github.wouterreijgers.map_reduce.database;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will read a mongoDB database
 */

public class ReadDatabase {
    public DB database = null;
    public DBCollection collection = null;

    public ReadDatabase(){
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        this.database = mongoClient.getDB("test-mongo-db");
        System.out.println(mongoClient.getDatabaseNames());
        database.createCollection("votes", null);
        this.collection = database.getCollection("votes");

    }

    public DB getDatabase(){
        return this.database;
    }

    /***
     * This function will add an entry to the DB every time it's executed
     */
    public void fillDatabase(){
        BasicDBObject document = new BasicDBObject();
        document.put("song", "a song");
        document.put("user_id", "user02");
        document.put("timestamp", "timestamp");
        document.put("score", "1");
        collection.insert(document);
    }

    public List<String> readUserActivity(){
        DBCursor cursor = collection.find();
        List<String> user_ids = new ArrayList<String>();
        int i = 0;
        for( DBObject dock : collection.find() ) {
            String user_id = (String) dock.get( "user_id" );
            user_ids.add(user_id);
        }
        return user_ids;
    }


}
