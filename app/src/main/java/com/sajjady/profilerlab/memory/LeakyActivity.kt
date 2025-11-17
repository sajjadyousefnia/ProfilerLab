package com.sajjady.profilerlab.memory

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sajjady.profilerlab.R

object LeakyHolder {
    var leakedContext: Context? = null
    val leakedBitmaps = mutableListOf<Bitmap>()
}

class LeakyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaky)

        // Leak عمدی:
        LeakyHolder.leakedContext = this

        findViewById<Button>(R.id.btn_allocate_big).setOnClickListener {
            // Bitmap بزرگ نگه می‌داریم تا heap dump جذاب شود
            val bmp = Bitmap.createBitmap(2000, 2000, Config.ARGB_8888)
            LeakyHolder.leakedBitmaps.add(bmp)
        }
    }
}