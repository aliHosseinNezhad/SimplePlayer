package com.example.simpleplayer.player

import android.net.Uri

data class PlayModel(
    val id: String,
    val uri: Uri,
    val title: String,
    val subtitle: String,
    val artist:String = "artist"
)