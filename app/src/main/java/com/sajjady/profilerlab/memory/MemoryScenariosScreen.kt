package com.sajjady.profilerlab.memory


import android.content.Intent
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        Button(onClick = {
            context.startActivity(Intent(context, LeakyActivity::class.java))
        }) {
            Text("Open LeakyActivity (Activity/Context leak)")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            scope.launch(Dispatchers.Default) {
                allocateBitmaps()
            }
        }) {
            Text("Allocate many Bitmaps")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            scope.launch(Dispatchers.Default) {
                shortLivedAllocations()
            }
        }) {
            Text("Short-lived allocations (GC churn)")
        }
    }
}
