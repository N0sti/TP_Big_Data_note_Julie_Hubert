package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.RecordReader;

import java.io.IOException;

public class RelationshipInputFormat extends FileInputFormat<LongWritable, Relationship> {
    @Override
    public RecordReader<LongWritable, Relationship> createRecordReader(InputSplit split, TaskAttemptContext context)
            throws IOException, InterruptedException {
        RelationshipRecordReader reader = new RelationshipRecordReader();
        reader.initialize(split, context);
        return reader;
    }
}
