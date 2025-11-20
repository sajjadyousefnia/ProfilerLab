package com.sajjady.profilerlab.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sajjady.profilerlab.ui.components.DirectionAwareText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCpuClick: () -> Unit,
    onMemoryClick: () -> Unit,
    onNetworkClick: () -> Unit,
    onEnergyClick: () -> Unit,
    onTraceClick: () -> Unit,
) {
    val scenarios = listOf(
        HomeScenario(
            id = "cpu",
            titleEn = "CPU Profiler",
            titleFa = "پروفایل CPU",
            descriptionEn = "Freeze UI, heavy background work, and allocation storms to explore flame chart, top-down and system trace.",
            descriptionFa = "بلوک کردن UI، کارهای سنگین پس‌زمینه و Allocation سنگین برای بررسی Flame Chart، نمای Top-Down و System Trace.",
            onClick = onCpuClick
        ),
        HomeScenario(
            id = "memory",
            titleEn = "Memory Profiler",
            titleFa = "پروفایل حافظه",
            descriptionEn = "Activity/context leak, bitmap RAM usage, and short-lived allocations for GC analysis.",
            descriptionFa = "Leak از Activity/Context، مصرف RAM توسط Bitmap و تخصیص‌های کوتاه‌عمر برای تحلیل GC.",
            onClick = onMemoryClick
        ),
        HomeScenario(
            id = "network",
            titleEn = "Network Profiler",
            titleFa = "پروفایل شبکه",
            descriptionEn = "Burst traffic vs 1-second polling to recognize different HTTP traffic patterns.",
            descriptionFa = "ترافیک Burst در برابر Polling با فاصله‌ی ۱ ثانیه برای تشخیص الگوهای مختلف ترافیک HTTP.",
            onClick = onNetworkClick
        ),
        HomeScenario(
            id = "energy",
            titleEn = "Energy & Background",
            titleFa = "انرژی و پردازش پس‌زمینه",
            descriptionEn = "WakeLock demo and WorkManager jobs, inspected via system trace and Background Task Inspector.",
            descriptionFa = "دموی WakeLock و Jobهای WorkManager با مشاهده در System Trace و Background Task Inspector.",
            onClick = onEnergyClick
        ),
        HomeScenario(
            id = "trace",
            titleEn = "Custom Tracing",
            titleFa = "Trace سفارشی",
            descriptionEn = "Record .trace files with Debug.startMethodTracing and inspect them in Android Studio.",
            descriptionFa = "تولید فایل‌های .trace با Debug.startMethodTracing و تحلیل آن‌ها در Android Studio.",
            onClick = onTraceClick
        )
    )

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        DirectionAwareText(
                            text = "ProfilerLab",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        DirectionAwareText(
                            text = "Android Studio Profiler playground",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(scenarios) { item ->
                ScenarioCard(item)
            }
        }
    }
}

@Composable
private fun ScenarioCard(
    scenario: HomeScenario
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { scenario.onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DirectionAwareText(
                text = scenario.titleEn,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            DirectionAwareText(
                text = scenario.titleFa,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            DirectionAwareText(
                text = scenario.descriptionEn,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 6.dp)
            )
            DirectionAwareText(
                text = scenario.descriptionFa,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

private data class HomeScenario(
    val id: String,
    val titleEn: String,
    val titleFa: String,
    val descriptionEn: String,
    val descriptionFa: String,
    val onClick: () -> Unit
)
