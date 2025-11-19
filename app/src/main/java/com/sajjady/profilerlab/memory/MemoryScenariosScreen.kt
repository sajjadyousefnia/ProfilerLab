package com.sajjady.profilerlab.memory


import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.sajjady.profilerlab.info.ScenarioIds
import com.sajjady.profilerlab.ui.components.ScenarioActionRow

@Composable
fun MemoryScenariosScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text("Memory Profiler Scenarios", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        ScenarioActionRow(
            label = "Open LeakyActivity (Activity/Context leak)",
            infoId = ScenarioIds.MEMORY_LEAKY_ACTIVITY,
            onAction = {
                context.startActivity(Intent(context, LeakyActivity::class.java))
            }
        )

        Spacer(Modifier.height(8.dp))

        ScenarioActionRow(
            label = "Allocate many Bitmaps",
            infoId = ScenarioIds.MEMORY_BITMAP_PRESSURE,
            onAction = {
                scope.launch(Dispatchers.Default) {
                    allocateBitmaps()
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        ScenarioActionRow(
            label = "Short-lived allocations (GC churn)",
            infoId = ScenarioIds.MEMORY_SHORT_LIVED,
            onAction = {
                scope.launch(Dispatchers.Default) {
                    shortLivedAllocations()
                }
            }
        )
    }
}
