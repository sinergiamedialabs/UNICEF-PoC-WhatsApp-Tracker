package com.synergia.datapoc

import androidx.room.ColumnInfo
import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(primaryKeys = ["sender", "date", "msg"])
data class MessageLog(
    @NotNull @ColumnInfo(name = "sender") var sender: String,
    @NotNull @ColumnInfo(name = "date") var date: String,
    @NotNull @ColumnInfo(name = "msg") var msg: String
)