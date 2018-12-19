package no.matter.who.mediaitem

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri

//class MediaItem(val context: Context, val url: String) {
class MediaItem(val asset: MediaAsset) {

    constructor(context: Context, url: String) : this(MediaAsset(context, url))

    fun loadMediaItem() {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(asset.assetUrl)

        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        val fileName = Uri.parse(asset.assetUrl).lastPathSegment
        asset.loadAsset(fileName)
    }


}