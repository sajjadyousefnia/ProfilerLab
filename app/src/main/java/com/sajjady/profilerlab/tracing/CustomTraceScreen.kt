package com.sajjady.profilerlab.tracing


import android.os.Debug
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import kotlinx.coroutines.launch

@Composable
fun CustomTraceScreen() {
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf("Idle") }

    Column(Modifier.padding(16.dp)) {
        Text("Custom Trace (Debug.startMethodTracing)", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            scope.launch(Dispatchers.Default) {
                status = "Recording trace..."
                Debug.startMethodTracing("custom_trace_demo")
                // کار سنگین
                var sum = 0L
                repeat(50_000_000) { sum += it }
                Debug.stopMethodTracing()
                status = "Trace saved as custom_trace_demo.trace"
            }
        }) {
            Text("Run traced heavy computation")
        }

        Spacer(Modifier.height(16.dp))
        Text(status)
    }
}
