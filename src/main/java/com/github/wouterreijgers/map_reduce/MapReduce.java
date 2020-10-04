package com.github.wouterreijgers.map_reduce;

/**
 * Project's main class
 */
public class MapReduce {

    public static void main(String[] args)
    {
        String urlReadDB = "localhost";
        int portReadDB = 27017;

        String urlWriteDB = "jdbc:mysql://localhost:3036/club_iot";
        String sqlPassword = "Root";
        String sqlUser = "root";
        int timeout = 30000; //in ms

        System.out.println("Starting MapReduce");
        ControlUnit control = new ControlUnit(urlReadDB, portReadDB, urlWriteDB, sqlPassword, sqlUser, timeout);
        control.start();
    }



}
