package com.codepath.apps.mysimpletweets.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

//parse the json + store the data, encapsulate state logic or display logic
@Parcel
public class Tweet {
    //list out the attributes that we care about
    private String body;
    private long uid;
    private User user;
    private String createdAt;

    public long getUid() {
        return uid;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }



    public User getUser() {
        return user;
    }

    public Tweet(){

    }



    //Deserialze the JSON
    //Tweet.fromJSON("{..}")
    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt=jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return tweet;
    }
    //Tweet.fromJSONArray([{ ...}, { ...}] ==> List<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i< jsonArray.length();i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet!=null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }
        return tweets;
    }
}
