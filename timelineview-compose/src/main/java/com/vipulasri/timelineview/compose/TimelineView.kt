package com.vipulasri.timelineview.compose

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp

@Composable
fun TimelineView(
  modifier: Modifier = Modifier,
  itemPosition: Int = -1,
  totalItems: Int = -1,
  marker: Drawable? = LocalContext.current.getDrawable(R.drawable.ic_vector_marker),
  markerSize: Dp = TimelineDefaults.MarkerSize,
  markerColor: Color = TimelineDefaults.MarkerColor,
  lineStyle: LineStyle = LineStyle.Normal()
) {
  DrawTimeline(modifier, itemPosition, totalItems, marker, markerSize, markerColor, lineStyle)
}

@Composable
private fun DrawTimeline(
  modifier: Modifier,
  itemPosition: Int,
  totalItems: Int,
  marker: Drawable?,
  markerSize: Dp,
  markerColor: Color,
  lineStyle: LineStyle = LineStyle.Dashed()
) {
  Canvas(
    modifier = modifier.defaultMinSize(
      minWidth = markerSize,
      minHeight = markerSize
    )
  ) {
    drawMarker(marker, markerSize, markerColor)
    drawLine(itemPosition, totalItems, markerSize, lineStyle)
  }
}

private fun DrawScope.drawMarker(
  marker: Drawable?,
  markerSize: Dp,
  markerColor: Color
) {
  drawIntoCanvas { canvas ->
    marker?.let {
      it.mutate()

      val width = this.size.width
      val height = this.size.height

      val left = (width.div(2f) - markerSize.value).toInt()
      val top = (height.div(2f) - markerSize.value).toInt()
      val right = (width.div(2f) + markerSize.value).toInt()
      val bottom = (width.div(2f) + markerSize.value).toInt()

      it.setBounds(left, top, right, bottom)
      it.setTint(markerColor.toArgb())
      it.draw(canvas.nativeCanvas)
    }
  }
}

private fun DrawScope.drawLine(
  itemPosition: Int,
  totalItems: Int,
  markerSize: Dp,
  lineStyle: LineStyle
) {
  when (itemPosition) {
    0 -> {
      drawEndLine(markerSize, lineStyle)
    }
    totalItems.minus(1) -> {
      drawStartLine(markerSize, lineStyle)
    }
    else -> {
      drawStartLine(markerSize, lineStyle)
      drawEndLine(markerSize, lineStyle)
    }
  }
}

private fun DrawScope.drawStartLine(markerSize: Dp, lineStyle: LineStyle) {
  val startOffset = this.center - Offset(0f, markerSize.value)
  val endOffset = Offset(this.center.x, 0f)
  drawLine(
    color = lineStyle.color(),
    start = startOffset,
    end = endOffset,
    strokeWidth = lineStyle.width().value,
    pathEffect = getPathEffect(lineStyle)
  )
}

private fun DrawScope.drawEndLine(markerSize: Dp, lineStyle: LineStyle) {
  val startOffset = this.center + Offset(0f, markerSize.value)
  val endOffset = Offset(this.center.x, this.size.height)
  drawLine(
    color = lineStyle.color(),
    start = startOffset,
    end = endOffset,
    strokeWidth = lineStyle.width().value,
    pathEffect = getPathEffect(lineStyle)
  )
}

private fun getPathEffect(lineStyle: LineStyle): PathEffect? {
  var pathEffect: PathEffect? = null

  if (lineStyle is LineStyle.Dashed) {
    val intervals = floatArrayOf(
      lineStyle.dashLength.value,
      lineStyle.dashLength.value + lineStyle.dashGap.value
    )
    pathEffect = PathEffect.dashPathEffect(intervals, 0f)
  }

  return pathEffect
}
