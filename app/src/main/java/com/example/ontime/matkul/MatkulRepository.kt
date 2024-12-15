package com.example.ontime.matkul

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MatkulRepository {
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val currentUserId = auth.currentUser?.uid

    private val _matkuls = MutableStateFlow<List<MataKuliah>>(emptyList())
    val matkuls: StateFlow<List<MataKuliah>> get() = _matkuls

    init {
        // Listen to data changes in Firebase
        currentUserId?.let { uid ->
            val matkulRef = database.getReference("matkuls/$uid")

            matkulRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fetchedNotes =
                        snapshot.children.mapNotNull { it.getValue(MataKuliah::class.java) }
                    _matkuls.value = fetchedNotes
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
        }
    }

    fun insertMatkulRep(matkul: MataKuliah) {
        currentUserId?.let { uid ->
            val matkulRef = database.getReference("matkuls/$uid")

            matkul.id = matkulRef.push().key
            matkul.id?.let { matkulRef.child(it).setValue(matkul) }
        }
    }

    fun deleteMatkulRep(matkul: MataKuliah) {
        currentUserId?.let { uid ->
            val matkulRef = database.getReference("matkuls/$uid")

            matkul.id?.let { matkulRef.child(it).removeValue() }
        }
    }
}