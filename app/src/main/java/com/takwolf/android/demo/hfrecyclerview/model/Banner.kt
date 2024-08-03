package com.takwolf.android.demo.hfrecyclerview.model

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.roundToInt
import kotlin.random.Random

data class Banner(
    val color: Int = randomColor(),
) {
    companion object {
        @ColorInt
        private fun randomColor(): Int {
            val red = (Random.nextDouble() * 255).roundToInt()
            val green = (Random.nextDouble() * 255).roundToInt()
            val blue = (Random.nextDouble() * 255).roundToInt()
            return Color.rgb(red, green, blue)
        }

        fun getList(size: Int = 5): List<Banner> {
            return List(size) {
                Banner()
            }
        }
    }
}
