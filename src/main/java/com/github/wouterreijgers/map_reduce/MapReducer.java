package com.github.wouterreijgers.map_reduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * This class will use the database to fetch data and perform map reducing
 * When complete, the results will be written to another database
 */
public class MapReducer {

    public static class TextMapper extends Mapper<Object, Text, Text, IntWritable>{

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            word.set( key.toString() );
            context.write(word, one);
        }
    }

    public static class IntSumReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public void perfromMapReduce(String inputFileName, String outputFileName) throws Exception {
        org.apache.hadoop.conf.Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(MapReducer.class);
        job.setMapperClass(TextMapper.class);
        job.setCombinerClass(MapReducer.IntSumReducer.class);
        job.setReducerClass(MapReducer.IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(inputFileName));
        FileOutputFormat.setOutputPath(job, new Path(outputFileName));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
