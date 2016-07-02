package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.Clients.TwitterClient;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TimeFormatter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class TweetDetailActivity extends AppCompatActivity {
    private TextView tvClassLikes;
    private TextView tvClassRetweets;
    private Boolean liked = false;
    private Boolean retweeted = false;
    private TwitterClient client;
    private ImageView ivLikeIcon;
    private ImageView ivRetweetIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        final Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tweet.getUser().getScreenName()

        );
        client= TwitterApplication.getRestClient();

        String chekc = tweet.getUser().getScreenName();
        toolbar.setTitle(tweet.getUser().getScreenName());
        TextView tvName = (TextView) findViewById(R.id.tvScreenName);
        TextView tvHandle = (TextView) findViewById(R.id.tvHandle);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvBody = (TextView) findViewById(R.id.tvBody);
        TextView tvLikes = (TextView) findViewById(R.id.tvLikes);
        ImageView replyIcon = (ImageView) findViewById(R.id.iv_reply);
        replyIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
                i.putExtra("tweet",Parcels.wrap(tweet));
                startActivity(i);



                //yOU'RE RIGHT HERE TRYING TO MAKE A REPLY


            }
        });
        ivLikeIcon = (ImageView) findViewById(R.id.ivLike);
        ivRetweetIcon = (ImageView) findViewById(R.id.ivRetweet);
        tvClassLikes = tvLikes;

        TextView tvRetweets = (TextView) findViewById(R.id.tvRetweet);
        tvClassRetweets = tvRetweets;
        ImageView ivContent = (ImageView) findViewById(R.id.ivContent);
        TextView tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        ImageView ivLike = (ImageView) findViewById(R.id.ivLike);


        ivRetweetIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                retweeted = !retweeted;
                long id = tweet.getUid();
                //set up stuff
                if(retweeted==true) {


                    client.retweetTweet(id, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            //gotta change classlikes to a classretweets
                            tvClassRetweets.setText(Integer.toString(tweet.getRetweets() + 1));
                            Toast.makeText(getApplicationContext(), "Retweeted", Toast.LENGTH_LONG).show();
                            Glide.with(getApplicationContext())
                                    .load(R.mipmap.ic_retweetred).crossFade(1000)
                                    .into(ivRetweetIcon);

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "Check Connection", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else if(retweeted==false){
                    client.unretweetTweet(id, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            tvClassRetweets.setText(Integer.toString(tweet.getRetweets()));
                            Toast.makeText(getApplicationContext(), "Unretweeted", Toast.LENGTH_LONG).show();
                            Glide.with(getApplicationContext())
                                    .load(R.mipmap.ic_retweet).crossFade(400)
                                    .into(ivRetweetIcon);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "Check Connection", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });


        ivLike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                liked = !liked;

                long id = tweet.getUid();
                if(liked==true) {


                    client.postLike(id, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            tvClassLikes.setText(Integer.toString(tweet.getLikes() + 1));
                            Toast.makeText(getApplicationContext(), "Post Liked", Toast.LENGTH_LONG).show();
                            Glide.with(getApplicationContext())
                                    .load(R.mipmap.ic_redheart).crossFade(1000)
                                    .into(ivLikeIcon);

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "Check Connection", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else if(liked==false){
                    client.destroyLike(id, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            tvClassLikes.setText(Integer.toString(tweet.getLikes()));
                            Toast.makeText(getApplicationContext(), "Post Unliked", Toast.LENGTH_LONG).show();
                            Glide.with(getApplicationContext())
                                    .load(R.mipmap.ic_heart).crossFade(400)
                                    .into(ivLikeIcon);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "Check Connection", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
        tvTimeStamp.setText(TimeFormatter.getTimeStamp(tweet.getCreatedAt()));
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
