package com.example.adamassignment1

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    private var player: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this,R.raw.music)
        player?.isLooping= true
        player?.start()
        return START_STICKY
    }

    override fun onDestroy() {
        player?.stop()
        player?.release()
        super.onDestroy()
    }
}