package com.vipulasri.timelineview.compose

import androidx.compose.ui.graphics.drawscope.Stroke

sealed class LineStyle {
  class Normal(val width: Float = Stroke.HairlineWidth) : LineStyle()
  class Dashed(
    val width: Float = Stroke.HairlineWidth,
    val dashLength: Float = 5f,
    val dashGap: Float = 5f
  ) : LineStyle()
}

fun LineStyle.width(): Float {
  return when (this) {
    is LineStyle.Normal -> this.width
    is LineStyle.Dashed -> this.width
  }
}