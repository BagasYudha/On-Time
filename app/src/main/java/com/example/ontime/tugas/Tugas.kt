package com.example.ontime.tugas

data class Tugas(
    var id: String? = null,
    var judul: String = "",
    var matkul: String = "",
    var done: Boolean = false,
    var isPriority: Boolean = false
)
