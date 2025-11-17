package com.sajjady.profilerlab.memory


import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import kotlinx.coroutines.delay

fun allocateBitmaps() {
    val bitmaps = mutableListOf<Bitmap>()
    repeat(80) {
        bitmaps += Bitmap.createBitmap(1000, 1000, Config.ARGB_8888)
    }
    // این لیست local است و بعد از خروج، GC می‌تواند آن را جمع کند
}

suspend fun shortLivedAllocations() {
    repeat(200) {
        val list = MutableList(50_000) { it }
        // کار بی‌معنی
        list.shuffle()
        // GC بعد از خروج scope
        delay(10)
    }
}