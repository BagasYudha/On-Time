package com.example.ontime.setup

class TugasRepository(
    private val dosenDao: DosenDao,
    private val mataKuliahDao: MataKuliahDao,
    private val tugasDao: TugasDao
) {

    // Dosen
    val allDosen = dosenDao.getAllDosen()

    suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insert(dosen)
    }

    // Mata Kuliah
    fun getMataKuliahByDosen(dosenId: Int) = mataKuliahDao.getMataKuliahByDosen(dosenId)

    suspend fun insertMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.insert(mataKuliah)
    }

    // Tugas
    fun getTugasByMataKuliah(mataKuliahId: Int) = tugasDao.getTugasByMatkul(mataKuliahId)

    suspend fun insertTugas(tugas: Tugas) {
        tugasDao.insert(tugas)
    }
}
