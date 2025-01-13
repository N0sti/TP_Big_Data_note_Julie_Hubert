package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class RelationshipMapper extends Mapper<LongWritable, Relationship, Text, Text> {

    private Text outKey = new Text();
    private Text outValue = new Text();

    @Override
    public void map(LongWritable key, Relationship value, Context context)
            throws IOException, InterruptedException {
        // Nettoyer les IDs en retirant le timestamp
        String id1 = cleanId(value.getId1());
        String id2 = cleanId(value.getId2());

        // Émettre la première relation
        outKey.set(id1);
        outValue.set(id2);
        context.write(outKey, outValue);

        // Émettre la relation inverse
        outKey.set(id2);
        outValue.set(id1);
        context.write(outKey, outValue);
    }

    // Méthode helper pour nettoyer les IDs
    private String cleanId(String id) {
        // Si l'ID contient une virgule et un timestamp, ne garder que la partie avant la virgule
        if (id.contains(",")) {
            return id.split(",")[0];
        }
        return id;
    }
}