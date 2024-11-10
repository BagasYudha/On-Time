package com.example.ontime.tugas

import androidx.lifecycle.LiveData

class TugasRepository (private val tugasDao: TugasDao) {
    val tugasIncomplete: LiveData<List<Tugas>> = tugasDao.getTugasIncomplete()
    val tugasComplete: LiveData<List<Tugas>> = tugasDao.getTugasComplete()

    suspend fun insertTugasRep(tugas: Tugas){
        tugasDao.insertTugas(tugas)
    }

    suspend fun deleteTugasRep(tugas: Tugas){
        tugasDao.deleteTugas(tugas)
    }

    suspend fun markTugasComplete(tugas: Tugas){
        tugasDao.markTugasAsComplete(tugas.id)
    }

    suspend fun markTugasIncomplete(tugas: Tugas){
        tugasDao.markTugasAsIncomplete(tugas.id)
    }
}