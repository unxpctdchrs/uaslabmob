package com.asparagus.ourlist.model

import java.util.UUID

data class ToDoItem(
    var title:String,
    var description:String,
    var isCompleted: Boolean = false,
    var id: UUID = UUID.randomUUID()
)