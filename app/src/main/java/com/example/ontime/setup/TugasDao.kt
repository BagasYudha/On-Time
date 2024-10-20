package com.example.ontime.setup

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TugasDao {
    @Insert
    suspend fun insert(tugas: Tugas)

    @Query("SELECT * FROM tb_tugas WHERE isDone = :isDone")
    fun getTugasByStatus(isDone: Boolean): Flow<List<Tugas>>

    @Query("SELECT * FROM tb_tugas")
    fun getAllTugas(): LiveData<List<Tugas>>
}
