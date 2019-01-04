package com.example.huypham.assigment3_fx00066_youtubeplaylist.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huypham.assigment3_fx00066_youtubeplaylist.R;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.model.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayYoutubeVideoActivity extends YouTubeBaseActivity implements OnInitializedListener {
    YouTubePlayerView youTubePlayerView;
    String KEY_BROWSE = "AIzaSyBFOYdKSAcey2-wS9_mGw0oeJsEf-26YZ8";
    TextView txtTitle,txtDescription;
    private String VIDEO_ID, TITLE_VIDEO, DESCRIPTION_VIDEO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_youtube_video);
        youTubePlayerView = findViewById(R.id.youtubePlayer);
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        youTubePlayerView.initialize(KEY_BROWSE,PlayYoutubeVideoActivity.this);
        Bundle bundle = getIntent().getExtras();
        Video video = (Video) bundle.getSerializable("Video");
        TITLE_VIDEO = video.getVideoTitle();
        VIDEO_ID = video.getVideoURL();
        DESCRIPTION_VIDEO = video.getVideoDescription();
        txtTitle.setText(TITLE_VIDEO);
        txtDescription.setText(DESCRIPTION_VIDEO);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.setShowFullscreenButton(true);
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        String error = "Không thể load video! Kiểm tra Internet và ứng dụng Youtube trên máy của bạn!";
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
