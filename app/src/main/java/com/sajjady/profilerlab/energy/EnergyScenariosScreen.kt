package com.sajjady.profilerlab.energy


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.sajjady.profilerlab.info.ScenarioIds
import com.sajjady.profilerlab.ui.components.ScenarioActionRow

@Composable
fun EnergyScenariosScreen() {
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Text("Energy & Background Scenarios", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        ScenarioActionRow(
            label = "Enqueue WakeLock worker (1 run)",
            infoId = ScenarioIds.ENERGY_WAKELOCK,
            onAction = {
                val req = OneTimeWorkRequestBuilder<DemoWakeLockWorker>()
                    .setInputData(workDataOf("dummy" to "value"))
                    .build()
                WorkManager.getInstance(context).enqueue(req)
            }
        )

        // اینجا می‌تونی TODO برای Location / AlarmManager بذاری
    }
}
