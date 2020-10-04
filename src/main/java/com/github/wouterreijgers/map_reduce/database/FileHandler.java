package com.github.wouterreijgers.map_reduce.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler {

    public static void writeListToFile(String filename, List<String> list)
    {
        try{
            File file = new File(filename);
            FileWriter writer = new FileWriter(file);
            for (String element : list)
                writer.write(element + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> readMapFromFile(String filename)
    {
        Map<String, Integer> entries = new HashMap<>();

        return entries;
    }

}
