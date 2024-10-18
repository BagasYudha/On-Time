package com.example.ontime.setup

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    @Insert
    suspend fun insert(dosen: Dosen)

    @Query("Select * FROM tb_dosen")
    fun getAllDosen(): Flow<List<Dosen>>
}