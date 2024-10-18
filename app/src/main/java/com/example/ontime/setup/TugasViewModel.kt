package com.example.ontime.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TugasViewModel(private val repository: TugasRepository) : ViewModel() {

    // Dosen
    val allDosen = repository.allDosen

    fun insertDosen(dosen: Dosen) {
        viewModelScope.launch {
            repository.insertDosen(dosen)
        }
    }

    // Mata Kuliah
    fun getMataKuliahByDosen(dosenId: Int) = repository.getMataKuliahByDosen(dosenId)

    fun insertMataKuliah(mataKuliah: MataKuliah) {
        viewModelScope.launch {
            repository.insertMataKuliah(mataKuliah)
        }
    }

    // Tugas
    fun getTugasByMataKuliah(mataKuliahId: Int) = repository.getTugasByMataKuliah(mataKuliahId)

    fun insertTugas(tugas: Tugas) {
        viewModelScope.launch {
            repository.insertTugas(tugas)
        }
    }
}
