package com.example.simpleplayer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.media3.session.MediaController
import androidx.media3.ui.PlayerView
import com.example.simpleplayer.ui.theme.SimplePlayerTheme
import com.google.common.util.concurrent.ListenableFuture


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimplePlayerTheme {
                MainScreen(startPlayer = {
                    startActivity(Intent(this,PlayerActivity::class.java))
                })
            }
        }
    }

    @Composable
    fun MainScreen(startPlayer:()->Unit) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Button(onClick = startPlayer) {
                Text(text = "Start Player")
            }
        }
    }
}

