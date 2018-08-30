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

import com.example.android.udacity_nanoand_bakeit.utilities.Utils;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import static android.support.constraint.Constraints.TAG;


public class MediaPlayerFragment extends Fragment {
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private ImageView mNoPlayerView;
    private MediaSessionCompat mMediaSession;

    private Uri mediaUri;
    private String mediaPath = "";
    private long videoStartTime = 0L;
    private boolean videoExists = false;
    private boolean imageExists = false;
    private boolean videoStartState = false;

    public static final String VIDEO_PATH = "video_path";
    public static final String VIDEO_TIME = "video_time";
    public static final String VIDEO_STATE = "video_state";

    public MediaPlayerFragment() {
    }

    public void setMediaUri(String url) {
        //
        // check end of url to detect image instead of video. If image, set flag
        Log.d(TAG, "MediaPlayerFragment.setVideUri: videopath=" + mediaPath);
        mediaPath = url;
        mediaUri = null;
        videoExists = false;
        imageExists = false;
        if (mediaPath.length() > 0) {
            mediaUri = Uri.parse(mediaPath);
            if (Utils.isImageUrl(url)){
                imageExists = true;
                Log.d(TAG, "MediaPlayerFragment.setVideUri: IMAGE");
            } else {
                videoExists = true;
                Log.d(TAG, "MediaPlayerFragment.setVideUri: VIDEO");
            }
        } else {
            Log.d(TAG, "MediaPlayerFragment.setVideUri: @@@@@ THIS STEP HAS NO VIDEO @@@@@@@@@");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "MediaPlayerFragment.onCreateView: ");
        // Load the saved state
        if (savedInstanceState != null) {
            setMediaUri(savedInstanceState.getString(VIDEO_PATH));
            videoStartTime = savedInstanceState.getLong(VIDEO_TIME);
            videoStartState = savedInstanceState.getBoolean(VIDEO_STATE);
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
        } else if (imageExists) {
            mNoPlayerView.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
            initializeImage();
        } else {
            mNoPlayerView.setVisibility(View.VISIBLE);
            mNoPlayerView.setImageResource(R.drawable.no_image);
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
        Log.d(TAG, "### mediaPLAYER ## initializePlayer:player=" + mExoPlayer + " mediaPath=" + mediaPath + " mediaUri=" + mediaUri);

// Bind the player to the view.
        mPlayerView.setPlayer(mExoPlayer);

// Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter);
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaUri);
// Prepare the player with the source.
        mExoPlayer.prepare(videoSource);
        mExoPlayer.seekTo(videoStartTime);
        mExoPlayer.setPlayWhenReady(videoStartState);

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
    public void releasePlayer(){
        if (mExoPlayer != null) {
            mExoPlayer.release();
        }
    }
    public void initializeImage(){
        Picasso.get()
                .load(mediaPath)
                .placeholder(R.drawable.blue_rect)
                .error(R.drawable.no_image)
                .into(mNoPlayerView);

    }
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putString(VIDEO_PATH, mediaPath);
        if (mExoPlayer != null){
            currentState.putLong(VIDEO_TIME, mExoPlayer.getCurrentPosition());
            currentState.putBoolean(VIDEO_STATE, mExoPlayer.getPlayWhenReady());
        } else {
            currentState.putLong(VIDEO_TIME, 0L);
            currentState.putBoolean(VIDEO_STATE,false);
        }


    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            //Before API level 24 release player resources early,
            releasePlayer();
        }
    }
    @Override
    public void onStop() {
        //API level 24+ release the player resources when the activity is no longer visible (onStop)
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
