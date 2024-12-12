package com.example.ontime.tugas

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TugasRepository {

    private val database = FirebaseDatabase.getInstance()
    private val tugasRef = database.getReference("tugas")

    // StateFlow untuk tugas yang belum selesai
    private val _tugasIncomplete = MutableStateFlow<List<Tugas>>(emptyList())
    val tugasIncomplete: StateFlow<List<Tugas>> get() = _tugasIncomplete

    // StateFlow untuk tugas yang sudah selesai
    private val _tugasComplete = MutableStateFlow<List<Tugas>>(emptyList())
    val tugasComplete: StateFlow<List<Tugas>> get() = _tugasComplete

    init {
        // Dengarkan perubahan data di Firebase
        tugasRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedIncomplete = snapshot.children.mapNotNull { it.getValue(Tugas::class.java) }.filter { !it.done } // Hanya ambil tugas yang belum selesai
                _tugasIncomplete.value = fetchedIncomplete

                val fetchedComplete = snapshot.children.mapNotNull { it.getValue(Tugas::class.java) }
                    .filter { it.done }
                _tugasComplete.value = fetchedComplete // Tugas selesai
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TugasRepository", "Gagal membaca data dari Firebase: ${error.message}", error.toException())
            }
        })
    }

    // Fungsi untuk menambahkan tugas baru ke Firebase
    fun insertTugasRep(tugas: Tugas) {
        tugas.id = tugasRef.push().key
        tugas.id?.let { tugasRef.child(it).setValue(tugas)
        }
    }

    // Fungsi untuk menandai tugas selesai
    fun markTugasComplete(tugas: Tugas) {
        tugas.id?.let { id ->
            tugasRef.child(id).child("done").setValue(true)
        } ?: Log.w("TugasRepository", "Gagal menandai tugas selesai karena ID null.")
    }

    // Fungsi untuk menandai tugas belum selesai
    fun markTugasIncomplete(tugas: Tugas) {
        tugas.id?.let { id ->
            tugasRef.child(id).child("done").setValue(false)
        }
    }

    // Fungsi untuk menghapus tugas dari Firebase
    fun deleteTugasRep(tugas: Tugas) {
        tugas.id?.let { id ->  tugasRef.child(id).removeValue() }
    }
}
