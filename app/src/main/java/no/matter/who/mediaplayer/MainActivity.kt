package who.matter.no.mediaplayer

import android.graphics.Point
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.View
import android.webkit.DownloadListener
import android.widget.FrameLayout
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.jar.Attributes

class MainActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var mediaPlayer: MediaPlayer
    private var playbackPosition = 0
    //private val rtspUrl = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov"
    private val rtspUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val holder = surfaceView.holder
        holder.addCallback(this)
    }

    override fun onPause() {
        super.onPause()

        playbackPosition = mediaPlayer.currentPosition
    }

    override fun onStop() {
        mediaPlayer.stop()
        mediaPlayer.release()

        super.onStop()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        val surface = holder?.surface

        if (surface != null)
            setupMediaPlayer(surface)

        prepareMediaPlayer()
    }

    private fun createAudioAttributes(): AudioAttributes {
        val builder = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
        return builder.build()
    }

    private fun setupMediaPlayer(surface: Surface) {
        progressBar.visibility = View.VISIBLE
        mediaPlayer = MediaPlayer()
        mediaPlayer.setSurface(surface)

        val audioAttributes = createAudioAttributes()
        mediaPlayer.setAudioAttributes(audioAttributes)
/*
        val uri = Uri.parse(rtspUrl)
        try {
            mediaPlayer.setDataSource(this, uri)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
*/
        var dir = ContextCompat.getExternalFilesDirs(this.applicationContext, null)[0].absolutePath
        PRDownloader.download(rtspUrl, dir, "/bunny.mp4")
                .build()
                .setOnStartOrResumeListener {
//                    var file = getFileStreamPath(dir + "/bunny.mp4")
//                    mediaPlayer.setDataSource(file.outputStream().fd)
                    Log.d("TEST", "Start Or Resume")

                }.start(object: OnDownloadListener {
                    override fun onDownloadComplete() {
                        Log.d("TEST", "Complete")
                        //prepareMediaPlayer()
                    }

                    override fun onError(error: Error?) {

                    }
                })

    }

    private fun setSurfaceDimensions(player: MediaPlayer, width: Int, height: Int) {
        if (width > 0 && height > 0) {
            val aspectRatio = height.toFloat() / width.toFloat()
            val screenDimensions = Point()
            windowManager.defaultDisplay.getSize(screenDimensions)
            val surfaceWidth = screenDimensions.x
            val surfaceHeight = (surfaceWidth * aspectRatio).toInt()
            val params = FrameLayout.LayoutParams(surfaceWidth, surfaceHeight)

            surfaceView.layoutParams = params

            val holder = surfaceView.holder
            player.setDisplay(holder)
        }
    }
    private fun prepareMediaPlayer() {
        val dir = ContextCompat.getExternalFilesDirs(this.applicationContext, null)[0].absolutePath

        var file = getFileStreamPath(dir + "/bunny.mp4")
                    mediaPlayer.setDataSource(file.outputStream().fd)
        try {
            mediaPlayer.prepareAsync()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        mediaPlayer.setOnPreparedListener {
            progressBar.visibility = View.INVISIBLE
            mediaPlayer.seekTo(playbackPosition)
            mediaPlayer.start()
        }
        mediaPlayer.setOnVideoSizeChangedListener { player, width, height ->
            setSurfaceDimensions(player, width, height)
        }
    }
}
