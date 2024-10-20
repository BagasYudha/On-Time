package com.example.ontime.setup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData dari dosenDao langsung diobservasi
    private val dosenDao = AppDatabase.getDatabase(application).dosenDao()

    // LiveData berisi data dari database, digunakan agar UI dapat mengamati perubahan data secara otomatis
    val allDosen: LiveData<List<Dosen>> = dosenDao.getAllDosen()

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
}
