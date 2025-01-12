package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class RelationshipReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        Set<String> relations = new TreeSet<>();
        for (Text val : values) {
            relations.add(val.toString());
        }

        String relationsList = String.join(",", relations);
        context.write(key, new Text(relationsList));
    }
}