package com.example.ontime.matkul

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MatkulRepository {
    private val database = FirebaseDatabase.getInstance()
    private val matkulRef = database.getReference("matkul")

    private val _matkuls = MutableStateFlow<List<MataKuliah>>(emptyList())
    val matkuls: StateFlow<List<MataKuliah>> get() = _matkuls

    init {
        // Listen to data changes in Firebase
        matkulRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedNotes = snapshot.children.mapNotNull { it.getValue(MataKuliah::class.java) }
                _matkuls.value = fetchedNotes
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun insertMatkulRep(matkul: MataKuliah) {
        matkul.id = matkulRef.push().key
        matkul.id?.let { matkulRef.child(it).setValue(matkul) }
    }

    fun deleteMatkulRep(matkul: MataKuliah) {
        matkul.id?.let { matkulRef.child(it).removeValue() }
    }
}