package com.codycao.jukebox

import android.content.Context
import android.media.MediaPlayer

object PLayer {
    lateinit var player: MediaPlayer
        private set

    fun play(context: Context, resourceId: Int){
        var source: Int = when(resourceId){
            R.id.radio_song1 -> R.raw.track1
            R.id.radio_song2 -> R.raw.track2
            R.id.radio_song3 -> R.raw.track3
            else -> {0}
        }
        player = MediaPlayer.create(context, source)
        player.start()
    }

    fun seek(position: Int){
        if(player != null)
            player.seekTo(position * player.duration / 100)
    }

    fun stop(){
        player.stop()
    }
}