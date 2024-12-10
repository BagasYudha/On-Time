package com.example.ontime.tugas

data class Tugas(
    var id: String? = null,
    var judul: String = "",
    var matkul: String = "",
    var isDone: Boolean = false,
    var isPriority: Boolean = false
)
