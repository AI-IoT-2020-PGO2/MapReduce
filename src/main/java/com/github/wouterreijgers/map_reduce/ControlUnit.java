package com.github.wouterreijgers.map_reduce;

import com.github.wouterreijgers.map_reduce.database.FileHandler;
import com.github.wouterreijgers.map_reduce.database.ReadDatabase;
import com.github.wouterreijgers.map_reduce.database.WriteDatabase;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ControlUnit extends Thread {

    String urlReadDB, urlWriteDB, password, user;
    int portReadDB, timeout;

    public ControlUnit(String urlReadDB, int portReadDB,  String urlWriteDB, String password, String user, int timeout){
        this.urlReadDB = urlReadDB;
        this.urlWriteDB = urlWriteDB;
        this.portReadDB = portReadDB;
        this.password = password;
        this.user=user;
        this.timeout = timeout;
    }
    @Override
    public void run()
    {
        // User variables to prevent typo mistakes
        String usersIn = "usersIn.txt";
        String usersOut = "count_users/";
        String likedIn = "likedIn.txt";
        String likedOut = "count_liked/";
        String dislikedIn = "dislikedIn.txt";
        String dislikedOut = "count_disliked/";
        String songScoreIn = "songScoreIn.txt";
        String scoreOut = "songScore/";

        //Setting up the mapReducer
        MapReducer mapReducer_userActivity = new MapReducer(new File(usersIn), new File(usersOut));
        MapReducer mapReducer_liked = new MapReducer(new File(likedIn), new File(likedOut));
        MapReducer mapReducer_disliked = new MapReducer(new File(dislikedIn), new File(dislikedOut));

        WriteDatabase writeDb = new WriteDatabase(this.urlWriteDB, user, password);


        boolean isRunning = true;
        while (isRunning)
        {
            ReadDatabase readDb = new ReadDatabase(urlReadDB, portReadDB);

            // Get list of user IDs
            List<Integer> userActivity = readDb.readUserActivity();


            // Update internal lists (liked & disliked)
            readDb.readSongScore();

            // Get updated liked and disliked lists
            List<Integer> likedSongs = readDb.getLikedSongs();
            List<Integer> dislikedSongs = readDb.getDislikedSongs();



            // TODO test if writeListToFile works for each list
            // Write lists to their respective files
            FileHandler.writeintListToFile(usersIn, userActivity);
            FileHandler.writeintListToFile(likedIn, likedSongs);
            FileHandler.writeintListToFile(dislikedIn, dislikedSongs);

            File user_count = mapReducer_userActivity.mapReduce();
            if (user_count != null){
                System.out.println("User count succeeded, result stored in: "+user_count.getPath());
            }
            File liked_count = mapReducer_liked.mapReduce();
            if (liked_count != null){
                System.out.println("Liked count succeeded, result stored in: "+liked_count.getPath());
            }
            File disliked_count = mapReducer_disliked.mapReduce();
            if (disliked_count != null){
                System.out.println("Disliked count succeeded, result stored in: "+disliked_count.getPath());
            }
            // TODO test if FileHandler can read the mapreduce output (it splits each line on the last occurrence of a space so spaces in names should be supported)
            // Output probably looks like:<entry_name> <occurrence_amount>
            // name/song here 12
            // another entry here 2
            // more entries here 5
            Map<Integer, Integer> userOccurrence = FileHandler.readUserMapFromFile(user_count);
            Map<Integer, Integer> likedSongOccurrence = FileHandler.readMapFromFile(liked_count);
            Map<Integer, Integer> dislikedSongOccurrence = FileHandler.readMapFromFile(disliked_count);

            // TODO Read url, username & password from a file
            writeDb.updateUsersField(userOccurrence);
            writeDb.updateSongScore(likedSongOccurrence, dislikedSongOccurrence, readDb.getSongList());
            // TODO create update method for songs

            try
            {
                // TODO find a right amount of sleep time (milliseconds)
                Thread.sleep(timeout);
            }
            catch (InterruptedException e)
            {
                isRunning = false;
                e.printStackTrace();
            }
        }
        System.out.println("Halted map reducing");
    }
}
