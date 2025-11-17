package com.sajjady.profilerlab.energy


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf

@Composable
fun EnergyScenariosScreen() {
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text("Energy & Background Scenarios", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            val req = OneTimeWorkRequestBuilder<DemoWakeLockWorker>()
                .setInputData(workDataOf("dummy" to "value"))
                .build()
            WorkManager.getInstance(context).enqueue(req)
        }) {
            Text("Enqueue WakeLock worker (1 run)")
        }

        // اینجا می‌تونی TODO برای Location / AlarmManager بذاری
    }
}
