package com.example.ontime.setup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    // Mengambil instance dari DAO untuk mengakses tabel di database
    private val dosenDao = AppDatabase.getDatabase(application).dosenDao()
    private val matkulDao = AppDatabase.getDatabase(application).matkulDao()

    // LiveData berisi data dari database, digunakan agar UI dapat mengamati perubahan data secara otomatis
    val allDosen: LiveData<List<Dosen>> = dosenDao.getAllDosen()
    val allMatkul: LiveData<List<MataKuliah>> = matkulDao.getAllMatkul()

    // Fungsi untuk memasukkan data yang diinput ke dalam tabel masing masing di database
    // Menggunakan coroutine viewModelScope untuk menjalankan proses ini di background thread
    fun insertDosen(dosen: Dosen) {
        viewModelScope.launch {
            dosenDao.insertDosen(dosen)  // Memasukkan data dosen ke database
        }
    }

    fun inserMatkul(mataKuliah: MataKuliah) {
        viewModelScope.launch {
            matkulDao.insertMatkul(mataKuliah)
        }
    }
}
