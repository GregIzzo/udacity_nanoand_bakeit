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

//import okhttp3.internal.Util;

public class MediaPlayerFragment extends Fragment {
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private MediaSessionCompat mMediaSession;

    private Uri videoUri;


    public MediaPlayerFragment(){
    }
    public void setVideoUri(Uri uri){
        videoUri = uri;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mediaplayer_view, container, false);
    //tv_mediaplayer
        mPlayerView = rootView.findViewById(R.id.tv_mediaplayer);
        Log.d(TAG, "*** MediaPlayerFragment.onCreateView: CREATING MEDIA PLAY FRAGMENT mPlayerView["+mPlayerView+"]");

        assert mPlayerView != null;
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.no_video));
        initializePlayer();
        return rootView;
    }

    public void initializePlayer( ) {
        Context context = getContext();
        // 1. Create a default TrackSelector
       // Handler mainHandler = new Handler();
// Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =new DefaultTrackSelector(videoTrackSelectionFactory);
// 2. Create the player
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        Log.d(TAG, "### PLAYER ## initializePlayer:player["+player+"] mPlayerView["+mPlayerView+"]");

// Bind the player to the view.
        mPlayerView.setPlayer(player);

// Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter);
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
// Prepare the player with the source.
        player.prepare(videoSource);


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

}
