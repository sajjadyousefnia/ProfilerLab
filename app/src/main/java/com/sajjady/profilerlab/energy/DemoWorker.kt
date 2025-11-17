package com.sajjady.profilerlab.energy


import android.content.Context
import android.os.PowerManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class DemoWakeLockWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val pm = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = pm.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "ProfilerLab:DemoWakeLock"
        )

        wakeLock.acquire(10_000) // حداکثر ۱۰ ثانیه
        try {
            // کار ساختگی
            delay(5000)
        } finally {
            if (wakeLock.isHeld) wakeLock.release()
        }

        return Result.success()
    }
}