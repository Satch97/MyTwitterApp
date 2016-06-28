package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;

public class TimelineActivity extends AppCompatActivity {

    private TweetsListFragment fragmentTweetsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
    }
    //send api request to get the timeline json
    //fill the



}
