package com.example.ontime.setup

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Tugas::class, Dosen::class, MataKuliah::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tugasDao(): TugasDao
    abstract fun dosenDao(): DosenDao
    abstract fun matkulDao(): MataKuliahDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ontime_database"
                )
                    // Tambahkan opsi migrasi atau fallback
                    .fallbackToDestructiveMigration()  // Hapus semua data dan buat ulang tabel
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
