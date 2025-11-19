package com.sajjady.profilerlab.network

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.sajjady.profilerlab.info.ScenarioIds
import com.sajjady.profilerlab.ui.components.ScenarioActionRow

@Composable
fun NetworkScenariosScreen() {
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf("") }
    var pollingJob by remember { mutableStateOf<Job?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text("Network Profiler Scenarios", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        ScenarioActionRow(
            label = "Burst 20 requests",
            infoId = ScenarioIds.NETWORK_BURST,
            onAction = {
                scope.launch(Dispatchers.IO) {
                    try {
                        repeat(20) {
                            NetworkApi.service.getPosts()
                        }
                        status = "20 burst requests done."
                    } catch (t: Throwable) {
                        status = "Error: ${t.message}"
                    }
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        ScenarioActionRow(
            label = "Start 1-second polling",
            infoId = ScenarioIds.NETWORK_POLLING_START,
            onAction = {
                if (pollingJob == null) {
                    pollingJob = scope.launch(Dispatchers.IO) {
                        repeat(60) {
                            NetworkApi.service.getPosts()
                            delay(1000)
                        }
                    }
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        ScenarioActionRow(
            label = "Stop polling",
            infoId = ScenarioIds.NETWORK_POLLING_STOP,
            onAction = {
                pollingJob?.cancel()
                pollingJob = null
            }
        )

        Spacer(Modifier.height(16.dp))
        Text(status)
    }
}
