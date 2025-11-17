package com.sajjady.profilerlab.navigation


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sajjady.profilerlab.cpu.CpuScenariosScreen
import com.sajjady.profilerlab.energy.EnergyScenariosScreen
import com.sajjady.profilerlab.memory.MemoryScenariosScreen
import com.sajjady.profilerlab.network.NetworkScenariosScreen
import com.sajjady.profilerlab.tracing.CustomTraceScreen
import com.sajjady.profilerlab.ui.HomeScreen

object Routes {
    const val HOME = "home"
    const val CPU = "cpu"
    const val MEMORY = "memory"
    const val NETWORK = "network"
    const val ENERGY = "energy"
    const val TRACE = "trace"
}

@Composable
fun ProfilerNavHost() {
    val navController = rememberNavController()

    MaterialTheme(

    ) {
        NavHost(navController = navController, startDestination = Routes.HOME) {
            composable(Routes.HOME) {
                HomeScreen(
                    onCpuClick = { navController.navigate(Routes.CPU) },
                    onMemoryClick = { navController.navigate(Routes.MEMORY) },
                    onNetworkClick = { navController.navigate(Routes.NETWORK) },
                    onEnergyClick = { navController.navigate(Routes.ENERGY) },
                    onTraceClick = { navController.navigate(Routes.TRACE) },
                )
            }
            composable(Routes.CPU) { CpuScenariosScreen() }
            composable(Routes.MEMORY) { MemoryScenariosScreen() }
            composable(Routes.NETWORK) { NetworkScenariosScreen() }
            composable(Routes.ENERGY) { EnergyScenariosScreen() }
            composable(Routes.TRACE) { CustomTraceScreen() }
        }
    }
}