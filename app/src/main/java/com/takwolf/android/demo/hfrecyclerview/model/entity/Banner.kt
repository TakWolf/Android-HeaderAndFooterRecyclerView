package com.takwolf.android.demo.hfrecyclerview.model.entity

import android.graphics.Color
import androidx.annotation.ColorInt
import java.util.UUID
import kotlin.math.roundToInt
import kotlin.random.Random

data class Banner(
    val id: String,
    val color: Int,
) {
    companion object {
        @ColorInt
        private fun randomColor(): Int {
            val red = (Random.nextDouble() * 255).roundToInt()
            val green = (Random.nextDouble() * 255).roundToInt()
            val blue = (Random.nextDouble() * 255).roundToInt()
            return Color.rgb(red, green, blue)
        }

        fun new(): Banner {
            return Banner(UUID.randomUUID().toString(), randomColor())
        }
    }
}
