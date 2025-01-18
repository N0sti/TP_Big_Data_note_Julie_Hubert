package org.epf.hadoop.colfil1;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ColFilJob1 extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        System.out.println("Number of arguments: " + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("Argument " + (i + 1) + ": " + args[i]);
        }
        if (args.length != 3) {
            System.err.println("Usage: ColFilJob1 <input path> <output path>");
            return -1;
        }

        String input = args[1];
        String output = args[2];

        Configuration conf = getConf();
        Job job = Job.getInstance(conf, "Relationship Processing");
        job.setJarByClass(ColFilJob1.class);

        // Configurer input/output
        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        // mettre un format custom pour l'InputFormat
        job.setInputFormatClass(RelationshipInputFormat.class);

        // Set Mapper and Reducer
        job.setMapperClass(RelationshipMapper.class);
        job.setReducerClass(RelationshipReducer.class);

        // saisie le nombre de reducers
        job.setNumReduceTasks(2);

        // saisie des types de sortie
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int exitCode = ToolRunner.run(conf, new ColFilJob1(), args);
        System.exit(exitCode);
    }

}