package com.github.wouterreijgers.map_reduce;

import com.github.wouterreijgers.map_reduce.database.FileHandler;
import com.github.wouterreijgers.map_reduce.database.ReadDatabase;
import com.github.wouterreijgers.map_reduce.database.WriteDatabase;

import java.util.List;
import java.util.Map;

public class ControlUnit extends Thread {

    @Override
    public void run()
    {
        boolean isRunning = true;
        while (isRunning)
        {
            ReadDatabase readDb = new ReadDatabase();

            // Get list of user IDs
            List<String> userActivity = readDb.readUserActivity();

            // Update internal lists (liked & disliked)
            readDb.readSongScore();

            // Get updated liked and disliked lists
            List<String> likedSongs = readDb.getLikedSongs();
            List<String> dislikedSongs = readDb.getDislikedSongs();

            // User variables to prevent typo mistakes
            String usersIn = "usersIn.txt";
            String usersOut = "usersOut.txt";
            String likedIn = "likedIn.txt";
            String likedOut = "likedOut.txt";
            String dislikedIn = "dislikedIn.txt";
            String dislikedOut = "dislikedOut.txt";

            // Write lists to their respective files
            FileHandler.writeListToFile(usersIn, userActivity);
            FileHandler.writeListToFile(likedIn, likedSongs);
            FileHandler.writeListToFile(dislikedIn, dislikedSongs);

            // Start map reducing for each file and pass the path to the previous file
            MapReducer mapReducer = new MapReducer();
            try
            {
                mapReducer.perfromMapReduce(usersIn, usersOut);
                mapReducer.perfromMapReduce(likedIn, likedOut);
                mapReducer.perfromMapReduce(dislikedIn, dislikedOut);
            }
            catch (Exception e)  { e.printStackTrace(); }

            // TODO Read entries from written file -> Code will depend on what the output will look like
            // TODO Place these entries in Maps

            Map<String, Integer> userOccurrence;
            Map<String, Integer> likedSongOccurrence;
            Map<String, Integer> dislikedSongOccurrence;

            // TODO Write entries to SQL database
            WriteDatabase writeDb = new WriteDatabase();


            try
            {
                Thread.sleep(300000);
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
