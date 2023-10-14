package com.example.simpleplayer

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleplayer.player.PlayModel
import com.example.simpleplayer.player.PlayerController
import com.example.simpleplayer.player.PlayerSessionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlayerViewModel(private val playerController: PlayerController) : ViewModel() {
    val playerSessionState = playerController.sessionState

    init {
        viewModelScope.launch {
            playerSessionState.collectLatest {
                when (it) {
                    is PlayerSessionState.Connected -> {
                        setPlayModel()
                        playerController.play()
                    }
                    PlayerSessionState.NotConnected -> {

                    }
                }
            }
        }
    }
    val file = "file:///android_asset/sample.mp4"
    val webUrl = "https://uploadkon.ir/uploads/a94d14_238ce0bcbfecf7312ce50c1c43e9fac9a651909890-480p.mp4"
    val webUrl2 = "https://caspian10.asset.aparat.com/aparat-video/8ce0bcbfecf7312ce50c1c43e9fac9a651909890-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImQxMGJlZDc4MmJiMmFjZDdiMDZiZjQ0ZDlhNTBkNTI1IiwiZXhwIjoxNjk3MzAzMzU4LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.KTl1_jMixSbL5_Gc2gVGoKw0oZLcCQSFo76DJbO-Ua8"
    private fun setPlayModel() {
        playerController.setPlayModel(
            PlayModel(
                "1",
                uri = Uri.parse(webUrl2),
                title = "movie",
                subtitle = "cartoon"
            )
        )
    }

    override fun onCleared() {
        playerController.release()
    }
}