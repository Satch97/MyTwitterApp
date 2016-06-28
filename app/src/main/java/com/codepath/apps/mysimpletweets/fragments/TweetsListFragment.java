package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;


public class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    private ListView lvTweets;





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        //Find the listview
        lvTweets= (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create the arraylist(data source)
        tweets = new ArrayList<>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
        //Construct the adapter from the data source

        //inflation logic
    }

    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }
}
