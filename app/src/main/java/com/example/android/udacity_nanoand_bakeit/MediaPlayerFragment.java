package com.example.android.udacity_nanoand_bakeit;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.logging.Handler;

import static android.support.constraint.Constraints.TAG;


public class MediaPlayerFragment extends Fragment {
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private ImageView mNoPlayerView;
    private MediaSessionCompat mMediaSession;

    private Uri videoUri;
    private String videoPath = "";
    private long videoStartTime = 0L;
    private boolean videoExists = false;

    public static final String VIDEO_PATH = "video_path";
    public static final String VIDEO_TIME = "video_time";

    public MediaPlayerFragment() {
    }

    public void setVideoUri(String url) {
        videoPath = url;
        videoUri = null;
        videoExists = false;
        if (videoPath.length() > 0) {
            Log.d(TAG, "MediaPlayerFragment.setVideUri: videopath=" + videoPath);
            videoUri = Uri.parse(videoPath);
            videoExists = true;
        } else {
            Log.d(TAG, "MediaPlayerFragment.setVideUri: @@@@@ THIS STEP HAS NO VIDEO @@@@@@@@@");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Load the saved state
        if (savedInstanceState != null) {
            setVideoUri(savedInstanceState.getString(VIDEO_PATH));
            videoStartTime = savedInstanceState.getLong(VIDEO_TIME);
        }
        View rootView = inflater.inflate(R.layout.mediaplayer_view, container, false);
        mPlayerView = rootView.findViewById(R.id.tv_mediaplayer);
        mNoPlayerView = rootView.findViewById(R.id.iv_no_video);
        if (videoExists) {
            assert mPlayerView != null;
            mNoPlayerView.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
            Log.d(TAG, "MediaPlayerFragment.onCreateView: CREATING VIDEO PLAYER");

            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.blue_rect));
            initializePlayer();
        } else {
            mNoPlayerView.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
            Log.d(TAG, "MediaPlayerFragment.onCreateView: NO VIDEO - NO VIDEO PLAYER");
        }
        return rootView;
    }

    public void initializePlayer() {
        Context context = getContext();
        // 1. Create a default TrackSelector
        // Handler mainHandler = new Handler();
// Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
// 2. Create the player
        mExoPlayer =
                ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        Log.d(TAG, "### mediaPLAYER ## initializePlayer:player=" + mExoPlayer + " videoPath=" + videoPath + " videoUri=" + videoUri);

// Bind the player to the view.
        mPlayerView.setPlayer(mExoPlayer);

// Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter);
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
// Prepare the player with the source.
        mExoPlayer.prepare(videoSource);
        mExoPlayer.seekTo(videoStartTime);


        /*
        if (mSimpleExoPlayer ==null){
            DefaultTrackSelector defTrackSel = new DefaultTrackSelector();
            DefaultLoadControl defLoadCon = new DefaultLoadControl();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance( this, defTrackSel, defLoadCon);
            mPlayerView.setPlayer(mSimpleExoPlayer);
            //Prepare the Media
            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(this, userAgent), new DefaultExtractorsFactory(), null, null);
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
        */
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putString(VIDEO_PATH, videoPath);
        if (mExoPlayer != null){
            currentState.putLong(VIDEO_TIME, mExoPlayer.getCurrentPosition());
        } else {
            currentState.putLong(VIDEO_TIME, 0L);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mExoPlayer.release();
    }
}
