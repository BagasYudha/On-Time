package com.example.ontime.matkul

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MataKuliahDao {
    @Insert
    suspend fun insertMatkul(vararg matkul: MataKuliah)

    @Query("SELECT * FROM tb_mata_kuliah")
    fun getAllMataKuliah(): LiveData<List<MataKuliah>>

//    @Query("DELETE FROM tb_mata_kuliah WHERE id = :id")
//    suspend fun deleteMatkul(id: Int)

    @Delete
    suspend fun deleteMatkul(vararg matkul: MataKuliah)
}

