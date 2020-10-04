package com.github.wouterreijgers.map_reduce;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class will use the database to fetch data and perform map reducing
 * When complete, the results will be written to another database
 */
public class MapReducer {
    public File out;
    public File in;

    public MapReducer(File in, File out){
        this.out = out;
        this.in = in;
        System.out.println();
        System.setProperty("hadoop.home.dir", System.getProperty("user.dir"));
    }

    public File mapReduce(){
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("SparkFileSumApp");
        conf.set("spark.driver.bindAddress", "127.0.0.1");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data);

        deleteDirectory(out);
        JavaRDD<String> textFile = sc.textFile(in.getPath());
        JavaPairRDD<String, Integer> counts = textFile
                .flatMap(s -> Arrays.asList(s.split("\n")).iterator())
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum);
        counts.saveAsTextFile(out.getPath());
        try{
            return createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public File createFile() throws IOException {
        File output = new File(out.getPath()+"/"+in.getName());
        output.createNewFile();
        FileWriter myWriter = new FileWriter(output);
        for(File f:out.listFiles()) {
            try {
                Scanner myReader = new Scanner(f);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (data.startsWith("("))
                        myWriter.write(data.substring(1, data.length() - 1)+"\n");
                    System.out.println(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        myWriter.close();
        return output;


    }
}

