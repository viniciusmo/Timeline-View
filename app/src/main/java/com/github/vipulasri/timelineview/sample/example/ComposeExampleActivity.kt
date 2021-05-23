package com.github.vipulasri.timelineview.sample.example

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Preview(showBackground = true)
@Composable
fun TimeLineViewPreview() {
  TimelineView(position = 0, size = 2)

}

@Preview(showBackground = true)
@Composable
fun TimeLineViewListPreview() {
  val list = listOf("1", "2", "3", "4", "5", "6")
  LazyColumn {
    itemsIndexed(list, itemContent = { index, item ->
      TimelineView(
        modifier = Modifier.size(100.dp),
        position = index,
        size = list.size
      )
    })
  }
}
