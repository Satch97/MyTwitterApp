package com.codepath.apps.mysimpletweets.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        TextView tvName = (TextView) findViewById(R.id.tvScreenName);
        TextView tvHandle = (TextView) findViewById(R.id.tvHandle);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvBody = (TextView) findViewById(R.id.tvBody);
        TextView tvLikes = (TextView) findViewById(R.id.tvLikes);
        TextView tvRetweets = (TextView) findViewById(R.id.tvRetweet);
        ImageView ivContent = (ImageView) findViewById(R.id.ivContent);
        tvName.setText(tweet.getUser().getName());
        tvHandle.setText(tweet.getUser().getScreenName());
        Glide.with(this)
                .load(tweet.getUser().getProfileImageUrl()).placeholder(R.drawable.twitteregg).crossFade(400)
                .into(ivProfileImage);
        tvBody.setText(tweet.getBody());
        tvLikes.setText(Integer.toString(tweet.getLikes()));
        tvRetweets.setText(Integer.toString(tweet.getRetweets()));
        if (tweet.getMediaImageUrl() != null) {
            Glide.with(this)
                    .load(tweet.getMediaImageUrl()).crossFade(400).override(500, 500)
                    .into(ivContent);
        }
    }
}