package com.example.ontime.setup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ontime.dosen.Dosen
import com.example.ontime.dosen.DosenRepository
import com.example.ontime.matkul.MataKuliah
import com.example.ontime.tugas.Tugas
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    // Inisialisasi Repository
    private val dosenRepository: DosenRepository

    // Observasi Database
    val allDosen: LiveData<List<Dosen>>

    init {
        val dosenDao = AppDatabase.getDatabase(application).dosenDao()

        dosenRepository = DosenRepository(dosenDao)

        allDosen = dosenRepository.allDosen
    }



    // LiveData dari dosenDao langsung diobservasi
    private val tugasDao = AppDatabase.getDatabase(application).tugasDao()
    private val matkulDao = AppDatabase.getDatabase(application).matkulDao()

    // LiveData berisi data dari database, digunakan agar UI dapat mengamati perubahan data secara otomatis
    val allTugas: LiveData<List<Tugas>> = tugasDao.getTugasByStatus()
    val tugasSelesai: LiveData<List<Tugas>> = tugasDao.getTugasSelesai()
    val allMatkul: LiveData<List<MataKuliah>> = matkulDao.getAllMataKuliah()


    // Menggunakan coroutine viewModelScope untuk menjalankan proses ini di background thread
    fun insertDosenVm(dosen: Dosen) {
        viewModelScope.launch {
            dosenRepository.insertDosenRep(dosen)  // Memasukkan data dosen ke database
        }
    }

    fun deleteDosenVm(dosen: Dosen) {
        viewModelScope.launch {
            dosenRepository.deleteDosenRep(dosen)
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
