package com.vipulasri.timelineview.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TimelineView(
  modifier: Modifier = Modifier,
  position: Int = -1,
  size: Int = -1,
  marker: Painter = painterResource(id = R.drawable.ic_vector_marker),
  markerSize: Dp = 20.dp,
  lineStyle: LineStyle = LineStyle.Dashed()
) {
  DrawTimeline(modifier, position, size, marker, markerSize, lineStyle)
}

@Composable
private fun DrawTimeline(
  modifier: Modifier,
  position: Int,
  size: Int,
  marker: Painter,
  markerSize: Dp,
  lineStyle: LineStyle = LineStyle.Dashed()
) {
  Canvas(
    modifier = modifier.defaultMinSize(
      minWidth = markerSize,
      minHeight = markerSize
    )
  ) {
    drawCircle(Color.Blue, radius = markerSize.value)
    when (position) {
      0 -> {
        drawEndLine(markerSize, lineStyle)
      }
      size.minus(1) -> {
        drawStartLine(markerSize, lineStyle)
      }
      else -> {
        drawStartLine(markerSize, lineStyle)
        drawEndLine(markerSize, lineStyle)
      }
    }
  }
}

private fun DrawScope.drawStartLine(markerSize: Dp, lineStyle: LineStyle) {
  val startOffset = this.center - Offset(0f, markerSize.value)
  val endOffset = Offset(this.center.x, 0f)
  drawLine(
    color = Color.Black,
    start = startOffset,
    end = endOffset,
    strokeWidth = lineStyle.width(),
    pathEffect = getPathEffect(lineStyle)
  )
}

private fun DrawScope.drawEndLine(markerSize: Dp, lineStyle: LineStyle) {
  val startOffset = this.center + Offset(0f, markerSize.value)
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
