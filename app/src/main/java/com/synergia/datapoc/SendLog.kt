package com.synergia.datapoc

import androidx.room.ColumnInfo
import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(primaryKeys = [ "date", "msg"])
data class SendLog(
    @NotNull @ColumnInfo(name = "date") var date: String,
    @NotNull @ColumnInfo(name = "msg") var msg: String
)