package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class User {

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String handle;

    @ColumnInfo
    public String imageURL;

    // Needed for Parceler
    public User() {
    }

    public static User fromJson(JSONObject object) throws JSONException {
        User user = new User();
        user.name = object.getString("name");
        user.handle = object.getString("screen_name");
        user.imageURL = object.getString("profile_image_url_https");
        user.id = object.getLong("id");
        return user;
    }

    public static List<User> fromJsonTweetArray(List<Tweet> array) {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < array.size(); i++){
            users.add(array.get(i).user);
        }
        return users;
    }
}