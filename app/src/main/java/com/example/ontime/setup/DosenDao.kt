package com.example.ontime.setup

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    @Insert
    suspend fun insertDosen(dosen: Dosen): Long

    @Query("SELECT * FROM tb_dosen")
    fun getAllDosen(): LiveData<List<Dosen>>

    @Delete
    suspend fun  delete(dosen: Dosen)
}

