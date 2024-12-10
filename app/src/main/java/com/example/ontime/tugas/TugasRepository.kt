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

    private val _tugasIncomplete = MutableStateFlow<List<Tugas>>(emptyList())
    val tugasIncomplete: StateFlow<List<Tugas>> get() = _tugasIncomplete

    private val _tugasComplete = MutableStateFlow<List<Tugas>>(emptyList())
    val tugasComplete: StateFlow<List<Tugas>> get() = _tugasComplete

    init {
        // Dengarkan perubahan data di Firebase
        tugasRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedIncomplete = mutableListOf<Tugas>()
                val fetchedComplete = mutableListOf<Tugas>()

                for (child in snapshot.children) {
                    val tugas = child.getValue(Tugas::class.java)
                    if (tugas != null) {
                        tugas.id = child.key // Set ID dengan key Firebase
                        if (tugas.isDone) {
                            fetchedComplete.add(tugas)
                        } else {
                            fetchedIncomplete.add(tugas)
                        }
                    }
                }

                _tugasIncomplete.value = fetchedIncomplete
                _tugasComplete.value = fetchedComplete

                Log.d("TugasRepository", "Data tugas diperbarui: Incomplete (${fetchedIncomplete.size}), Complete (${fetchedComplete.size})")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TugasRepository", "Gagal membaca data dari Firebase: ${error.message}", error.toException())
            }
        })
    }

    fun insertTugasRep(tugas: Tugas) {
        val id = tugasRef.push().key
        if (id != null) {
            tugas.id = id
            tugasRef.child(id).setValue(tugas)
                .addOnSuccessListener {
                    Log.d("TugasRepository", "Tugas berhasil ditambahkan dengan ID: $id")
                }
                .addOnFailureListener {
                    Log.e("TugasRepository", "Gagal menambahkan tugas dengan ID: $id, Error: ${it.message}", it)
                }
        } else {
            Log.w("TugasRepository", "Gagal membuat ID unik untuk tugas.")
        }
    }

    fun markTugasComplete(tugas: Tugas) {
        tugas.id?.let { id ->
            tugasRef.child(id).child("isDone").setValue(true)
                .addOnSuccessListener {
                    Log.d("TugasRepository", "Tugas berhasil ditandai selesai dengan ID: $id")
                }
                .addOnFailureListener {
                    Log.e("TugasRepository", "Gagal menandai tugas selesai dengan ID: $id, Error: ${it.message}", it)
                }
        } ?: Log.w("TugasRepository", "Gagal menandai tugas selesai karena ID null.")
    }

    fun deleteTugasRep(tugas: Tugas) {
        tugas.id?.let { id ->
            tugasRef.child(id).removeValue()
                .addOnSuccessListener {
                    Log.d("TugasRepository", "Tugas berhasil dihapus dengan ID: $id")
                }
                .addOnFailureListener {
                    Log.e("TugasRepository", "Gagal menghapus tugas dengan ID: $id, Error: ${it.message}", it)
                }
        } ?: Log.w("TugasRepository", "Gagal menghapus tugas karena ID null.")
    }


    fun markTugasIncomplete(tugas: Tugas) {
        tugas.id?.let {
            tugasRef.child(it).child("isDone").setValue(false)
                .addOnSuccessListener {
                    Log.d("TugasRepository", "Tugas berhasil ditandai belum selesai dengan ID: $it")
                }
                .addOnFailureListener {
                    Log.e("TugasRepository", "Gagal menandai tugas belum selesai dengan ID: $it, Error: ${it.message}", it)
                }
        } ?: Log.w("TugasRepository", "Gagal menandai tugas belum selesai karena ID null.")
    }
}
