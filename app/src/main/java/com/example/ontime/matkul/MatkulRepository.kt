package com.example.ontime.matkul

import androidx.lifecycle.LiveData

class MatkulRepository(private val matkulDao: MataKuliahDao) {
    val allMatkul: LiveData<List<MataKuliah>> = matkulDao.getAllMataKuliah()

    suspend fun insertMatkulRep(matkul: MataKuliah) {
        matkulDao.insertMatkul(matkul)
    }

    suspend fun deleteMatkulRep(matkul: MataKuliah) {
        matkulDao.deleteMatkul(matkul)
    }
}