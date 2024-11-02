package com.example.ontime.setup

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ontime.dosen.Dosen
import com.example.ontime.dosen.DosenDao
import com.example.ontime.matkul.MataKuliah
import com.example.ontime.matkul.MataKuliahDao
import com.example.ontime.tugas.Tugas
import com.example.ontime.tugas.TugasDao

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
