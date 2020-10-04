package com.github.wouterreijgers.map_reduce.database;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class will read a mongoDB database
 */

public class ReadDatabase {
    public DB database = null;
    public DBCollection collection = null;
    public List<Integer> liked;
    public List<Integer> disliked;

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
        Random rn = new Random();
        int id = rn.nextInt(10) + 1;
        int song = rn.nextInt(10) + 1;

        BasicDBObject document = new BasicDBObject();
        byte score = 1;
        document.put("score", score);
        document.put("userID", id);
        document.put("timestamp", "timestamp");
        document.put("songID", song);
        collection.insert(document);
    }

    public List<Integer> readUserActivity(){
        DBCursor cursor = collection.find();
        List<Integer> user_ids = new ArrayList<Integer>();
        for( DBObject dock : collection.find() ) {
            int user_id = (int) dock.get( "userID" );
            user_ids.add(user_id);
        }
        return user_ids;
    }

    public void readSongScore(){
        DBCursor cursor = collection.find();
        this.liked = new ArrayList<>();
        this.disliked = new ArrayList<>();
        for( DBObject dock : collection.find() ) {
            int song = (Integer) dock.get( "songID" );
            int score = (int) dock.get( "score" );
            if(score==1) {
                this.liked.add(song);
                System.out.println("test");
            } else {
                this.disliked.add(song);
            }
        }
    }


    public List<Integer> getLikedSongs() {
        return this.liked;
    }
    public List<Integer> getDislikedSongs() {
        return this.disliked;
    }
}
