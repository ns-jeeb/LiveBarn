package com.example.livebarnapp

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.livebarnapp.databinding.ActivityMediaBinding

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
class MediaActivity : AppCompatActivity() {
    private var playbakPostion = 0
    private val realTimeStreaming = "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8"
    private var binding : ActivityMediaBinding? = null

    private lateinit var mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_media)

        mediaController = MediaController(this@MediaActivity)
        binding?.videoView?.setOnPreparedListener {

            mediaController.setAnchorView(binding?.videoContainer)
            binding?.videoView?.setMediaController(mediaController)
            binding?.videoView?.seekTo(playbakPostion)
            binding?.videoView?.start()
        }
        binding?.videoView?.setOnInfoListener { player, what, exera ->
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                binding?.progressBar?.visibility = View.INVISIBLE
            true
        }
        binding?.imgClose?.setOnClickListener{
            binding?.videoView?.stopPlayback()
            this.finish()
        }

    }

    override fun onStart() {
        Log.e("onStart","this is called")
        super.onStart()
        var uri = Uri.parse(realTimeStreaming)
        binding?.videoView?.setVideoURI(uri)
        binding?.progressBar?.visibility = View.VISIBLE
    }

    override fun onPause() {
        Log.e("onPause","this is called")
        super.onPause()
        binding?.videoView?.pause()
        playbakPostion = binding?.videoView?.currentPosition!!
    }

    override fun onStop() {
        Log.e("onStop","this is called")
        binding?.videoView?.stopPlayback()

        super.onStop()
    }
}