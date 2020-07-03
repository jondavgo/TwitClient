package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    TwitterClient client;
    Tweet tweet;
    boolean liked;
    boolean retweeted;
    ImageView ivProfileImage;
    ImageView ivEmbed;
    TextView tvBody;
    TextView tvScreenName;
    TextView tvTime;
    ImageView ivHeart;
    ImageView ivRT;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        client = TwitterApplication.getRestClient(this);
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        ivProfileImage = binding.ivProfileImage;
        tvBody = binding.tvBody;
        tvScreenName = binding.tvScreenName;
        tvTime = binding.tvTime;
        ivEmbed = binding.ivEmbed;
        ivHeart = binding.ivLike;
        ivRT = binding.ivRT;
        tvBody.setText(tweet.body);
        tvScreenName.setText(tweet.user.name);
        tvTime.setText(TweetsAdapter.getRelativeTimeAgo(tweet.time));
        Glide.with(this)
                .load(tweet.user.imageURL)
                .transform(new CircleCrop())
                .into(ivProfileImage);
        if(!tweet.embedURL.equals("")){
            ivEmbed.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(tweet.embedURL)
                    .transform(new RoundedCorners(30))
                    .into(ivEmbed);
        } else {
            ivEmbed.setVisibility(View.GONE);
        }
        client.checkStatus(tweet.id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject object = json.jsonObject;
                try {
                    liked = object.getBoolean("favorited");
                    retweeted = object.getBoolean("retweeted");
                    Log.i("Details", "Success! Liked: " + liked + " Retweeted: " + retweeted);
                    fixHeart();
                    fixRT();
                } catch (JSONException e) {
                    Log.e("Details", "JSON Exception", e);
                }
            }


            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("Details", "Response Failure");
            }
        });
        ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.Like(liked, tweet.id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i("Details", "Like success");
                        liked = !liked;
                        fixHeart();
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e("Details", "Like Failure");
                        failToast();
                    }
                });
            }
        });
        ivRT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.Retweet(retweeted, tweet.id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i("Details", "Retweeted!");
                        retweeted = !retweeted;
                        fixRT();
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e("Details", "Not Retweeted!");
                        failToast();
                    }
                });
            }
        });
    }

    private void failToast(){
        Toast.makeText(this, "Unable to do that! :(", Toast.LENGTH_SHORT).show();
    }
    private void fixHeart(){
        if(liked){
            Glide.with(this).load(R.drawable.ic_vector_heart).into(ivHeart);
        } else {
            Glide.with(this).load(R.drawable.ic_vector_heart_stroke).into(ivHeart);
        }
    }

    private void fixRT(){
        if(retweeted){
            Glide.with(this).load(R.drawable.ic_vector_retweet).into(ivRT);
        } else {
            Glide.with(this).load(R.drawable.ic_vector_retweet_stroke).into(ivRT);
        }
    }
}