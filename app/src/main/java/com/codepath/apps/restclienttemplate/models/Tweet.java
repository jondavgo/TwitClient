package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String time;
    public String embedURL;

    // Needed for Parceler
    public Tweet(){

    }

    public static Tweet fromJson(JSONObject object) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = object.getString("text");
            tweet.createdAt = object.getString("created_at");
            tweet.user = User.fromJson(object.getJSONObject("user"));
            tweet.time = object.getString("created_at");
            if(object.getJSONObject("entities").has("media")) {
                tweet.embedURL = object.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
            } else {
                tweet.embedURL = "";
            }
        } catch(Exception e){
            Log.i("Tweet.fromJson", "JSONException", e);
        }
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
