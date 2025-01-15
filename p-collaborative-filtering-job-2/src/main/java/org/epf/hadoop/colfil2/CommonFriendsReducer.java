package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;

public class CommonFriendsReducer extends Reducer<UserPair, Text, UserPair, Text> {
    private Text result = new Text();

    @Override
    protected void reduce(UserPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> commonFriends = new HashSet<>();
        boolean isDirectConnection = false;

        for (Text value : values) {
            if (value.toString().equals("DIRECT")) {
                isDirectConnection = true;
            } else {
                commonFriends.add(value.toString());
            }
        }

        if (!isDirectConnection && !commonFriends.isEmpty()) {
            result.set(String.valueOf(commonFriends.size()));
            context.write(key, result);
        }
    }
}
