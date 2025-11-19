package com.sajjady.profilerlab.info

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sajjady.profilerlab.ui.components.DirectionAwareText
import com.sajjady.profilerlab.ui.theme.ProfilerDayTheme

class ScenarioInfoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scenarioId = intent.getStringExtra(EXTRA_SCENARIO_ID)
        val info = ScenarioInfoRepository.getScenarioInfo(scenarioId)

        setContent {
            ProfilerDayTheme {
                ScenarioInfoScreen(
                    info = info,
                    onBack = { finish() }
                )
            }
        }
    }

    companion object {
        private const val EXTRA_SCENARIO_ID = "extra_scenario_id"

        fun start(context: Context, scenarioId: String) {
            val intent = Intent(context, ScenarioInfoActivity::class.java)
                .putExtra(EXTRA_SCENARIO_ID, scenarioId)
            context.startActivity(intent)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScenarioInfoScreen(
    info: ScenarioInfo?,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    DirectionAwareText(
                        text = info?.title ?: "Scenario guide",
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        DirectionAwareText("←")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        if (info == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DirectionAwareText(
                    text = "متن راهنما برای این سناریو موجود نیست.",
                    style = MaterialTheme.typography.bodyLarge
                )
                FilledTonalButton(onClick = onBack) {
                    DirectionAwareText("بازگشت")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item { SummaryCard(info) }
                if (info.bodyParagraphs.isNotEmpty()) {
                    item {
                        SectionCard(
                            title = "چرا مهم است؟",
                            lines = info.bodyParagraphs
                        )
                    }
                }
                if (info.usageSteps.isNotEmpty()) {
                    item {
                        SectionCard(
                            title = "مراحل پیشنهادی در Profiler",
                            lines = info.usageSteps.mapIndexed { index, step -> "${index + 1}. $step" }
                        )
                    }
                }
                if (info.profilerInsights.isNotEmpty()) {
                    item {
                        SectionCard(
                            title = "نکات تحلیلی",
                            lines = info.profilerInsights
                        )
                    }
                }
                if (info.links.isNotEmpty()) {
                    item {
                        LinksSection(info.links)
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(info: ScenarioInfo) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DirectionAwareText(
                text = info.summary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    lines: List<String>
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DirectionAwareText(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Divider()
            lines.forEach { line ->
                DirectionAwareText(
                    text = line,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun LinksSection(links: List<ScenarioLink>) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DirectionAwareText(
                text = "مطالعه بیشتر",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            links.forEach { link ->
                FilledTonalButton(onClick = { openExternalLink(context, link) }) {
                    DirectionAwareText(link.label)
                }
            }
        }
    }
}

private fun openExternalLink(context: Context, link: ScenarioLink) {
    val uri = Uri.parse(link.url)
    val preferredIntent = Intent(Intent.ACTION_VIEW, uri).apply {
        when (link.type) {
            ScenarioLinkType.CHROME -> setPackage("com.android.chrome")
            ScenarioLinkType.YOUTUBE -> setPackage("com.google.android.youtube")
        }
    }
    val fallbackIntent = Intent(Intent.ACTION_VIEW, uri)

    try {
        context.startActivity(preferredIntent)
    } catch (error: ActivityNotFoundException) {
        context.startActivity(fallbackIntent)
    }
}
