package com.example.ontime.setup

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TugasDao {
    @Insert
    suspend fun insert(tugas: Tugas)

    // Mengambil tugas yang belum selesai (isDone = false) menggunakan LiveData
    @Query("SELECT * FROM tb_tugas WHERE isDone = 0")
    fun getTugasByStatus(): LiveData<List<Tugas>>

    // Mengambil tugas yang sudah selesai (isDone = true) menggunakan LiveData
    @Query("SELECT * FROM tb_tugas WHERE isDone = 1")
    fun getTugasSelesai(): LiveData<List<Tugas>>

    // Mengambil semua tugas menggunakan LiveData
    @Query("SELECT * FROM tb_tugas")
    fun getAllTugas(): LiveData<List<Tugas>>

    // Update status isDone menjadi true
    @Query("UPDATE tb_tugas SET isDone = 1 WHERE id = :tugasId")
    suspend fun markTugasAsDone(tugasId: Int)

    // Update status isDone menjadi false
    @Query("UPDATE tb_tugas SET isDone = 0 WHERE id = :tugasId")
    suspend fun markTugasAsIncomplete(tugasId: Int)
}
