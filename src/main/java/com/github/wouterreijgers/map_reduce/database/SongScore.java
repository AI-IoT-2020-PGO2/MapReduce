package com.github.wouterreijgers.map_reduce.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SongScore {
    public List<Integer> likes;
    public List<Integer> dislikes;

    public SongScore(List<Integer> likes, List<Integer> dislikes){
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public List<Integer> getLikedSongs() {
        return likes;
    }

    public List<Integer> getDislikedSongs() {
        return dislikes;
    }

    public void makeFile() {
        try{
            File Likefile = new File("likes.txt");
            FileWriter writer = new FileWriter(Likefile);
            for(Integer user:likes)
                writer.write(user + "\n");
            writer.close();
            File dislikefile = new File("dislikes.txt");
            FileWriter dislikewriter = new FileWriter(dislikefile);
            for(Integer user:likes)
                dislikewriter.write(user + "\n");
            dislikewriter.close();
            System.out.println("made file");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
