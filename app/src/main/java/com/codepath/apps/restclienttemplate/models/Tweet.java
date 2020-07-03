package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TwitterClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity=User.class, parentColumns="id", childColumns="userID"))
public class Tweet {

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public String time;

    @ColumnInfo
    public String embedURL;

    @Ignore
    public User user;

    @ColumnInfo
    public long userID;

    // Needed for Parceler
    public Tweet(){

    }

    public static Tweet fromJson(JSONObject object) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = object.getString("text");
        tweet.createdAt = object.getString("created_at");
        tweet.user = User.fromJson(object.getJSONObject("user"));
        tweet.time = object.getString("created_at");
        if(object.getJSONObject("entities").has("media")) {
            tweet.embedURL = object.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
        } else {
            tweet.embedURL = "";
        }
        tweet.id = object.getLong("id");
        tweet.userID = tweet.user.id;
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
