package com.codepath.apps.mysimpletweets.Clients;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "18riuDJzdhIjI9PRUiGviWgDg";       // Change this
	public static final String REST_CONSUMER_SECRET = "p8Q5jiRMrqc583VswWHThqOo3y7SvMIDzEKsHleQtFEDtkAJru"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)
	private SwipeRefreshLayout swipeContainer = null;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}






	//Method === ENDPOINT

	//HomeTimeLine - Gets us the home timeline
	//Get statuses/home_timeline.json
	//count=25
	//since_id = 1
	public void getHomeTimeLine(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		//execute request
		getClient().get(apiUrl,params,handler);
	}

	public void getMentionsTimeLine(JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 5);
		//execute request
		getClient().get(apiUrl,params,handler);
	}
	public void getUserTimeline (String screenName, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 5);
		params.put("screen_name", screenName); //declare screenName as string parameter
		getClient().get(apiUrl,params,handler);
	}
	public void getUserInfo(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl,null,handler);

	}

	public void postNewTweet(String tweet, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		getClient().post(apiUrl, params, handler);
	}
	public void postReplyTweet(String tweet, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		getClient().post(apiUrl, params, handler);
	}

	public void getSearchResults(String query, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("search/tweets.json");
		RequestParams params = new RequestParams();
		params.put("count", 5);
		params.put("q", query);
		getClient().get(apiUrl, params, handler);
	}

	public void getPopularSearchResults(String query, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("search/tweets.json");
		RequestParams params = new RequestParams();
		params.put("count", 5);
		params.put("q", query);
		params.put("result_type", "popular");
		getClient().get(apiUrl, params, handler);
	}
	public void postLike(long id, JsonHttpResponseHandler handler){
		String apiUrl = getApiUrl("favorites/create.json");
		RequestParams params =  new RequestParams();
		params.put ("id", Long.toString(id) );
		getClient().post(apiUrl, params, handler);
	}
	public void destroyLike(long id, JsonHttpResponseHandler handler){
		String apiUrl = getApiUrl("favorites/destroy.json");
		RequestParams params =  new RequestParams();
		params.put ("id", Long.toString(id) );
		getClient().post(apiUrl, params, handler);
	}


	public void retweetTweet(long id,JsonHttpResponseHandler handler){
		String apiUrl= getApiUrl("statuses/retweet/" + Long.toString(id) + ".json");
		getClient().post(apiUrl, null , handler);
		//https://api.twitter.com/1.1/  statuses/retweet/241259202004267009.json
	}
	public void unretweetTweet(long id,JsonHttpResponseHandler handler){
		String apiUrl= getApiUrl("statuses/unretweet/" + Long.toString(id) + ".json");
		getClient().post(apiUrl, null , handler);
		//https://api.twitter.com/1.1/  statuses/retweet/241259202004267009.json
	}
	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Defin the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}