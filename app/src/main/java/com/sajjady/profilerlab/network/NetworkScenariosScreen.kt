package com.sajjady.profilerlab.network

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

        Button(onClick = {
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
        }) {
            Text("Burst 20 requests")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            if (pollingJob == null) {
                pollingJob = scope.launch(Dispatchers.IO) {
                    repeat(60) {
                        NetworkApi.service.getPosts()
                        delay(1000)
                    }
                }
            }
        }) {
            Text("Start 1-second polling")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            pollingJob?.cancel()
            pollingJob = null
        }) {
            Text("Stop polling")
        }

        Spacer(Modifier.height(16.dp))
        Text(status)
    }
}
