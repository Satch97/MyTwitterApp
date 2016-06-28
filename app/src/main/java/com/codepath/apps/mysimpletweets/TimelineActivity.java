package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private TweetArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //Find the listview
        lvTweets= (ListView) findViewById(R.id.lvTweets);
        //Create the arraylist(data source)
        aTweets = new TweetArrayAdapter(this, tweets);
        //Construct the adapter from the data source
        lvTweets.setAdapter(aTweets);
        //connect adapter to list view
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }
    //send api request to get the timeline json
    //fill the
    private void populateTimeline() {
        client.getHomeTimeLine(new JsonHttpResponseHandler(){
            //SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                //JSON here
                //deserialize json
                //create models
                //load the model data into list view

                aTweets.addAll(Tweet.fromJSONArray(json));
                aTweets.notifyDataSetChanged();
                Log.d("Debug", aTweets.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }

            //FAILUTE
        });
    }
}
