package com.example.ontime.setup

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Dosen::class,
                MataKuliah::class,
                Tugas::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dosenDao(): DosenDao
    abstract fun matkulDao(): MataKuliahDao
    abstract fun tugasDao(): TugasDao
}