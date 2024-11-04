package com.example.ontime.setup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ontime.dosen.Dosen
import com.example.ontime.dosen.DosenRepository
import com.example.ontime.matkul.MataKuliah
import com.example.ontime.matkul.MatkulRepository
import com.example.ontime.tugas.Tugas
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    // Inisialisasi Repository
    private val dosenRepository: DosenRepository
    private val matkulRepository: MatkulRepository

    // Observasi Database
    val allDosen: LiveData<List<Dosen>>
    val allMatkul: LiveData<List<MataKuliah>>

    init {
        val dosenDao = AppDatabase.getDatabase(application).dosenDao()
        val matkulDao = AppDatabase.getDatabase(application).matkulDao()

        dosenRepository = DosenRepository(dosenDao)
        matkulRepository = MatkulRepository(matkulDao)

        allDosen = dosenRepository.allDosen
        allMatkul = matkulRepository.allMatkul
    }


    // LiveData dari dosenDao langsung diobservasi
    private val tugasDao = AppDatabase.getDatabase(application).tugasDao()


    // LiveData berisi data dari database, digunakan agar UI dapat mengamati perubahan data secara otomatis
    val allTugas: LiveData<List<Tugas>> = tugasDao.getTugasByStatus()
    val tugasSelesai: LiveData<List<Tugas>> = tugasDao.getTugasSelesai()

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

    fun insertMatkulVm(matkul: MataKuliah) {
        viewModelScope.launch {
            matkulRepository.insertMatkulRep(matkul)
        }
    }

    fun deleteMatkulVm(matkul: MataKuliah) {
        viewModelScope.launch {
            matkulRepository.deleteMatkulRep(matkul)
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

}
