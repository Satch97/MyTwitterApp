package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.Clients.TwitterClient;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by satchinc on 6/27/16.
 */
public class SearchPopularResultsFragment extends TweetsListFragment {
    private TwitterClient client;
    String query = "";

    public static SearchPopularResultsFragment newInstance(String query){
        SearchPopularResultsFragment fragment = new SearchPopularResultsFragment();

        Bundle args = new Bundle();
        args.putString("searchQuery", query);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        query = getArguments().getString("searchQuery", "");
        populateTimeline();
    }

    private void populateTimeline() {
        client.getPopularSearchResults(query, new JsonHttpResponseHandler(){
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                //JSON here
                //deserialize json
                //create models
                //load the model data into list view

                try {
                    JSONArray array = json.getJSONArray("statuses");
                    addAll(Tweet.fromJSONArray(array));
                    notifyChanges();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }

            //FAILURE
        });
    }

    public void appendTweet(Tweet tweet){

        addTweet(tweet);

    }
    @Override
    public void onRefresh() {
        Toast.makeText(getContext(),"Refreshing", Toast.LENGTH_LONG).show();
        client.getPopularSearchResults(query, new JsonHttpResponseHandler(){
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                //JSON here
                //deserialize json
                //create models
                //load the model data into list view
                clearOld();
                try {
                    JSONArray array = json.getJSONArray("statuses");
                    clearOld();
                    addAll(Tweet.fromJSONArray(array));
                    notifyChanges();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }

            //FAILURE
        });


    }
}
