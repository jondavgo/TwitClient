package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public String handle;
    public String imageURL;

    // Needed for Parceler
    public User() {
    }

    public static User fromJson(JSONObject object) throws JSONException {
        User user = new User();
        user.name = object.getString("name");
        user.handle = object.getString("screen_name");
        user.imageURL = object.getString("profile_image_url_https");
        return user;
    }
}