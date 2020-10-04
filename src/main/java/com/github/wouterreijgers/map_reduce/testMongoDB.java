package com.github.wouterreijgers.map_reduce;

import com.github.wouterreijgers.map_reduce.database.ReadDatabase;
import com.github.wouterreijgers.map_reduce.database.SongScore;
import com.github.wouterreijgers.map_reduce.database.UserActivityRead;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class testMongoDB {
    public static void main(String[] args)
    {

        ReadDatabase readDatabase = new ReadDatabase();
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

