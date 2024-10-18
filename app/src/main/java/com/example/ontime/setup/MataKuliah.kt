package com.example.ontime.setup

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_mata_kuliah")
class MataKuliah(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val matkul: String,
    val dosenId: Int //Foreign key to Dosen
)