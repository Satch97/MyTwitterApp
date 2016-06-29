package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.mysimpletweets.Clients.TwitterClient;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client;
    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();
    }


    public void onSubmitTweet(View view) {
        EditText etTweet = (EditText) findViewById(R.id.etTweet);
        String status =  etTweet.getText().toString();
        client.postNewTweet(status, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Success", response.toString());
                tweet = new Tweet();
                tweet = Tweet.fromJSON(response);
                Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("tweet", Parcels.wrap(tweet));
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Success", errorResponse.toString());
            }
        });

    }
}