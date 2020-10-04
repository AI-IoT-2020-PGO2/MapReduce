package com.github.wouterreijgers.map_reduce;

import com.github.wouterreijgers.map_reduce.database.FileHandler;

import java.io.File;


/**
 * Project's main class
 */
public class MapReduce {

    public static void main(String[] args)
    {
        String urlReadDB = FileHandler.configReader("MONGO.URL");
        int portReadDB = Integer.parseInt(FileHandler.configReader("MONGO.PORT"));


        String urlWriteDB = FileHandler.configReader("SQL.URL");
        String sqlPassword = FileHandler.configReader("SQL.PASSWORD");
        String sqlUser = FileHandler.configReader("SQL.USER");
        int timeout = Integer.parseInt(FileHandler.configReader("TIMEOUT")); //in ms

        System.out.println("Starting MapReduce");
        ControlUnit control = new ControlUnit(urlReadDB, portReadDB, urlWriteDB, sqlPassword, sqlUser, timeout);
        control.start();
    }



}
