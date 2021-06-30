package com.github.vipulasri.timelineview.sample.example

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
  val list = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
  LazyColumn {
    itemsIndexed(list, itemContent = { index, item ->
      TimelineView(
        modifier = Modifier
          .size(100.dp)
          .fillMaxWidth(),
        itemPosition = index,
        totalItems = list.size
      )
    })
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
