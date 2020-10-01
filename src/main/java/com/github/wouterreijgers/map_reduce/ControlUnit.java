package com.github.wouterreijgers.map_reduce;

public class ControlUnit extends Thread {

    @Override
    public void run()
    {
        boolean isRunning = true;
        while (isRunning)
        {
            // TODO Get elements from database (noSQL) and write them to a file
            // TODO Start map reducing and pass the path to the previous file
            // TODO Read entries from written file
            // TODO Write read entries to other database (SQL)
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
