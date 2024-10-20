package com.example.ontime.setup

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {
    @Insert
    suspend fun insertMatkul(matkul: MataKuliah)

    @Query("SELECT * FROM tb_mata_kuliah")
    fun getAllMataKuliah(): LiveData<List<MataKuliah>>

    @Query("DELETE FROM tb_mata_kuliah WHERE id = :id")
    suspend fun deleteMatkul(id: Int)
}

