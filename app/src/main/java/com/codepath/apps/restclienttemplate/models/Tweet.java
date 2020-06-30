package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String time;

    public static Tweet fromJson(JSONObject object) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = object.getString("text");
        tweet.createdAt = object.getString("created_at");
        tweet.user = User.fromJson(object.getJSONObject("user"));
        tweet.time = object.getString("created_at");
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray array) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            tweets.add(fromJson(array.getJSONObject(i)));
        }
        return tweets;
    }
}
