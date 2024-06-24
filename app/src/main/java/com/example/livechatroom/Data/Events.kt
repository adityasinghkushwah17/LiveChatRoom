package com.example.livechatroom.Data

open class Events< out T>(val Content: T) {
    var hasBeenhandled=false
    fun getContentornull(): T? {
        return if (hasBeenhandled) {
            null
        } else {
            hasBeenhandled = true
            Content
    }
}
}