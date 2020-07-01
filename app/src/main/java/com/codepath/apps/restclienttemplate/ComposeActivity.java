package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    public static final String TAG = "Compose";
    public static final int MAX_TWEET_LENGTH = 280;
    EditText etCompose;
    Button buttonTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        etCompose = findViewById(R.id.etCompose);
        buttonTweet = findViewById(R.id.buttonTweet);
        client = TwitterApplication.getRestClient(this);
        buttonTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetBody = etCompose.getText().toString();
                if(tweetBody.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Your Tweet cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tweetBody.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(getApplicationContext(), "Your Tweet is too long!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(getApplicationContext(), tweet, Toast.LENGTH_SHORT).show();
                client.postTweet(tweetBody, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "Tweet posted!");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Tweet: " + tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", tweet);
                            setResult(RESULT_OK,intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "Request failed.", throwable);
                    }
                });
            }
        });
    }
}