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

            // TODO test if writeListToFile works for each list
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

            // TODO test if FileHandler can read the mapreduce output (it splits each line on the last occurrence of a space so spaces in names should be supported)
            // Output probably looks like:<entry_name> <occurrence_amount>
            // name/song here 12
            // another entry here 2
            // more entries here 5
            Map<Integer, Integer> userOccurrence = FileHandler.readUserMapFromFile(usersOut);
            Map<String, Integer> likedSongOccurrence = FileHandler.readMapFromFile(likedOut);
            Map<String, Integer> dislikedSongOccurrence = FileHandler.readMapFromFile(dislikedOut);

            // TODO Read url, username & password from a file
            WriteDatabase writeDb = new WriteDatabase("url_to_db", "root", "");
            writeDb.updateUsersField(userOccurrence);
            // TODO create update method for songs

            try
            {
                // TODO find a right amount of sleep time (milliseconds)
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
