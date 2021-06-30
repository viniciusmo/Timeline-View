package com.github.vipulasri.timelineview.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

  private var _binding: ViewBinding? = null
  abstract val bindingInflater: (LayoutInflater) -> VB

  @Suppress("UNCHECKED_CAST")
  protected val binding: VB
    get() = _binding as VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = bindingInflater.invoke(layoutInflater)
    setContentView(requireNotNull(_binding).root)
  }

  fun setToolbar(toolbar: Toolbar, isHomeButtonEnabled: Boolean = false) {
    setSupportActionBar(toolbar)
    supportActionBar?.setHomeButtonEnabled(isHomeButtonEnabled)
    supportActionBar?.setDisplayHomeAsUpEnabled(isHomeButtonEnabled)
  }

  fun setToolbarHomeIcon(@DrawableRes iconRes: Int, @ColorRes colorRes: Int = -1) {
    val drawable = ContextCompat.getDrawable(this, iconRes)
    if (colorRes != -1) {
      drawable?.mutate()
      drawable?.setTint(ContextCompat.getColor(this, colorRes))
    }
    supportActionBar?.setHomeAsUpIndicator(drawable)
  }

  fun setToolbarTitle(title: CharSequence?) {
    supportActionBar?.title = title
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Menu
    when (item.itemId) {
      // When home is clicked
      android.R.id.home -> {
        onActionBarHomeIconClicked()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  open fun onActionBarHomeIconClicked() {
    onBackPressed()
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

}
