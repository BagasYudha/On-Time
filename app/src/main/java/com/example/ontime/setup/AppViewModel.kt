package com.example.ontime.setup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData dari dosenDao langsung diobservasi
    private val dosenDao = AppDatabase.getDatabase(application).dosenDao()
    private val tugasDao = AppDatabase.getDatabase(application).tugasDao()
    private val matkulDao = AppDatabase.getDatabase(application).matkulDao()

    // LiveData berisi data dari database, digunakan agar UI dapat mengamati perubahan data secara otomatis
    val allDosen: LiveData<List<Dosen>> = dosenDao.getAllDosen()
    val allTugas: LiveData<List<Tugas>> = tugasDao.getTugasByStatus()
    val tugasSelesai: LiveData<List<Tugas>> = tugasDao.getTugasSelesai()
    val allMatkul: LiveData<List<MataKuliah>> = matkulDao.getAllMataKuliah()


    // Fungsi untuk memasukkan data yang diinput ke dalam tabel masing masing di database
    // Menggunakan coroutine viewModelScope untuk menjalankan proses ini di background thread
    fun insertDosen(dosen: Dosen) {
        viewModelScope.launch {
            dosenDao.insertDosen(dosen)  // Memasukkan data dosen ke database
        }
    }

    fun deleteDosen (dosen: Dosen) {
        viewModelScope.launch {
            dosenDao.delete(dosen)
        }
    }

    fun insertTugas(tugas: Tugas) {
        viewModelScope.launch {
            tugasDao.insert(tugas)
        }
    }

    fun deleteTugas(tugas: Tugas) {
        viewModelScope.launch {
            tugasDao.markTugasAsDone(tugas.id)
        }
    }

    fun incompleteTugas(tugas: Tugas) {
        viewModelScope.launch {
            tugasDao.markTugasAsIncomplete(tugas.id) // Panggil fungsi di TugasDao untuk mengubah status
        }
    }

    fun insertMatkul(mataKuliah: MataKuliah) {
        viewModelScope.launch {
            matkulDao.insertMatkul(mataKuliah)
        }
    }

    fun deleteMatkul(matkul: MataKuliah) {
        viewModelScope.launch {
            matkulDao.deleteMatkul(matkul.id)
        }
    }
}
