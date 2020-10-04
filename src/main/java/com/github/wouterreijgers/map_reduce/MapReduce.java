package com.github.wouterreijgers.map_reduce;

/**
 * Project's main class
 */
public class MapReduce {

    public static void main(String[] args)
    {
        String urlReadDB = "localhost";
        int portReadDB = 27017;

        String urlWriteDB = "localhost";

        System.out.println("Starting MapReduce");
        ControlUnit control = new ControlUnit(urlReadDB, portReadDB, urlWriteDB);
        control.start();
    }

}
