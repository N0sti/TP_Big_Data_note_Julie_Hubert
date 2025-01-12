package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class RelationshipMapper extends Mapper<LongWritable, Relationship, Text, Text> {

    @Override
    public void map(LongWritable key, Relationship value, Context context)
            throws IOException, InterruptedException {
        Text userId1 = new Text(value.getId1());
        Text userId2 = new Text(value.getId2());

        context.write(userId1, userId2);
        context.write(userId2, userId1);
    }
}