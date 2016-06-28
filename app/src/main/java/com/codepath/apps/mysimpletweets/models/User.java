package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by satchinc on 6/27/16.
 */
public class User {
    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public static User fromJSON(JSONObject json){
        User u = new User();
        //extract and fill the values
        try {
            u.name=json.getString("name");
            u.uid= json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;

    }
}
