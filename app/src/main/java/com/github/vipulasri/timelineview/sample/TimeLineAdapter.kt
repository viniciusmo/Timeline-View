package com.github.vipulasri.timelineview.sample

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineBinding
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineHorizontalBinding
import com.github.vipulasri.timelineview.sample.extentions.formatDateTime
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.model.TimelineAttributes
import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils

/**
 * Created by Vipul Asri on 05-12-2015.
 */

class TimeLineAdapter(
  private val feedList: List<TimeLineModel>,
  private var attributes: TimelineAttributes
) : RecyclerView.Adapter<TimeLineAdapter.BaseTimeLineViewHolder>() {

  override fun getItemViewType(position: Int): Int {
    return TimelineView.getTimeLineViewType(position, itemCount)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTimeLineViewHolder {

    return if (isHorizontal()) {
      TimeLineHorizontalViewHolder(
        ItemTimelineHorizontalBinding.inflate(
          LayoutInflater.from(parent.context)
        ),
        viewType
      )
    } else {
      TimeLineViewHolder(
        ItemTimelineBinding.inflate(
          LayoutInflater.from(parent.context)
        ),
        viewType
      )
    }
  }

  override fun onBindViewHolder(holder: BaseTimeLineViewHolder, position: Int) {
    val timeLineModel = feedList[position]
    holder.bind(timeLineModel)
  }

  override fun getItemCount() = feedList.size

  private fun isHorizontal() = attributes.orientation == Orientation.HORIZONTAL

  inner class TimeLineViewHolder(
    private val binding: ItemTimelineBinding,
    viewType: Int
  ) : BaseTimeLineViewHolder(binding, viewType, attributes) {

    init {
      binding.timeline.setAttributes()
    }

    override fun bind(data: TimeLineModel) {
      binding.run {
        timeline.bindMarker(data)
        textTimelineDate.bindDate(data)
        textTimelineTitle.bindMessage(data)
      }
    }
  }

  inner class TimeLineHorizontalViewHolder(
    private val binding: ItemTimelineHorizontalBinding,
    viewType: Int
  ) : BaseTimeLineViewHolder(binding, viewType, attributes) {

    init {
      binding.timeline.setAttributes()
    }

    override fun bind(data: TimeLineModel) {
      binding.run {
        timeline.bindMarker(data)
        textTimelineDate.bindDate(data)
        textTimelineTitle.bindMessage(data)
      }
    }
  }

  abstract class BaseTimeLineViewHolder(
    private val binding: ViewBinding,
    private val viewType: Int,
    private val attributes: TimelineAttributes
  ) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(data: TimeLineModel)

    fun TimelineView.setAttributes() {
      initLine(viewType)
      markerSize = attributes.markerSize
      setMarkerColor(attributes.markerColor)
      isMarkerInCenter = attributes.markerInCenter
      markerPaddingLeft = attributes.markerLeftPadding
      markerPaddingTop = attributes.markerTopPadding
      markerPaddingRight = attributes.markerRightPadding
      markerPaddingBottom = attributes.markerBottomPadding
      linePadding = attributes.linePadding
      lineWidth = attributes.lineWidth
      setStartLineColor(attributes.startLineColor, viewType)
      setEndLineColor(attributes.endLineColor, viewType)
      lineStyle = attributes.lineStyle
      lineStyleDashLength = attributes.lineDashWidth
      lineStyleDashGap = attributes.lineDashGap
    }

    fun TimelineView.bindMarker(data: TimeLineModel) {
      when (data.status) {
        OrderStatus.INACTIVE -> {
          this.marker = VectorDrawableUtils.getDrawable(
            context,
            R.drawable.ic_marker_inactive,
            attributes.markerColor
          )
        }
        OrderStatus.ACTIVE -> {
          this.marker = VectorDrawableUtils.getDrawable(
            context,
            R.drawable.ic_marker_active,
            attributes.markerColor
          )
        }
        else -> {
          this.setMarker(
            ContextCompat.getDrawable(
              context,
              R.drawable.ic_marker
            ), attributes.markerColor
          )
        }
      }
    }

    fun TextView.bindDate(data: TimeLineModel) {
      if (data.date.isNotEmpty()) {
        isVisible = true
        text = data.date.formatDateTime("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
      } else
        isVisible = false
    }

    fun TextView.bindMessage(data: TimeLineModel) {
      text = data.message
    }

  }

}
