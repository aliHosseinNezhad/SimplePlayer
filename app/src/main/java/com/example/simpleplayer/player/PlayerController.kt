package com.example.simpleplayer.player

import androidx.media3.common.Player
import kotlinx.coroutines.flow.StateFlow

sealed class PlayerSessionState {
    data class Connected(val player: Player) : PlayerSessionState()
    object NotConnected : PlayerSessionState()
}


interface PlayerController {

    val isPlaying: StateFlow<Boolean>

    val sessionState:StateFlow<PlayerSessionState>

    fun play()

    fun pause()

    fun setPlayModel(model: PlayModel)

    fun release()

}