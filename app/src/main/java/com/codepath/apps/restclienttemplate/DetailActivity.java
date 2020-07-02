package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    Tweet tweet;
    ImageView ivProfileImage;
    ImageView ivEmbed;
    TextView tvBody;
    TextView tvScreenName;
    TextView tvTime;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        ivProfileImage = binding.ivProfileImage;
        tvBody = binding.tvBody;
        tvScreenName = binding.tvScreenName;
        tvTime = binding.tvTime;
        ivEmbed = binding.ivEmbed;

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
    }
}