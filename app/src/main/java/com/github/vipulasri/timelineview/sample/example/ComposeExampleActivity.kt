package com.github.vipulasri.timelineview.sample.example

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.vipulasri.timelineview.sample.BaseActivity
import com.vipulasri.timelineview.compose.TimelineView

class ComposeExampleActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TimelineView()
    }
  }

}

@Preview
@Composable
fun TimelineViewPreview() {
  TimelineView()
}