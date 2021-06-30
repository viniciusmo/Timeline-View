package com.vipulasri.timelineview.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

sealed class LineStyle {
  class Normal(
    val width: Dp = TimelineDefaults.LineWidth,
    val color: Color = TimelineDefaults.LineColor
  ) : LineStyle()

  class Dashed(
    val width: Dp = TimelineDefaults.LineWidth,
    val color: Color = TimelineDefaults.LineColor,
    val dashLength: Dp = TimelineDefaults.LineDashLength,
    val dashGap: Dp = TimelineDefaults.LineDashGap
  ) : LineStyle()
}

internal fun LineStyle.width(): Dp {
  return when (this) {
    is LineStyle.Normal -> this.width
    is LineStyle.Dashed -> this.width
  }
}

internal fun LineStyle.color(): Color {
  return when (this) {
    is LineStyle.Normal -> this.color
    is LineStyle.Dashed -> this.color
  }
}