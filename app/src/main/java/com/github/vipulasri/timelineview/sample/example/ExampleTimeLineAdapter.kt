package com.github.vipulasri.timelineview.sample.example

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineBinding
import com.github.vipulasri.timelineview.sample.extentions.formatDateTime
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils

/**
 * Created by Vipul Asri on 13-12-2018.
 */

class ExampleTimeLineAdapter(private val feedList: List<TimeLineModel>) :
  RecyclerView.Adapter<ExampleTimeLineAdapter.TimeLineViewHolder>() {

  override fun getItemViewType(position: Int): Int {
    return TimelineView.getTimeLineViewType(position, itemCount)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {

    return TimeLineViewHolder(
      ItemTimelineBinding.inflate(LayoutInflater.from(parent.context)),
      viewType
    )
  }

  override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
    val timeLineModel = feedList[position]
    holder.bind(timeLineModel)
  }

  override fun getItemCount() = feedList.size

  inner class TimeLineViewHolder(
    private val binding: ItemTimelineBinding,
    viewType: Int
  ) :
    RecyclerView.ViewHolder(binding.root) {

    init {
      binding.timeline.initLine(viewType)
    }

    fun bind(data: TimeLineModel) {
      when (data.status) {
        OrderStatus.INACTIVE -> {
          binding.setMarker(R.drawable.ic_marker_inactive, R.color.material_grey_500)
        }
        OrderStatus.ACTIVE -> {
          binding.setMarker(R.drawable.ic_marker_active, R.color.material_grey_500)
        }
        else -> {
          binding.setMarker(R.drawable.ic_marker, R.color.material_grey_500)
        }
      }

      if (data.date.isNotEmpty()) {
        binding.textTimelineDate.isVisible = true
        binding.textTimelineDate.text =
          data.date.formatDateTime("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
      } else {
        binding.textTimelineDate.isVisible = false
      }

      binding.textTimelineTitle.text = data.message
    }

    private fun ItemTimelineBinding.setMarker(drawableResId: Int, colorFilter: Int) {
      timeline.marker = VectorDrawableUtils.getDrawable(
        root.context,
        drawableResId,
        ContextCompat.getColor(root.context, colorFilter)
      )
    }

  }

}
