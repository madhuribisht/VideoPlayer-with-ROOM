package com.example.videoapp.view.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.example.videoapp.R;
import com.example.videoapp.constant.BaseConstant;
import com.example.videoapp.utils.CommonUtil;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class VideoDetailsActivity extends AppCompatActivity {
    private PlayerView playerView;
    private Toolbar toolbar;
    private SimpleExoPlayer player;
    private MediaItem mediaItem;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private String videoURL, videoThumbnail, songName, artistName, date;
    private Double price;
    private ImageView toolbarImg;
    private TextView trackNameTV, artistNameTV, releaseDateTV, trackPriceTV;
    private Format format = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        try {
            initViews();
            getVideoData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() throws Exception {
        try {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            playerView = findViewById(R.id.exoplayer);
            toolbarImg = findViewById(R.id.toolbar_image);
            trackNameTV = findViewById(R.id.track_name);
            artistNameTV = findViewById(R.id.artist_name);
            releaseDateTV = findViewById(R.id.release_date);
            trackPriceTV = findViewById(R.id.song_price);
            player = new SimpleExoPlayer.Builder(this).build();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void getVideoData() throws Exception {
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                videoURL = extras.getString(BaseConstant.VIDEO_URL);
                videoThumbnail = extras.getString(BaseConstant.VIDEO_THUMBNAIL);
                songName = extras.getString(BaseConstant.SONG_NAME);
                artistName = extras.getString(BaseConstant.ARTIST_NAME);
                date = extras.getString(BaseConstant.DATE);
                price = extras.getDouble(BaseConstant.PRICE);
                if (!CommonUtil.isNullOrEmpty(songName)) {
                    toolbar.setTitle(songName);
                }
                else{
                    toolbar.setTitle(getString(R.string.video_details));
                }
                if (!CommonUtil.isNullOrEmpty(videoThumbnail)) {
                    Glide.with(getApplicationContext()).load(videoThumbnail)
                            .placeholder(R.drawable.no_image)
                            .error(R.drawable.no_image)
                            .into(toolbarImg);
                }
                if (!CommonUtil.isNullOrEmpty(songName)) {
                    trackNameTV.setText(songName);
                }
                if (!CommonUtil.isNullOrEmpty(artistName)) {
                    artistNameTV.setText(artistName);
                }
                if (!CommonUtil.isNullOrEmpty(date)) {
                    releaseDateTV.setText(date);
                }
                if (!CommonUtil.isNullOrEmpty(String.valueOf(price))) {
                    trackPriceTV.setText(format.format(new BigDecimal(String.valueOf(price))));
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void initializePlayer() throws Exception {
        try {
            playerView.setPlayer(player);
            if (!CommonUtil.isNullOrEmpty(videoURL)) {
                mediaItem = MediaItem.fromUri(videoURL);
                player.setMediaItem(mediaItem);
            }
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            player.prepare();
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    private void releasePlayer() {
        try {
            if (player != null) {
                playbackPosition = player.getCurrentPosition();
                currentWindow = player.getCurrentWindowIndex();
                playWhenReady = player.getPlayWhenReady();
                player.release();
                player.setPlayWhenReady(false);
                player = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        try {
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            initializePlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        try {
            initializePlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
        }
        releasePlayer();
    }
      @Override
      protected void onDestroy() {
          super.onDestroy();
          releasePlayer();
      }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
