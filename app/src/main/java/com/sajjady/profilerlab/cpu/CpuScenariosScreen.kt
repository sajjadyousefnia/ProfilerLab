package com.sajjady.profilerlab.cpu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sajjady.heavyscenarios.HeavyMath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.sajjady.profilerlab.info.ScenarioIds
import com.sajjady.profilerlab.ui.components.ScenarioActionRow

@Composable
fun CpuScenariosScreen() {
    val scope = rememberCoroutineScope()
    val heavyMath = remember { HeavyMath() }
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Text("CPU Profiler Scenarios", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        ScenarioActionRow(
            label = "Freeze UI (Block Main Thread 2.5s)",
            infoId = ScenarioIds.CPU_FREEZE_UI,
            onAction = { blockMainThread(2500) }
        )

        Spacer(Modifier.height(8.dp))

        ScenarioActionRow(
            label = "Heavy CPU on Background (from :heavyscenarios)",
            infoId = ScenarioIds.CPU_BACKGROUND_LOAD,
            onAction = {
                scope.launch(Dispatchers.Default) {
                    heavyMath.calculatePiWithLeibniz(30_000_000)
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        ScenarioActionRow(
            label = "Allocation Storm (CPU + GC)",
            infoId = ScenarioIds.CPU_ALLOCATION_STORM,
            onAction = {
                scope.launch(Dispatchers.Default) {
                    allocationStorm()
                }
            }
        )
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
