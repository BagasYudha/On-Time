package com.example.ontime.setup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.ontime.setup.MataKuliahDao

class AppViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData dari dosenDao langsung diobservasi
    private val dosenDao = AppDatabase.getDatabase(application).dosenDao()

    val allDosen: LiveData<List<Dosen>> = dosenDao.getAllDosen()

    // Fungsi untuk memasukkan dosen
    fun insertDosen(dosen: Dosen) {
        viewModelScope.launch {
            dosenDao.insertDosen(dosen)
        }
    }
}
