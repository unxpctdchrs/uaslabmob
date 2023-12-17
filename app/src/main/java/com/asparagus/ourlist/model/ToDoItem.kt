package com.asparagus.ourlist.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class ToDoItem(
    var title:String,
    var description:String,
    var dueTime: LocalTime?,
    var completedDate: LocalDate?,
    var isCompleted: Boolean = false,
    var id: UUID = UUID.randomUUID()
)