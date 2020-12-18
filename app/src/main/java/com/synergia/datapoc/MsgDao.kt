package com.synergia.datapoc

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MsgDao {
    @Query("SELECT * FROM MessageLog")
     fun getAll(): LiveData<List<MessageLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insert(users: MessageLog)

    @Query("SELECT * FROM SendLog")
     fun getSendLog(): LiveData<List<SendLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertSendLog(users: SendLog)
}