package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class RelationshipReducer extends Reducer<Text, Text, Text, Text> {

    private Text result = new Text();
    private Text formattedKey = new Text();

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        // Utiliser un TreeSet pour trier et éliminer les doublons
        Set<String> relations = new TreeSet<>();

        // Collecter toutes les relations uniques
        for (Text val : values) {
            relations.add(val.toString());
        }

        // Construire la chaîne de sortie
        String relationsList = String.join(", ", relations);

        // Formater la clé avec le deux points
        formattedKey.set(key.toString() + " :");
        result.set(relationsList);

        context.write(formattedKey, result);
    }
}