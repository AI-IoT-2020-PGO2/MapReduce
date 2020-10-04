package com.github.wouterreijgers.map_reduce.database;

import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

    public static void writeintListToFile(String filename, List<Integer> list)
    {
        try{
            File file = new File(filename);
            FileWriter writer = new FileWriter(file);
            for (int element : list)
                writer.write(element + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Map<String, Integer> readMapFromFile(File filename)
    {
        Map<String, Integer> entries = new HashMap<>();
        try{
            FileInputStream fstream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String line;
            while ((line = br.readLine()) != null)   {
                int index = line.lastIndexOf(",");
                if (index < line.length()-1)
                {
                    String entry = line.substring(0, index);
                    String scoreStr = line.substring(index + 1);
                    int score = -1;
                    try
                    {
                        score = Integer.parseInt(scoreStr);
                    }
                    catch (NumberFormatException exc) { exc.printStackTrace(); }

                    if (score != -1)
                        entries.put( entry, score );
                }
            }

            fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public static Map<Integer, Integer> readUserMapFromFile(File input)
    {
        Map<Integer, Integer> entries = new HashMap<>();
        try{
            FileInputStream fstream = new FileInputStream(input);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String line;
            while ((line = br.readLine()) != null)   {
                int index = line.lastIndexOf(",");
                if (index < line.length()-1)
                {
                    String idStr = line.substring(0, index);
                    String scoreStr = line.substring(index + 1);
                    int id = -1;
                    int score = -1;
                    try
                    {
                        id = Integer.parseInt(idStr);
                        score = Integer.parseInt(scoreStr);
                    }
                    catch (NumberFormatException exc) { exc.printStackTrace(); }

                    if (score != -1 && id != -1)
                        entries.put( id, score );
                }
            }

            fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

}
