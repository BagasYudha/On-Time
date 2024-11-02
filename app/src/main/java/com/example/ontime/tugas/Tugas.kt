package com.example.ontime.tugas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_tugas")
data class Tugas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val judul: String,
    val matkul: String,
    val isDone: Boolean
)