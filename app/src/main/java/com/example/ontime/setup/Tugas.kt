package com.example.ontime.setup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_tugas")
data class Tugas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val judul: String,
    val isDone: Boolean
)