package com.github.wouterreijgers.map_reduce;

/**
 * Project's main class
 */
public class MapReduce {

    public static void main(String[] args)
    {
        System.out.println("Starting MapReduce");
        ControlUnit control = new ControlUnit();
        control.start();
    }

}
