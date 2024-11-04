package com.example.ontime.dosen

import androidx.lifecycle.LiveData

class DosenRepository (private val dosenDao: DosenDao) {
    val allDosen: LiveData<List<Dosen>> = dosenDao.getAllDosen()

    suspend fun insertDosenRep(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    suspend fun deleteDosenRep(dosen: Dosen) {
        dosenDao.deleteDosen(dosen)
    }
}