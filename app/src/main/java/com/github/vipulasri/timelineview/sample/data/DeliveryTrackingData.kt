package com.github.vipulasri.timelineview.sample.data

import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.OrderStatus.INACTIVE
import com.github.vipulasri.timelineview.sample.model.TimeLineModel

val deliveryTrackingData = listOf(
  TimeLineModel("Item successfully delivered", "", INACTIVE),
  TimeLineModel(
    "Courier is out to delivery your order",
    "2017-02-12 08:00",
    OrderStatus.ACTIVE
  ),
  TimeLineModel(
    "Item has reached courier facility at New Delhi",
    "2017-02-11 21:00",
    OrderStatus.COMPLETED
  ),
  TimeLineModel(
    "Item has been given to the courier",
    "2017-02-11 18:00",
    OrderStatus.COMPLETED
  ),
  TimeLineModel(
    "Item is packed and will dispatch soon",
    "2017-02-11 09:30",
    OrderStatus.COMPLETED
  ),
  TimeLineModel(
    "Order is being readied for dispatch",
    "2017-02-11 08:00",
    OrderStatus.COMPLETED
  ),
  TimeLineModel(
    "Order processing initiated",
    "2017-02-10 15:00",
    OrderStatus.COMPLETED
  ),
  TimeLineModel(
    "Order confirmed by seller",
    "2017-02-10 14:30",
    OrderStatus.COMPLETED
  ),
  TimeLineModel(
    "Order placed successfully",
    "2017-02-10 14:00",
    OrderStatus.COMPLETED
  )
)