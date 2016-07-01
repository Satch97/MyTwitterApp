package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.SmartFragmentStatePagerAdapter;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity {

    private TweetsListFragment fragmentTweetsList;
    private final int REQUEST_CODE=20;
    private TweetsPagerAdapter pagerAdapter;
    private HomeTimelineFragment homeTimelineFragment;
    private  MentionTimelineFragment mentionTimelineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        homeTimelineFragment = new HomeTimelineFragment();
        mentionTimelineFragment = new MentionTimelineFragment();
        //Get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        //set the viewpager adapter for the pager
        vpPager.setAdapter(pagerAdapter);
        //find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //attac hthe tabstrip to the view pager
        tabStrip.setViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "Search Query Submitted", Toast.LENGTH_LONG).show();
                Intent i = new Intent(TimelineActivity.this, SearchActivity.class);
                i.putExtra("query", query);
                startActivity(i);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("user", Parcels.wrap(null));
        startActivity(i);
    }

    public void onComposeNewTweet(MenuItem item) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    //send api request to get the timeline json
    //fill the


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode==REQUEST_CODE)&&(resultCode==RESULT_OK)){
            Tweet tweet =  Parcels.unwrap(data.getParcelableExtra("tweet"));
            HomeTimelineFragment fragmentHomeTweets =
                    (HomeTimelineFragment)  pagerAdapter.getRegisteredFragment(0);// adapterViewPager.getRegisteredFragment(0);
          fragmentHomeTweets.appendTweet(tweet); //I HAVE A PROBLEM GETTING THE TWEET BACK INTO THIS ACTIVITY NOT SURE WHAT IT IS

        }
    }

    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {
        final int  PAGE_COUNT = 2;
        private String tabTitles[] = { "Home", "Mentions"};
        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return homeTimelineFragment;
            }
            else if (position==1){
                return mentionTimelineFragment;
            }
            else
                {
                        return null;
                }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }


}
