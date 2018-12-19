package no.matter.who.mediaitem.core

import java.util.*

class MediaRequest(val url: String) {

    var reqId: Int

    init {
        reqId = (Math.random() * 10).toInt()


    }
}