package com.github.vipulasri.timelineview.sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.databinding.BottomSheetOptionsBinding
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimelineAttributes
import com.github.vipulasri.timelineview.sample.widgets.BorderedCircle
import com.github.vipulasri.timelineview.sample.widgets.RoundedCornerBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.thebluealliance.spectrum.SpectrumDialog
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TimelineAttributesBottomSheet : RoundedCornerBottomSheet<BottomSheetOptionsBinding>() {

  interface Callbacks {
    fun onAttributesChanged(attributes: TimelineAttributes)
  }

  companion object {

    private const val EXTRA_ATTRIBUTES = "EXTRA_ATTRIBUTES"

    fun showDialog(
      fragmentManager: FragmentManager,
      attributes: TimelineAttributes,
      callbacks: Callbacks
    ) {
      val dialog = TimelineAttributesBottomSheet()
      dialog.arguments = bundleOf(
        EXTRA_ATTRIBUTES to attributes
      )
      dialog.setCallback(callbacks)
      dialog.show(fragmentManager, "[TIMELINE_ATTRIBUTES_BOTTOM_SHEET]")
    }
  }

  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetOptionsBinding
    get() = BottomSheetOptionsBinding::inflate

  private var callbacks: Callbacks? = null
  private lateinit var attributes: TimelineAttributes
  private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

  override fun onStart() {
    super.onStart()

    if (dialog != null) {
      val bottomSheet = dialog!!.findViewById<View>(R.id.design_bottom_sheet)
      bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    view?.post {
      val parent = view?.parent as View
      val params = parent.layoutParams as CoordinatorLayout.LayoutParams
      val behavior = params.behavior
      bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
      bottomSheetBehavior?.peekHeight = view?.measuredHeight!!
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val attributes = (arguments?.getParcelable(EXTRA_ATTRIBUTES) as? TimelineAttributes)!!
    this.attributes = attributes.copy()

    binding.textAttributesHeading.setOnClickListener { dismiss() }

    setupOrientation()
    setupMarker()
    setupLine()

    binding.buttonApply.setOnClickListener {
      callbacks?.onAttributesChanged(this.attributes)
      dismiss()
    }
  }

  private fun setupOrientation() {
    binding.rgOrientation.setOnCheckedChangeListener { group, checkedId ->
      when (checkedId) {
        R.id.rb_horizontal -> {
          attributes.orientation = Orientation.HORIZONTAL
        }
        R.id.rb_vertical -> {
          attributes.orientation = Orientation.VERTICAL
        }
      }
    }
    binding.rgOrientation.check(
      if (attributes.orientation == Orientation.VERTICAL) R.id.rb_vertical
      else R.id.rb_horizontal
    )
  }

  private fun setupMarker() {
    binding.layoutMarker.run {
      seekMarkerSize.progress = attributes.markerSize
      imageMarkerColor.fillColor = attributes.markerColor
      checkboxMarkerInCenter.isChecked = attributes.markerInCenter
      seekMarkerLeftPadding.progress = attributes.markerLeftPadding
      seekMarkerTopPadding.progress = attributes.markerTopPadding
      seekMarkerRightPadding.progress = attributes.markerRightPadding
      seekMarkerBottomPadding.progress = attributes.markerBottomPadding
      seekMarkerLinePadding.progress = attributes.linePadding

      checkboxMarkerInCenter.setOnCheckedChangeListener { buttonView, isChecked ->
        attributes.markerInCenter = isChecked
      }

      imageMarkerColor.setOnClickListener {
        showColorPicker(
          attributes.markerColor,
          imageMarkerColor
        )
      }

      seekMarkerSize.setOnProgressChangeListener(progressChangeListener)
      seekMarkerLeftPadding.setOnProgressChangeListener(progressChangeListener)
      seekMarkerTopPadding.setOnProgressChangeListener(progressChangeListener)
      seekMarkerRightPadding.setOnProgressChangeListener(progressChangeListener)
      seekMarkerBottomPadding.setOnProgressChangeListener(progressChangeListener)
      seekMarkerLinePadding.setOnProgressChangeListener(progressChangeListener)
    }
  }

  private fun setupLine() {
    //line
    Log.e(" mAttributes.lineWidth", "${attributes.lineWidth}")

    binding.layoutLine.run {
      seekLineWidth.progress = attributes.lineWidth
      imageStartLineColor.fillColor = attributes.startLineColor
      imageEndLineColor.fillColor = attributes.endLineColor

      imageStartLineColor.setOnClickListener {
        showColorPicker(
          attributes.startLineColor,
          imageStartLineColor
        )
      }
      imageEndLineColor.setOnClickListener {
        showColorPicker(
          attributes.endLineColor,
          imageEndLineColor
        )
      }

      when (attributes.lineStyle) {
        TimelineView.LineStyle.NORMAL -> spinnerLineType.setSelection(0)
        TimelineView.LineStyle.DASHED -> spinnerLineType.setSelection(1)
      }

      spinnerLineType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
          val selectedItem = parent.getItemAtPosition(position).toString()
          when (selectedItem) {
            "Normal" -> attributes.lineStyle = TimelineView.LineStyle.NORMAL
            "Dashed" -> attributes.lineStyle = TimelineView.LineStyle.DASHED
            else -> {
              attributes.lineStyle = TimelineView.LineStyle.NORMAL
            }
          }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
      }

      seekLineDashWidth.progress = attributes.lineDashWidth
      seekLineDashGap.progress = attributes.lineDashGap

      seekLineWidth.setOnProgressChangeListener(progressChangeListener)
      seekLineDashWidth.setOnProgressChangeListener(progressChangeListener)
      seekLineDashGap.setOnProgressChangeListener(progressChangeListener)
    }
  }

  private fun showColorPicker(selectedColor: Int, colorView: BorderedCircle) {
    SpectrumDialog.Builder(requireContext())
      .setColors(R.array.colors)
      .setSelectedColor(selectedColor)
      .setDismissOnColorSelected(true)
      .setOutlineWidth(1)
      .setOnColorSelectedListener { positiveResult, color ->
        if (positiveResult) {
          colorView.fillColor = color

          when (colorView.id) {
            R.id.image_marker_color -> {
              attributes.markerColor = color
            }
            R.id.image_start_line_color -> {
              attributes.startLineColor = color
            }
            R.id.image_end_line_color -> {
              attributes.endLineColor = color
            }
            else -> {
              //do nothing
            }
          }

        }
      }.build().show(childFragmentManager, "ColorPicker")
  }

  private var progressChangeListener: DiscreteSeekBar.OnProgressChangeListener =
    object : DiscreteSeekBar.OnProgressChangeListener {

      override fun onProgressChanged(discreteSeekBar: DiscreteSeekBar, value: Int, b: Boolean) {

        Log.d("onProgressChanged", "value->$value")
        when (discreteSeekBar.id) {
          R.id.seek_marker_size -> {
            attributes.markerSize = value
          }
          R.id.seek_marker_left_padding -> {
            attributes.markerLeftPadding = value
          }
          R.id.seek_marker_top_padding -> {
            attributes.markerTopPadding = value
          }
          R.id.seek_marker_right_padding -> {
            attributes.markerRightPadding = value
          }
          R.id.seek_marker_bottom_padding -> {
            attributes.markerBottomPadding = value
          }
          R.id.seek_marker_line_padding -> {
            attributes.linePadding = value
          }
          R.id.seek_line_width -> {
            attributes.lineWidth = value
          }
          R.id.seek_line_dash_width -> {
            attributes.lineDashWidth = value
          }
          R.id.seek_line_dash_gap -> {
            attributes.lineDashGap = value
          }
        }
      }

      override fun onStartTrackingTouch(discreteSeekBar: DiscreteSeekBar) {

      }

      override fun onStopTrackingTouch(discreteSeekBar: DiscreteSeekBar) {

      }
    }

  private fun setCallback(callbacks: Callbacks) {
    this.callbacks = callbacks
  }

}