package com.github.vipulasri.timelineview.sample.example

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vipulasri.timelineview.sample.data.deliveryTrackingData
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.vipulasri.timelineview.compose.TimelineView

class ComposeExampleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MaterialTheme {
        Scaffold(
          topBar = {
            ToolBar {
              closeScreen()
            }
          }
        ) {
          TimeLineViewList()
        }
      }
    }
  }

  private fun closeScreen() {
    finish()
  }

}

@Composable
private fun ToolBar(onBack: () -> Unit) {
  Surface {
    TopAppBar(
      title = { Text(text = "Compose Example") },
      navigationIcon = {
        IconButton(onClick = onBack) {
          Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "back"
          )
        }
      }
    )
  }
}

@Composable
private fun TimeLineViewList() {
  val list = deliveryTrackingData
  LazyColumn {
    itemsIndexed(list, itemContent = { index, item ->
      DeliveryTrackingCard(
        item = item,
        position = index,
        totalItems = list.size
      )
    })
  }
}

@Composable
private fun DeliveryTrackingCard(
  item: TimeLineModel,
  position: Int,
  totalItems: Int
) {
  Row {
    TimelineView(
      modifier = Modifier
        .size(50.dp),
      markerSize = 20.dp,
      itemPosition = position,
      totalItems = totalItems
    )
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.CenterVertically)
        .padding(10.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(10.dp)
      ) {
        Text(text = item.date)
        Text(text = item.message)
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun TimeLineViewPreview() {
  TimelineView()
}

@Preview(showBackground = true)
@Composable
fun TimeLineViewListPreview() {
  TimeLineViewList()
}
