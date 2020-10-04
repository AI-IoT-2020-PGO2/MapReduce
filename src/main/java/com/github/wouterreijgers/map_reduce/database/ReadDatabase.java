package com.github.wouterreijgers.map_reduce.database;

import com.mongodb.*;

import java.util.*;

/**
 * This class will read a mongoDB database
 */

public class ReadDatabase {
    public DB database = null;
    public DBCollection collection = null;
    public List<Integer> liked;
    public List<Integer> disliked;
    public HashSet<Integer> songList;


    public ReadDatabase(String url, int port){

        MongoClient mongoClient = new MongoClient(url, port);
        this.database = mongoClient.getDB(FileHandler.configReader("MONGO.DB"));
        System.out.println(mongoClient.getDatabaseNames());
        database.createCollection(FileHandler.configReader("MONGO.COLLECTION"), null);
        this.collection = database.getCollection(FileHandler.configReader("MONGO.COLLECTION"));

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
        List<Integer> user_ids = new ArrayList<Integer>();
        for( DBObject dock : collection.find() ) {
            int user_id = (int) dock.get( "userID" );
            user_ids.add(user_id);
        }
        return user_ids;
    }

    public void readSongScore(){
        this.liked = new ArrayList<>();
        this.disliked = new ArrayList<>();
        this.songList = null;
        this.songList = new HashSet<>();
        for( DBObject dock : collection.find() ) {
            int song = (Integer) dock.get( "songID" );
            int score = (int) dock.get( "score" );
            songList.add(song);
            if(score==1) {
                this.liked.add(song);
                System.out.println(song+","+score);
            } else {
                this.disliked.add(song);
            }
        }
    }

    public HashSet<Integer> getSongList(){return this.songList;}

    public List<Integer> getLikedSongs() {
        return this.liked;
    }
    public List<Integer> getDislikedSongs() {
        return this.disliked;
    }
}
