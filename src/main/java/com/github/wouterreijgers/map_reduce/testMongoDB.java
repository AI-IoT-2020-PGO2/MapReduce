package com.github.wouterreijgers.map_reduce;

import com.github.wouterreijgers.map_reduce.database.ReadDatabase;
import com.github.wouterreijgers.map_reduce.database.UserActivityRead;

public class testMongoDB {
    public static void main(String[] args)
    {
        ReadDatabase readDatabase = new ReadDatabase();
        readDatabase.fillDatabase();
        System.out.println("finished");
        UserActivityRead userActivityRead = new UserActivityRead(readDatabase.readUserActivity());
        for(String e: userActivityRead.getUserids()){
            System.out.println(e);
        }
    }
 }
