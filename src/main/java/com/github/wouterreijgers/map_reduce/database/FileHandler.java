package com.github.wouterreijgers.map_reduce.database;

import org.apache.commons.io.FileUtils;

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


    public static Map<Integer, Integer> readMapFromFile(File filename)
    {
        Map<Integer, Integer> entries = new HashMap<>();
        try{
            FileInputStream fstream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String line;
            while ((line = br.readLine()) != null)   {
                int index = line.lastIndexOf(",");
                if (index < line.length()-1)
                {
                    String entryStr = line.substring(0, index);
                    String scoreStr = line.substring(index + 1);
                    int score = -1;
                    try
                    {
                        score = Integer.parseInt(scoreStr);
                    }
                    catch (NumberFormatException exc) { exc.printStackTrace(); }

                    if (score != -1)
                        entries.put( Integer.parseInt(entryStr), score );
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

    public static void concatFiles(String path1, String path2, String destination) throws IOException {
        // Files to read
        File file1 = new File(path1);
        File file2 = new File(path2);

        // File to write
        File file3 = new File(destination);

        // Read the file as string
        String file1Str = FileUtils.readFileToString(file1);
        String file2Str = FileUtils.readFileToString(file2);

        // Write the file
        FileUtils.write(file3, file1Str);
        FileUtils.write(file3, file2Str, true); // true for append
    }
}


