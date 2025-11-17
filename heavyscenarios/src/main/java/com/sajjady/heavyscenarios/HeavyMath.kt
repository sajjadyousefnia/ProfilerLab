package com.sajjady.heavyscenarios


public class HeavyMath {
    fun calculatePiWithLeibniz(iterations: Int): Double {
        var acc = 0.0
        var sign = 1.0
        for (i in 0 until iterations) {
            val denom = 2 * i + 1
            acc += sign * (1.0 / denom)
            sign = -sign
        }
        return 4 * acc
    }
}
