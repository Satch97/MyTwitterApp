package com.codepath.apps.mysimpletweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.Activities.TweetDetailActivity;
import com.codepath.apps.mysimpletweets.Adapters.TweetArrayAdapter;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class TweetsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    private ListView lvTweets;
    SwipeRefreshLayout swipeLayout;





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);


        //Find the listview
        lvTweets= (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tweet thisTweet = tweets.get(position);

                Intent i = new Intent(getContext(), TweetDetailActivity.class);
                i.putExtra("tweet", Parcels.wrap(thisTweet));
                startActivity(i);
                Toast.makeText(getContext(),"Come on" , Toast.LENGTH_LONG).show();
            }
        });
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
    public void notifyChanges(){
        aTweets.notifyDataSetChanged();
    }
    public void clearOld(){
        aTweets.clear();
    }

    public void addTweet(Tweet tweet){
        tweets.add(0,tweet);
        aTweets.notifyDataSetChanged();
        lvTweets.setSelection(0);
    }


    @Override
    public void onRefresh() {
    }
}
