package com.example.ontime.tugas

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TugasDao {
    @Insert
    suspend fun insertTugas(vararg tugas: Tugas)

    @Delete
    suspend fun deleteTugas(vararg tugas: Tugas)

    // Mengambil tugas yang belum selesai (isDone = false) menggunakan LiveData
    @Query("SELECT * FROM tb_tugas WHERE isDone = 0")
    fun getTugasIncomplete(): LiveData<List<Tugas>>

    // Mengambil tugas yang sudah selesai (isDone = true) menggunakan LiveData
    @Query("SELECT * FROM tb_tugas WHERE isDone = 1")
    fun getTugasComplete(): LiveData<List<Tugas>>

    // Update status isDone menjadi true
    @Query("UPDATE tb_tugas SET isDone = 1 WHERE id = :tugasId")
    suspend fun markTugasAsComplete(tugasId: Int)

    // Update status isDone menjadi false
    @Query("UPDATE tb_tugas SET isDone = 0 WHERE id = :tugasId")
    suspend fun markTugasAsIncomplete(tugasId: Int)
}
