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
import com.example.ontime.tugas.TugasRepository
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    // Inisialisasi Repository
    private val dosenRepository: DosenRepository
    private val matkulRepository: MatkulRepository
    private val tugasRepository: TugasRepository

    // Observasi Database
    val allDosen: LiveData<List<Dosen>>
    val allMatkul: LiveData<List<MataKuliah>>
    val tugasSelesai: LiveData<List<Tugas>>
    val tugasBelumSelesai: LiveData<List<Tugas>>

    init {
        val dosenDao = AppDatabase.getDatabase(application).dosenDao()
        val matkulDao = AppDatabase.getDatabase(application).matkulDao()
        val tugasDao = AppDatabase.getDatabase(application).tugasDao()

        dosenRepository = DosenRepository(dosenDao)
        matkulRepository = MatkulRepository(matkulDao)
        tugasRepository = TugasRepository(tugasDao)

        allDosen = dosenRepository.allDosen
        allMatkul = matkulRepository.allMatkul
        tugasSelesai = tugasRepository.tugasComplete
        tugasBelumSelesai = tugasRepository.tugasIncomplete
    }

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

    fun insertTugasVm(tugas: Tugas) {
        viewModelScope.launch {
            tugasRepository.insertTugasRep(tugas)
        }
    }

    fun markTugasCompleteVm(tugas: Tugas) {
        viewModelScope.launch {
            tugasRepository.markTugasComplete(tugas)
        }
    }

    fun markTugasIncompleteVm(tugas: Tugas) {
        viewModelScope.launch {
            tugasRepository.markTugasIncomplete(tugas)
        }
    }
}
