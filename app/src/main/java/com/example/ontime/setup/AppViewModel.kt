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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    // Inisialisasi Repository
    private val dosenRepository = DosenRepository()
    private val matkulRepository= MatkulRepository()
    private val tugasRepository: TugasRepository

    // Observasi Database
    val tugasSelesai: LiveData<List<Tugas>>
    val tugasBelumSelesai: LiveData<List<Tugas>>

    val dosens: Flow<List<Dosen>> = dosenRepository.dosens
    val matkuls: Flow<List<MataKuliah>> = matkulRepository.matkuls

    init {
        val tugasDao = AppDatabase.getDatabase(application).tugasDao()

        tugasRepository = TugasRepository(tugasDao)

        tugasSelesai = tugasRepository.tugasComplete
        tugasBelumSelesai = tugasRepository.tugasIncomplete
    }

    // Menggunakan coroutine viewModelScope untuk menjalankan proses ini di background thread
    fun insertDosenVm(dosen: Dosen) {
        viewModelScope.launch {
            dosenRepository.insertDosenRep(dosen)
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

    fun deleteTugasVm(tugas: Tugas) {
        viewModelScope.launch {
            tugasRepository.deleteTugasRep(tugas)
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
