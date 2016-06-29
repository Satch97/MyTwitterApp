package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by satchinc on 6/27/16.
 */
@Parcel
public class User {

    public User(){

    }
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

    public String getFollowingCount() {
        return followingCount;
    }

    public String getDescription() {
        return description;
    }

    public String getFollowersCount() {
        return followersCount;
    }

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    //new fields

    private String followersCount;
    private String followingCount;



    private String description;

    public static User fromJSON(JSONObject json){
        User u = new User();
        //extract and fill the values
        try {
            u.name=json.getString("name");
            u.uid= json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.followersCount = json.getString("followers_count");
            u.followingCount = json.getString("following");
            u.description= json.getString("description");



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;

    }
}
