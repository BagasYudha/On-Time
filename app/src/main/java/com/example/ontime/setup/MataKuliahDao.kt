package com.example.ontime.setup

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {
    @Insert
    fun insertMataKuliah(mataKuliah: MataKuliah)

    @Query("SELECT * FROM tb_mata_kuliah")
    fun getMataKuliah(): LiveData<List<MataKuliah>>
}


