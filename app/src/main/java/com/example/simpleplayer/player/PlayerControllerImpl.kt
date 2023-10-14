package com.example.simpleplayer.player

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableStateFlow


class PlayerControllerImpl(context: Context) : PlayerController {

    private val sessionToken =
        SessionToken(context, ComponentName(context, VideoPlayerService::class.java))


    private val controllerFuture = MediaController.Builder(context, sessionToken)
        .setListener(object : MediaController.Listener {
            override fun onDisconnected(controller: MediaController) {
                sessionState.value = PlayerSessionState.NotConnected
            }
        }).buildAsync()

    private val mediaController get() = if (controllerFuture.isDone) controllerFuture.get() else null


    override val isPlaying = MutableStateFlow(false)

    override val sessionState:MutableStateFlow<PlayerSessionState> = MutableStateFlow(PlayerSessionState.NotConnected)

    init {
        controllerFuture.addListener({
            mediaController?.let {
                sessionState.value = PlayerSessionState.Connected(it)
                it.addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        this@PlayerControllerImpl.isPlaying.value = isPlaying
                    }
                })
            }
        }, MoreExecutors.directExecutor())
    }

    override fun play() {
        mediaController?.playWhenReady = true
    }

    override fun pause() {
        mediaController?.playWhenReady = false
    }

    override fun setPlayModel(model: PlayModel) {
        mediaController?.apply {
            setMediaItem(
                MediaItem.Builder()
                    .setUri(model.uri)
                    .setMediaId(model.id)
                    .setMediaMetadata(MediaMetadata.Builder()
                        .setTitle(model.title)
                        .setArtist(model.artist)
                        .setSubtitle(model.subtitle)
                        .build())
                    .build()
            )
            prepare()
        }
    }

    override fun release() {
        mediaController?.stop()
        MediaController.releaseFuture(controllerFuture)
    }
}