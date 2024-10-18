package com.example.ontime.setup

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {
    @Insert
    suspend fun insert(mataKuliah: MataKuliah)

    @Query("SELECT * FROM tb_mata_kuliah WHERE dosenId = :dosenId")
    fun getMataKuliahByDosen(dosenId: Int): Flow<List<MataKuliah>>
}