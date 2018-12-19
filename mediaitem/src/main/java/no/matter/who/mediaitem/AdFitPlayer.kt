package no.matter.who.mediaitem

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import no.matter.who.mediaitem.core.MediaRequestQueue

class AdFitPlayer: MediaPlayer() {
//    private var mediaRequestQueue: MediaRequestQueue;
//
//    init {
//        mediaRequestQueue = MediaRequestQueue()
//    }

    fun setMediaItem(mediaItem: MediaItem) {
        //mediaItem.loadAsset()
        mediaItem.loadMediaItem()
    }

}