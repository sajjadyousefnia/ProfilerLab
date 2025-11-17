package com.sajjady.profilerlab.cpu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sajjady.heavyscenarios.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CpuScenariosScreen() {
    val scope = rememberCoroutineScope()
    val heavyMath = remember { HeavyMath() }
    MaterialTheme() {
        Column(
            Modifier
                .padding(16.dp)
        ) {
            Text("CPU Profiler Scenarios", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))

            Button(onClick = { blockMainThread(2500) }) {
                Text("Freeze UI (Block Main Thread 2.5s)")
            }

            Spacer(Modifier.height(8.dp))

            Button(onClick = {
                scope.launch(Dispatchers.Default) {
                    heavyMath.calculatePiWithLeibniz(30_000_000)
                }
            }) {
                Text("Heavy CPU on Background (from :heavyscenarios)")
            }

            Spacer(Modifier.height(8.dp))

            Button(onClick = {
                scope.launch(Dispatchers.Default) {
                    allocationStorm()
                }
            }) {
                Text("Allocation Storm (CPU + GC)")
            }
        }
    }
}

private fun blockMainThread(durationMs: Long) {
    val end = System.currentTimeMillis() + durationMs
    // Busy loop روی Main Thread → Jank واضح
    while (System.currentTimeMillis() < end) {
        // کار بی‌معنی
        Math.sqrt(1234.5678)
    }
}

private fun allocationStorm() {
    repeat(2000) {
        val list = MutableList(10_000) { it.toString() }
        // list بلافاصله out of scope می‌شود → GC زیاد
    }
}