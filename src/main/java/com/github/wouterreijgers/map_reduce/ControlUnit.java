package com.github.wouterreijgers.map_reduce;

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

            // Update internal lists
            readDb.readSongScore();

            // Get updated liked and disliked lists
            List<String> likedSongs = readDb.getLikedSongs();
            List<String> dislikedSongs = readDb.getDislikedSongs();

            // TODO write userActivity, likedSongs and dislikedSongs to seperate files (names must match input of mapreduce)

            // Start map reducing and pass the path to the previous file
            MapReducer mapReducer = new MapReducer();
            try
            {
                mapReducer.perfromMapReduce("usersFileIn.txt", "usersFileOut.txt");
                mapReducer.perfromMapReduce("likedSongsIn.txt", "likedSongsOut.txt");
                mapReducer.perfromMapReduce("dislikedSongsIn.txt", "dislikedSongsOut.txt");
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
