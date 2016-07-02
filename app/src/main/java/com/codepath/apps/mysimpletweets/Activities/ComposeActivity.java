package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView mTextView;
    private EditText mEditText ;
    private Tweet replyTweet = null;
    EditText etTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        replyTweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        client = TwitterApplication.getRestClient();
        mEditText = (EditText) findViewById(R.id.etTweet) ;
        mTextView = (TextView) findViewById(R.id.tvCount);
        etTweet = (EditText) findViewById(R.id.etTweet);
        mEditText.addTextChangedListener(mTextEditorWatcher);

        if (replyTweet!= null){
            etTweet.setText(replyTweet.getUser().getScreenName());
        }


    }


    public void onSubmitTweet(View view) {
        String status =  etTweet.getText().toString();
        if (replyTweet == null) {
            client.postNewTweet(status, new JsonHttpResponseHandler() {
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
        else if(replyTweet!= null){
            client.postNewTweet(replyTweet.getUid(),status, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("Success", response.toString());
                    finish();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("Success", errorResponse.toString());
                    Toast.makeText(getApplicationContext(),"Reply was not posted",  Toast.LENGTH_SHORT);
                    finish();
;                }

            });
        }

    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            mTextView.setText(String.valueOf(140-s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };
}
