package com.github.vipulasri.timelineview.sample.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.R.style
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class RoundedCornerBottomSheet<VB : ViewBinding> : BottomSheetDialogFragment() {

  private var _binding: ViewBinding? = null
  abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

  @Suppress("UNCHECKED_CAST")
  protected val binding: VB
    get() = _binding as VB

  override fun onStart() {
    super.onStart()

    view?.post {
      val parent = view?.parent as View
      parent.setBackgroundDrawable(
        ContextCompat.getDrawable(
          requireContext(),
          R.drawable.bg_bottom_sheet
        )
      )
    }

  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val contextThemeWrapper = ContextThemeWrapper(requireActivity(), style.AppTheme)
    _binding =
      bindingInflater.invoke(inflater.cloneInContext(contextThemeWrapper), container, false)
    return requireNotNull(_binding).root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

}