package com.codepath.apps.mysimpletweets.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.Activities.ProfileActivity;
import com.codepath.apps.mysimpletweets.Activities.TweetDetailActivity;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TimeFormatter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

//taking the tweet objects and turning them into views displayed in the list
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    private Context myContext;

    public TweetArrayAdapter(Context context, List<Tweet> tweets) {

        super(context, android.R.layout.simple_list_item_1, tweets);
        this.myContext = context;

    }

    //override and setup custom template

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1.Get the tweet
        final Tweet tweet = getItem(position);
        //2.Find or inflate the template
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
        }
        //3. Find the subviews to fill with data in the template
        final ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "ImageView clicked for the row = ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("user", Parcels.wrap(tweet.getUser()));
                myContext.startActivity(i);
            }
        });
        TextView tvHandle = (TextView) convertView.findViewById(R.id.tvHandle);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        final TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTimeDifference = (TextView) convertView.findViewById(R.id.tvTimeDifference);
        //4. Populate data into the subviews
        tvTimeDifference.setText(TimeFormatter.getTimeDifference(tweet.getCreatedAt()));
        tvUserName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvHandle.setText(tweet.getUser().getScreenName()) ;
        Glide.with(getContext())
               .load(tweet.getUser().getProfileImageUrl()).crossFade(400)
               .into(ivProfileImage);
     //  Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(myContext, TweetDetailActivity.class);

                i.putExtra("tweet", Parcels.wrap(tweet));
                Pair<View, String> p1 = Pair.create((View)ivProfileImage, "userImage");
                //Pair<View, String> p2 = Pair.create(vPalette, "palette");
                //Pair<View, String> p2 = Pair.create((View)tvBody, "tweetText");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) myContext, p1);
                myContext.startActivity(i, options.toBundle());

            }

        });


        return convertView;
    }
}
