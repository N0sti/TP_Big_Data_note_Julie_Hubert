package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;

public class CommonFriendsMapper extends Mapper<Object, Text, UserPair, Text> {
    private UserPair userPair = new UserPair();
    private Text relationInfo = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Parse input: "user: friend1, friend2, friend3"
        String[] parts = value.toString().split(":");
        if (parts.length < 2) {
            return; // Skip malformed input
        }

        String user = parts[0].trim();
        String[] friends = parts[1].trim().split(",\\s*");

        // Emit pairs (User1, User2)
        for (int i = 0; i < friends.length; i++) {
            for (int j = i + 1; j < friends.length; j++) {
                userPair = new UserPair(friends[i], friends[j]);
                relationInfo.set(user); // Emit the user as a value
                context.write(userPair, relationInfo);
            }
        }

        // Mark direct connections
        for (String friend : friends) {
            userPair = new UserPair(user, friend);
            relationInfo.set("DIRECT");
            context.write(userPair, relationInfo);
        }
    }
}
