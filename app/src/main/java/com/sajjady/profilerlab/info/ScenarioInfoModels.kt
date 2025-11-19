package com.sajjady.profilerlab.info

/**
 * Shared models that describe the detailed documentation we show inside [ScenarioInfoActivity].
 */
data class ScenarioInfo(
    val id: String,
    val title: String,
    val summary: String,
    val bodyParagraphs: List<String>,
    val usageSteps: List<String>,
    val profilerInsights: List<String>,
    val links: List<ScenarioLink>
)

data class ScenarioLink(
    val label: String,
    val url: String,
    val type: ScenarioLinkType
)

enum class ScenarioLinkType {
    CHROME,
    YOUTUBE
}

object ScenarioIds {
    const val CPU_FREEZE_UI = "cpu_freeze_ui"
    const val CPU_BACKGROUND_LOAD = "cpu_background_load"
    const val CPU_ALLOCATION_STORM = "cpu_allocation_storm"
    const val MEMORY_LEAKY_ACTIVITY = "memory_leaky_activity"
    const val MEMORY_BITMAP_PRESSURE = "memory_bitmap_pressure"
    const val MEMORY_SHORT_LIVED = "memory_short_lived"
    const val NETWORK_BURST = "network_burst"
    const val NETWORK_POLLING_START = "network_polling_start"
    const val NETWORK_POLLING_STOP = "network_polling_stop"
    const val ENERGY_WAKELOCK = "energy_wakelock"
    const val TRACE_CUSTOM = "trace_custom"
}
