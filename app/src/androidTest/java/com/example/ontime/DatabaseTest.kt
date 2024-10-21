package com.example.myapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ontime.setup.AppDatabase
import com.example.ontime.setup.Dosen
import com.example.ontime.setup.DosenDao
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dosenDao: DosenDao
    private lateinit var db: AppDatabase

    // Buat objek Dosen dan Mata Kuliah untuk pengujian
    private val dosen = Dosen(nama = "Bagas Yudha", email = "yudha@example.com")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries() // Memungkinkan pengujian di main thread
            .build()
        dosenDao = db.dosenDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndRetrieveDosen() {
        dosenDao.insertDosenTest(dosen)
        val allDosen = dosenDao.getAllDosenTest()

        assert(allDosen.size == 1)
        assertEquals(allDosen[0].nama, "Bagas Yudha")
    }
}