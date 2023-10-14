package com.example.simpleplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.example.simpleplayer.player.PlayerSessionState
import com.example.simpleplayer.ui.theme.SimplePlayerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : ComponentActivity() {

    private val viewModel by viewModel<PlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimplePlayerTheme {

                val sessionState = viewModel
                    .playerSessionState
                    .collectAsState()


                PlayerScreen(
                    playerSessionState = sessionState
                )
            }
        }
    }
}


@Composable
fun PlayerScreen(playerSessionState: State<PlayerSessionState>) {
    val player: Player? by remember {
        derivedStateOf {
            when (val state = playerSessionState.value) {
                is PlayerSessionState.Connected -> state.player
                PlayerSessionState.NotConnected -> null
            }
        }
    }
    AndroidView(
        factory = {
            PlayerView(it)
        },
        update = {
            it.player = player
        },
        modifier = Modifier.fillMaxSize()
    )
}


