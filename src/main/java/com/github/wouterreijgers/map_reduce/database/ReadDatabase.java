package com.github.wouterreijgers.map_reduce.database;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class will read a mongoDB database
 */

public class ReadDatabase {
    public DB database = null;
    public DBCollection collection = null;
    public List<String> liked;
    public List<String> disliked;

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
        byte score = 1;
        document.put("score", score);
        document.put("userID", "user02");
        document.put("timestamp", "timestamp");
        document.put("songID", "234");
        collection.insert(document);
    }

    public List<String> readUserActivity(){
        DBCursor cursor = collection.find();
        List<String> user_ids = new ArrayList<String>();
        int i = 0;
        for( DBObject dock : collection.find() ) {
            String user_id = (String) dock.get( "userID" );
            user_ids.add(user_id);
        }
        return user_ids;
    }

    public void readSongScore(){
        DBCursor cursor = collection.find();
        this.liked = new ArrayList<>();
        this.disliked = new ArrayList<>();
        int i = 0;
        for( DBObject dock : collection.find() ) {
            String song = (String) dock.get( "songID" );
            int score = (int) dock.get( "score" );
            if(score==1) {
                this.liked.add(song);
                System.out.println("test");
            } else {
                this.disliked.add(song);
            }
        }
    }


    public List<String> getLikedSongs() {
        return this.liked;
    }
    public List<String> getDislikedSongs() {
        return this.disliked;
    }
}
