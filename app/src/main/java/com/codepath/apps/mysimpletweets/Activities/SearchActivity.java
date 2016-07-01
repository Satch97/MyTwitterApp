package com.codepath.apps.mysimpletweets.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.fragments.SearchPopularResultsFragment;
import com.codepath.apps.mysimpletweets.fragments.SearchResultsFragment;

public class SearchActivity extends TimelineActivity {

    private TweetsPagerAdapter pagerAdapter;
    private SearchPopularResultsFragment searchPopularResultsFragment;
    private SearchResultsFragment searchResultsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchPopularResultsFragment = new SearchPopularResultsFragment();
        searchResultsFragment = new SearchResultsFragment();
        String query = getIntent().getStringExtra("query");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        searchResultsFragment = SearchResultsFragment.newInstance(query);
        ft.commit();
        FragmentTransaction ftPop = getSupportFragmentManager().beginTransaction();
        searchPopularResultsFragment = SearchPopularResultsFragment.newInstance(query);
        ftPop.commit();
        ViewPager vpPager = (ViewPager) findViewById(R.id.searchViewpager);
        pagerAdapter = new SearchTweetsPagerAdapter(getSupportFragmentManager());
        //set the viewpager adapter for the pager
        vpPager.setAdapter(pagerAdapter);
        //find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.searchTabs);
        //attac hthe tabstrip to the view pager
        tabStrip.setViewPager(vpPager);
    }

    public class SearchTweetsPagerAdapter extends TweetsPagerAdapter {
        final int  PAGE_COUNT = 2;
        private String tabTitles[] = { "Popular", "All"};
        public SearchTweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return searchPopularResultsFragment;
            }
            else if (position==1){
                return searchResultsFragment;
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
