package com.example.simpleplayer.player

import android.app.PendingIntent
import android.content.Intent
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.simpleplayer.PlayerActivity

class VideoPlayerService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    // If desired, validate the controller before returning the media library session
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession


    // Create your player and media library session in the onCreate lifecycle event
    override fun onCreate() {
        super.onCreate()

        val player = ExoPlayer.Builder(this)
            .setHandleAudioBecomingNoisy(true)
            .build()

        mediaSession = MediaSession.Builder(this, player)
            .setCallback(object : MediaSession.Callback {})
            .setSessionActivity(
                PendingIntent.getActivity(
                    applicationContext,
                    1,
                    Intent(applicationContext, PlayerActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            ).build()


    }

    // Remember to release the player and media library session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}