package no.matter.who.mediaitem

import android.content.Context
import no.matter.who.mediaitem.core.MediaRequest
import no.matter.who.mediaitem.core.MediaRequestQueue

class MediaAsset(val context: Context, url: String) {
    var assetUrl: String
    var tempDir: String
    private var request: MediaRequest

    init {
        this.assetUrl = url
        this.tempDir = context.applicationContext.filesDir.absolutePath

        request = MediaRequest(url)
    }

    fun loadAsset(fileName: String) {
        //MediaRequestQueue.addRequest(request)
    }

}