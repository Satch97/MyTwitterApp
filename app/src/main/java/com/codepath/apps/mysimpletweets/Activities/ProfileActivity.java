package com.codepath.apps.mysimpletweets.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.Clients.TwitterClient;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;
    String screenName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
       //user = getIntent().get
        user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        if(user== null){
            client = TwitterApplication.getRestClient();
            client.getUserInfo(new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    //My current user account's info
                    getSupportActionBar().setTitle(user.getScreenName());
                    populateProfileHeader(user);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("debug", errorResponse.toString());
                }
            });
            screenName = getIntent().getStringExtra("screen_name");
        }
        else{
            getSupportActionBar().setTitle(user.getScreenName());
            populateProfileHeader(user);
            screenName = user.getScreenName();
        }
        //get the screen name
       //

        if(savedInstanceState == null) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            //display user fragment within this activity(dynamic way)
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }

    }

    private void populateProfileHeader(User user){
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        TextView tvHandle = (TextView) findViewById(R.id.tvHandle);
        TextView tvUserName = (TextView) findViewById(R.id.tvScreenName);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        ImageView ivProfilePic = (ImageView) findViewById(R.id.ivProfileImage);
        tvFollowers.setText(user.getFollowersCount());
        tvFollowing.setText(user.getFollowingCount());
        tvDescription.setText(user.getDescription());
       tvUserName.setText(user.getName());
       tvHandle.setText(user.getScreenName());
       Picasso.with(getApplicationContext()).load(user.getProfileImageUrl()).into(ivProfilePic);
    }




}
