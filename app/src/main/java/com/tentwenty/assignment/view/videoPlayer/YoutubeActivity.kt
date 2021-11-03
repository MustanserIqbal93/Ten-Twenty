package com.tentwenty.assignment.view.videoPlayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.tentwenty.assignment.R
import kotlinx.android.synthetic.main.activity_youtube.*

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "YoutubeActivity"

    companion object {
        const val VIDEO_ID = "video_id"
    }

    private lateinit var YOUTUBE_VIDEO_ID: String
    private lateinit var youTubePlayer: YouTubePlayer
    private var isYouTubePlayerFullScreen : Boolean = false

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_youtube)

        YOUTUBE_VIDEO_ID = intent.getStringExtra(VIDEO_ID)!!

        playerView.initialize(getString(R.string.api_key), this)

        closeButton.setOnClickListener { onBackPressed() }
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        this.youTubePlayer = youTubePlayer!!
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer.setPlaybackEventListener(playbackEventListener)
        youTubePlayer.setOnFullscreenListener(fullScreenLitener)

        if (!wasRestored) {
            youTubePlayer.cueVideo(YOUTUBE_VIDEO_ID)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        val REQUEST_CODE = 0

        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the YoutubePlayer ($youTubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val fullScreenLitener = object : YouTubePlayer.OnFullscreenListener {
        override fun onFullscreen(p0: Boolean) {
            isYouTubePlayerFullScreen = p0
        }


    }
    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
        }

        override fun onStopped() {
        }

        override fun onPaused() {
        }
    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
        }

        override fun onLoaded(p0: String?) {
            youTubePlayer.play()
        }

        override fun onVideoEnded() {
            finish()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }

    override fun onBackPressed() {
        if (isYouTubePlayerFullScreen) {
            youTubePlayer.setFullscreen(false)
        } else {
            super.onBackPressed()
        }
    }
}