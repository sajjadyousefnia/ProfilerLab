package com.sajjady.profilerlab.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sajjady.profilerlab.info.ScenarioInfoActivity

@Composable
fun ScenarioActionRow(
    label: String,
    infoId: String,
    modifier: Modifier = Modifier,
    onAction: () -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = onAction
        ) {
            Text(label)
        }
        FilledTonalButton(onClick = { ScenarioInfoActivity.start(context, infoId) }) {
            Text(
                text = "ℹ",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
