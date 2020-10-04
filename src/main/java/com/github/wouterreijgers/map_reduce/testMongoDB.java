package com.github.wouterreijgers.map_reduce;

import com.github.wouterreijgers.map_reduce.database.ReadDatabase;
import com.github.wouterreijgers.map_reduce.database.SongScore;
import com.github.wouterreijgers.map_reduce.database.UserActivityRead;

import java.io.File;

public class testMongoDB {
    public static void main(String[] args)
    {

        ReadDatabase readDatabase = new ReadDatabase("localhost", 3036);
        readDatabase.fillDatabase();
        System.out.println("finished");
        UserActivityRead userActivityRead = new UserActivityRead(readDatabase.readUserActivity());
        for(int e: userActivityRead.getUserids()){
            System.out.println(e);
        }
        userActivityRead.getFile();

        readDatabase.readSongScore();
        SongScore songScore = new SongScore(readDatabase.getLikedSongs(), readDatabase.getDislikedSongs());
        songScore.makeFile();

        MapReducer mapReduce = new MapReducer(new File("likes.txt"), new File("count/"));
        mapReduce.mapReduce();

    }


 }

