package com.vipulasri.timelineview.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun TimelineView(
  position: Int = -1,
  size: Int = -1,
  lineStyle: LineStyle = LineStyle.Dashed()
) {
  DrawTimeline(position, size, lineStyle)
}

@Composable
private fun DrawTimeline(
  position: Int,
  size: Int,
  lineStyle: LineStyle = LineStyle.Dashed()
) {
  Canvas(modifier = Modifier.size(100.dp)) {
    drawCircle(Color.Blue, radius = 20f)
    when (position) {
      0 -> {
        drawEndLine(lineStyle)
      }
      size.minus(1) -> {
        drawStartLine(lineStyle)
      }
      else -> {
        drawStartLine(lineStyle)
        drawEndLine(lineStyle)
      }
    }
  }
}

private fun DrawScope.drawStartLine(lineStyle: LineStyle) {
  val startOffset = this.center - Offset(0f, 20f)
  val endOffset = Offset(this.center.x, 0f)
  drawLine(
    color = Color.Black,
    start = startOffset,
    end = endOffset,
    strokeWidth = lineStyle.width(),
    pathEffect = getPathEffect(lineStyle)
  )
}

private fun DrawScope.drawEndLine(lineStyle: LineStyle) {
  val startOffset = this.center + Offset(0f, 20f)
  val endOffset = Offset(this.center.x, this.size.height)
  drawLine(
    color = Color.Black,
    start = startOffset,
    end = endOffset,
    strokeWidth = lineStyle.width(),
    pathEffect = getPathEffect(lineStyle)
  )
}

private fun getPathEffect(lineStyle: LineStyle): PathEffect? {
  var pathEffect: PathEffect? = null

  if (lineStyle is LineStyle.Dashed) {
    val intervals = floatArrayOf(
      lineStyle.dashLength,
      lineStyle.dashLength + lineStyle.dashGap
    )
    pathEffect = PathEffect.dashPathEffect(intervals, 0f)
  }

  return pathEffect
}
